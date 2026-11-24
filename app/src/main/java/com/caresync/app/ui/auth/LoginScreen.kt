package com.caresync.app.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caresync.app.data.AuthRepository

@Composable
fun LoginScreen(
    onLoggedIn: () -> Unit,
    onGoToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text("CareSync", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text("Log in to continue", fontSize = 20.sp)

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") }, singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") }, singleLine = true,
            textStyle = TextStyle(fontSize = 20.sp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        error?.let { Text(it, color = MaterialTheme.colorScheme.error, fontSize = 18.sp) }

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    error = "Please enter your email and password."
                } else {
                    error = null
                    loading = true
                    AuthRepository.signIn(email, password) { result ->
                        loading = false
                        if (result == null) onLoggedIn() else error = result
                    }
                }
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(if (loading) "Logging in..." else "Log In", fontSize = 20.sp)
        }

        TextButton(onClick = onGoToSignUp, modifier = Modifier.fillMaxWidth()) {
            Text("New here? Create an account", fontSize = 18.sp)
        }
    }
}