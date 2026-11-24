package com.caresync.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.caresync.app.data.AuthRepository
import com.caresync.app.data.UserRole
import com.caresync.app.ui.auth.LoginScreen
import com.caresync.app.ui.auth.SignUpScreen
import com.caresync.app.ui.home.PlaceholderHome
import com.caresync.app.ui.theme.CareSyncTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CareSyncTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        CareSyncApp()
                    }
                }
            }
        }
    }
}

@Composable
fun CareSyncApp() {
    var screen by remember { mutableStateOf(if (AuthRepository.isSignedIn()) "home" else "login") }
    var role by remember { mutableStateOf<UserRole?>(null) }

    when (screen) {
        "login" -> LoginScreen(
            onLoggedIn = { screen = "home" },
            onGoToSignUp = { screen = "signup" }
        )
        "signup" -> SignUpScreen(
            onSignedUp = { chosenRole -> role = chosenRole; screen = "home" },
            onGoToLogin = { screen = "login" }
        )
        "home" -> PlaceholderHome(
            title = when (role) {
                UserRole.PATIENT -> "Patient Home"
                UserRole.CAREGIVER -> "Caregiver Dashboard"
                null -> "Home"
            },
            onSignOut = { AuthRepository.signOut(); role = null; screen = "login" }
        )
    }
}