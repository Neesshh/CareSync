package com.caresync.app.ui.auth

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caresync.app.data.AuthRepository
import com.caresync.app.data.UserRepository
import com.caresync.app.data.UserRole

/**
 * Sign-up screen: name, email, password, and Patient/Caregiver choice.
 * Flow: check the fields → create the Auth account → save the Firestore profile → onSignedUp(role).
 *
 * @param onSignedUp called after the account AND profile are both saved
 * @param onGoToLogin called when the user taps "Already have an account?"
 */
@Composable
fun SignUpScreen(
    onSignedUp: (UserRole) -> Unit,
    onGoToLogin: () -> Unit
) {
    // Screen state: "remember" keeps these values when the screen redraws
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf<UserRole?>(null) }     // null = not picked yet
    var error by remember { mutableStateOf<String?>(null) }      // null = no error to show
    var loading by remember { mutableStateOf(false) }            // true while waiting on Firebase

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),   // scrolls on small phones / large text
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Create Account", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = name, onValueChange = { name = it },
            label = { Text("Full name") }, singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") }, singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),   // shows the @ keyboard
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password (6+ characters)") }, singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp),
            visualTransformation = PasswordVisualTransformation(),   // shows dots instead of letters
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Text("I am a:", fontSize = 20.sp)
        RoleOption("Patient", role == UserRole.PATIENT) { role = UserRole.PATIENT }
        RoleOption("Caregiver", role == UserRole.CAREGIVER) { role = UserRole.CAREGIVER }

        // Only shows when there's an error message
        error?.let { Text(it, color = MaterialTheme.colorScheme.error, fontSize = 18.sp) }

        Button(
            onClick = {
                error = validate(name, email, password, role)
                val chosenRole = role   // copy into a val so it can't change while Firebase is working
                if (error == null && chosenRole != null) {
                    loading = true
                    // Step 1: create the login account
                    AuthRepository.signUp(email, password) { authError ->
                        if (authError != null) {
                            loading = false
                            error = authError
                        } else {
                            // Step 2: save their profile (name, role, etc.) to Firestore
                            UserRepository.createProfile(name, email, chosenRole) { saveError ->
                                loading = false
                                if (saveError == null) onSignedUp(chosenRole) else error = saveError
                            }
                        }
                    }
                }
            },
            enabled = !loading,   // disabled while loading so they can't tap twice
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(if (loading) "Creating account..." else "Sign Up", fontSize = 20.sp)
        }

        TextButton(onClick = onGoToLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Already have an account? Log in", fontSize = 18.sp)
        }
    }
}

/** One radio-button row. The whole 56dp row is tappable, not just the small circle (easier for seniors). */
@Composable
private fun RoleOption(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(selected = selected, onClick = onSelect, role = Role.RadioButton)
    ) {
        RadioButton(selected = selected, onClick = null)   // null: the Row handles the tap
        Spacer(Modifier.width(12.dp))
        Text(label, fontSize = 20.sp)
    }
}

/** Checks the form before calling Firebase. Returns an error message, or null if everything is OK. */
private fun validate(name: String, email: String, password: String, role: UserRole?): String? = when {
    name.isBlank() -> "Please enter your name."
    !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> "Please enter a valid email."
    password.length < 6 -> "Password must be at least 6 characters."
    role == null -> "Please choose Patient or Caregiver."
    else -> null
}