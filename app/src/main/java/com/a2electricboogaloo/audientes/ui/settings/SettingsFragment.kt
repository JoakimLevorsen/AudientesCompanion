package com.a2electricboogaloo.audientes.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.ui.login.SignInActivity
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var signIn: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.settings_fragment, container, false)
        signIn = root.findViewById(R.id.SignIn)
        signIn.setOnClickListener {
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
        }
        return root
    }
}