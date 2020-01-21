package com.a2electricboogaloo.audientes.model.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class Auth  {
    companion object {
        fun signInAnonymously() {
            FirebaseAuth
                .getInstance()
                .addAuthStateListener { signedIn.value = it }
            FirebaseAuth
                .getInstance()
                .signInAnonymously()
        }

        fun getUID(): String {
            val uid = signedIn.value?.uid
            if (uid == null) {
                throw java.lang.Error("You must be signed in to save data.")
            }
            return uid
        }

        val signedIn: MutableLiveData<FirebaseAuth> = MutableLiveData()
    }
}