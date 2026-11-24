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
import com.caresync.app.data.UserRole

@Composable
fun SignUpScreen(
    onSignedUp: (UserRole) -> Unit,
    onGoToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf<UserRole?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password (6+ characters)") }, singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Text("I am a:", fontSize = 20.sp)
        RoleOption("Patient", role == UserRole.PATIENT) { role = UserRole.PATIENT }
        RoleOption("Caregiver", role == UserRole.CAREGIVER) { role = UserRole.CAREGIVER }

        error?.let { Text(it, color = MaterialTheme.colorScheme.error, fontSize = 18.sp) }

        Button(
            onClick = {
                error = validate(name, email, password, role)
                if (error == null) {
                    loading = true
                    AuthRepository.signUp(email, password) { result ->
                        loading = false
                        if (result == null) onSignedUp(role!!) else error = result
                    }
                }
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(if (loading) "Creating account..." else "Sign Up", fontSize = 20.sp)
        }

        TextButton(onClick = onGoToLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Already have an account? Log in", fontSize = 18.sp)
        }
    }
}

@Composable
private fun RoleOption(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(selected = selected, onClick = onSelect, role = Role.RadioButton)
    ) {
        RadioButton(selected = selected, onClick = null)
        Spacer(Modifier.width(12.dp))
        Text(label, fontSize = 20.sp)
    }
}

private fun validate(name: String, email: String, password: String, role: UserRole?): String? = when {
    name.isBlank() -> "Please enter your name."
    !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> "Please enter a valid email."
    password.length < 6 -> "Password must be at least 6 characters."
    role == null -> "Please choose Patient or Caregiver."
    else -> null
}