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
import kotlinx.android.synthetic.main.fragment_settings.*


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

        SignIn.setOnClickListener{
            //Ikke logget ind
            if(true){
                signInText.setText(R.string.sign_in)
                SignInundertext.setText(R.string.sign_in_to_audientes_companion)
            }
            //Allerede logget ind
            else{
                signInText.setText(R.string.sign_out)
                SignInundertext.setText(R.string.sign_out_of_audientes_companion)
            }
        }
    }
}