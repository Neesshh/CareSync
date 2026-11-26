package com.caresync.app.data

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

/** The two kinds of accounts in CareSync. Saved to Firestore as "patient" / "caregiver". */
enum class UserRole { PATIENT, CAREGIVER }

/**
 * Handles all Firebase Authentication calls (sign up, log in, sign out).
 * Screens call this instead of Firebase directly, so all Firebase Auth code lives in one place.
 *
 * Results come back through a callback:
 *   onResult(null)      = success
 *   onResult("message") = failed, show this message to the user
 */
object AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    /** Creates a new email/password account. Firebase requires passwords of 6+ characters. */
    fun signUp(email: String, password: String, onResult: (String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email.trim(), password)
            .addOnSuccessListener { onResult(null) }
            .addOnFailureListener { e -> onResult(friendlyError(e)) }
    }

    /** Logs in an existing user. Firebase remembers the login, so they stay signed in after closing the app. */
    fun signIn(email: String, password: String, onResult: (String?) -> Unit) {
        auth.signInWithEmailAndPassword(email.trim(), password)
            .addOnSuccessListener { onResult(null) }
            .addOnFailureListener { e -> onResult(friendlyError(e)) }
    }

    /** Logs the current user out. */
    fun signOut() = auth.signOut()

    /** True if someone is already logged in (used to skip the login screen on app start). */
    fun isSignedIn(): Boolean = auth.currentUser != null

    /**
     * Turns Firebase's technical errors into short, clear messages for seniors.
     * ORDER MATTERS: WeakPassword is a type of InvalidCredentials, so it must be checked first.
     */
    private fun friendlyError(e: Exception): String = when (e) {
        is FirebaseAuthWeakPasswordException -> "Password must be at least 6 characters."
        is FirebaseAuthUserCollisionException -> "An account with this email already exists."
        is FirebaseAuthInvalidUserException -> "No account found with this email."
        is FirebaseAuthInvalidCredentialsException -> "Email or password is incorrect."
        is FirebaseNetworkException -> "No internet connection. Please try again."
        else -> "Something went wrong. Please try again."
    }
}