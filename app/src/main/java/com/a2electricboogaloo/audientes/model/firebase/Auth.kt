package com.a2electricboogaloo.audientes.model.firebase

import com.google.firebase.auth.FirebaseAuth

class Auth {
    companion object {
        val fireInstance = FirebaseAuth.getInstance()

        fun isSignedIn() = fireInstance.currentUser != null

        fun signInAnonymously() = fireInstance.signInAnonymously()
    }
}