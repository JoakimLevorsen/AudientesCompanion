package com.a2electricboogaloo.audientes.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.ui.login.SignInActivity
import com.a2electricboogaloo.audientes.ui.welcome.SelectDeviceActivity
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
            //Ikke logget ind eller ingen konto
            if(isUserLoggedIn()){
                val intent = Intent(this.context, SignInActivity::class.java)
                startActivity(intent)            }
            //Allerede logget ind
            else{
                signInText.setText(R.string.sign_out)
                SignInundertext.setText(R.string.sign_out_of_audientes_companion)
            }
        }
    }
    fun isUserLoggedIn(): Boolean {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        return currentUser != null
        //indsæt kode for at tjekke om brugeren er logget ind med email og ikke kun anonymt
    }
}