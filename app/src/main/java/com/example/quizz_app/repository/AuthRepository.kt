package com.example.quizz_app.repository

import android.content.Context
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth



class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    fun sendLoginLink(email: String, context: Context, onSuccess: () -> Unit, onError: (String) -> Unit) {

        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://quizapp.page.link/login") // must be whitelisted
            .setHandleCodeInApp(true)
            .setAndroidPackageName(
                context.packageName,
                true,
                null
            )
            .build()

        auth.sendSignInLinkToEmail(email, actionCodeSettings)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Error") }
    }

    fun verifyLink(email: String, link: String, onSuccess: () -> Unit, onError: (String) -> Unit) {

        if (!auth.isSignInWithEmailLink(link)) {
            onError("Invalid link")
            return
        }

        auth.signInWithEmailLink(email, link)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Verification failed") }
    }
}


