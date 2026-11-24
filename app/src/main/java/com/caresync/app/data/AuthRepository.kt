package com.caresync.app.data

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

enum class UserRole { PATIENT, CAREGIVER }

object AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String, onResult: (String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email.trim(), password)
            .addOnSuccessListener { onResult(null) }
            .addOnFailureListener { e -> onResult(friendlyError(e)) }
    }

    fun signIn(email: String, password: String, onResult: (String?) -> Unit) {
        auth.signInWithEmailAndPassword(email.trim(), password)
            .addOnSuccessListener { onResult(null) }
            .addOnFailureListener { e -> onResult(friendlyError(e)) }
    }

    fun signOut() = auth.signOut()

    fun isSignedIn(): Boolean = auth.currentUser != null

    private fun friendlyError(e: Exception): String = when (e) {
        is FirebaseAuthWeakPasswordException -> "Password must be at least 6 characters."
        is FirebaseAuthUserCollisionException -> "An account with this email already exists."
        is FirebaseAuthInvalidUserException -> "No account found with this email."
        is FirebaseAuthInvalidCredentialsException -> "Email or password is incorrect."
        is FirebaseNetworkException -> "No internet connection. Please try again."
        else -> "Something went wrong. Please try again."
    }
}