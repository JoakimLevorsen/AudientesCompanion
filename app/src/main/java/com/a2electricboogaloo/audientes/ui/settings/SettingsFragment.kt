package com.a2electricboogaloo.audientes.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.ui.login.SignInActivity
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.settings_fragment.*
import com.a2electricboogaloo.audientes.ui.settings.aboutUs.AboutUs


class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var signIn: LinearLayout
    private lateinit var aboutUs: LinearLayout
    private lateinit var signInText: TextView
    private lateinit var SignInundertext: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        auth = FirebaseAuth.getInstance()
        //if logged in with email, changee sign in to sign out
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.settings_fragment, container, false)
        signIn = root.findViewById(R.id.SignIn)
        signInText = root.findViewById(R.id.signInText)
        SignInundertext = root.findViewById(R.id.SignInundertext)
        if(getAuthProviders()){
            toast("sign out")
            signInText.setText(R.string.sign_out_text)
            SignInundertext.setText(R.string.sign_out_undertext)
            signIn.setOnClickListener {
                auth.signOut()
                signInText.setText(R.string.sign_in)
                SignInundertext.setText(R.string.sign_in_to_audientes_companion)
                }
            }
        else{
            toast("sign in")
            signIn.setOnClickListener {
                val intent = Intent(context, SignInActivity::class.java)
                startActivity(intent)
            }
        }

        aboutUs = root.findViewById(R.id.aboutUs)
        aboutUs.setOnClickListener {
            val intent = Intent(context, AboutUs::class.java)
            startActivity(intent)
        }

        return root
    }
    fun getAuthProviders() :  Boolean{
        var returnBoolean : Boolean = false
        val list = auth.currentUser?.providerData
        if (list != null) {
            for(i in list){
                println(i.email)
                if(i.email!=null){
                     return true
                }
            }
        }
        else{
            returnBoolean = false
        }
        return returnBoolean
    }
    fun toast(message : String){
        Toast.makeText(this.context,message, Toast.LENGTH_LONG).show()
    }
}