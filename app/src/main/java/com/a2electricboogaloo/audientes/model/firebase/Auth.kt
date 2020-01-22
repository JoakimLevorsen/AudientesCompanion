package com.a2electricboogaloo.audientes.model.firebase

import androidx.lifecycle.MutableLiveData
import com.a2electricboogaloo.audientes.model.types.Audiogram
import com.a2electricboogaloo.audientes.model.types.Program
import com.google.firebase.auth.FirebaseAuth

class Auth {
    companion object {
        fun signInAnonymously() {
            FirebaseAuth
                .getInstance()
                .addAuthStateListener {
                    signedIn.value = it
                    if (it.currentUser != null) {
                        Audiogram.addFirebaseListener()
                        Program.addFirebaseListener()
                    }
                }
            if (FirebaseAuth.getInstance().currentUser == null) {
                FirebaseAuth
                    .getInstance()
                    .signInAnonymously()
            }
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