package com.a2electricboogaloo.audientes.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.a2electricboogaloo.audientes.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        return root

        val buttonSignIn = root.findViewById<Button>(R.id.button_signIn)
        buttonSignIn.setOnClickListener{

                  }
        val buttonSignUp = root.findViewById<Button>(R.id.button_signUp)
        buttonSignIn.setOnClickListener{

        }
        val buttonSignOut = root.findViewById<Button>(R.id.button_signOut)
        buttonSignIn.setOnClickListener{

        }
    }
}