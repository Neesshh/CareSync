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
import com.caresync.app.data.UserRepository
import com.caresync.app.data.UserRole
import com.caresync.app.ui.auth.LoginScreen
import com.caresync.app.ui.auth.SignUpScreen
import com.caresync.app.ui.home.PlaceholderHome
import com.caresync.app.ui.theme.CareSyncTheme

/** App entry point. Sets the theme and shows CareSyncApp(), which handles which screen is visible. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CareSyncTheme {
                // innerPadding keeps content away from the status bar and navigation bar
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        CareSyncApp()
                    }
                }
            }
        }
    }
}

/**
 * App navigation. `screen` holds the name of the current screen; changing it switches screens.
 *
 * To add a screen:
 * 1. Create a @Composable in your own package with callbacks for navigation (onBack, onSaved, etc.)
 * 2. Add a line to the `when (screen)` block below: "yourScreen" -> YourScreen(onBack = { screen = "home" })
 * 3. Navigate to it from another screen by setting screen = "yourScreen"
 * Only ADD lines here; don't reformat others' code (avoids merge conflicts).
 */
@Composable
fun CareSyncApp() {
    // Start on Home if already logged in, otherwise Login
    var screen by remember { mutableStateOf(if (AuthRepository.isSignedIn()) "home" else "login") }
    var role by remember { mutableStateOf<UserRole?>(null) }          // null = don't know yet
    var roleChecked by remember { mutableStateOf(false) }             // true once we've asked Firestore

    when (screen) {
        "login" -> LoginScreen(
            onLoggedIn = { screen = "home" },
            onGoToSignUp = { screen = "signup" }
        )

        "signup" -> SignUpScreen(
            onSignedUp = { chosenRole -> role = chosenRole; screen = "home" },
            onGoToLogin = { screen = "login" }
        )

        "home" -> {
            // Runs once when Home appears. If we don't know the role yet (user just logged in,
            // or reopened the app), read it from Firestore. roleChecked lets us show "Loading..."
            // instead of the wrong screen while we wait.
            LaunchedEffect(Unit) {
                if (role == null) {
                    UserRepository.getRole { savedRole ->
                        role = savedRole
                        roleChecked = true
                    }
                } else {
                    roleChecked = true
                }
            }
            PlaceholderHome(
                title = when {
                    role == UserRole.PATIENT -> "Patient Home"
                    role == UserRole.CAREGIVER -> "Caregiver Dashboard"
                    !roleChecked -> "Loading..."
                    else -> "Home (no profile found)"
                },
                onSignOut = {
                    AuthRepository.signOut()
                    role = null
                    roleChecked = false
                    screen = "login"
                }
            )
        }
    }
}