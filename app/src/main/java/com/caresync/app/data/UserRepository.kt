package com.caresync.app.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.TimeZone

/**
 * Saves and reads user profiles in the Firestore "users" collection (see ERD v2).
 * Document ID = the user's Firebase Auth UID, so each user has exactly one profile.
 */
object UserRepository {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Creates the user's profile right after sign-up.
     * - name is saved as first / middle / last to match the Name data class
     * - phone is left out on purpose, so it loads as null ("no phone yet")
     * - createdAt uses the server's time (not the phone's) so it's always accurate
     */
    fun createProfile(name: String, email: String, role: UserRole, onResult: (String?) -> Unit) {
        // Get the logged-in user's ID. If nobody is logged in, stop and report an error.
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: return onResult("You're not signed in. Please try again.")

        val profile = hashMapOf(
            "userId" to uid,
            "role" to role.name.lowercase(),          // PATIENT → "patient" (matches ERD + Security Rules)
            "name" to splitName(name),
            "email" to email.trim(),
            "timeZone" to TimeZone.getDefault().id,   // e.g. "America/New_York", used for dose times later
            "createdAt" to FieldValue.serverTimestamp()
        )

        db.collection("users").document(uid).set(profile)
            .addOnSuccessListener { onResult(null) }
            .addOnFailureListener { onResult("Couldn't save your profile. Please try again.") }
    }

    /**
     * Looks up whether the logged-in user is a patient or caregiver.
     * Returns null if there's no profile (e.g. old test accounts) or the read fails.
     */
    fun getRole(onResult: (UserRole?) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return onResult(null)

        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                val role = when (doc.getString("role")) {
                    "patient" -> UserRole.PATIENT
                    "caregiver" -> UserRole.CAREGIVER
                    else -> null
                }
                onResult(role)
            }
            .addOnFailureListener { onResult(null) }
    }

    /**
     * Splits a full name into the ERD's first / middle / last format.
     * "Margaret Ann Smith" → { first: "Margaret", middle: "Ann", last: "Smith" }
     * "Margaret"           → { first: "Margaret", middle: "",    last: "" }
     */
    private fun splitName(fullName: String): Map<String, String> {
        val parts = fullName.trim().split(Regex("\\s+"))   // split on one or more spaces
        return mapOf(
            "first" to parts.first(),
            "middle" to parts.drop(1).dropLast(1).joinToString(" "),
            "last" to if (parts.size > 1) parts.last() else ""
        )
    }
}