package com.a2electricboogaloo.audientes.ui.settings

import android.content.Intent
import android.os.Bundle
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
import com.a2electricboogaloo.audientes.ui.settings.aboutUs.AboutUs
import com.a2electricboogaloo.audientes.ui.settings.language.Language
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var signIn: LinearLayout
    private lateinit var aboutUs: LinearLayout
    private lateinit var signInText: TextView
    private lateinit var SignInundertext: TextView
    private lateinit var deleteData: LinearLayout
    private lateinit var changeLanguage: LinearLayout

    companion object {
        var instance: SettingsFragment? = null
        fun setGlobalInstance(fragment: SettingsFragment) {
            instance = fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        setGlobalInstance(this)
        auth = FirebaseAuth.getInstance()
        //if logged in with email, changee sign in to sign out
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.settings_fragment, container, false)
        signIn = root.findViewById(R.id.SignIn)
        signInText = root.findViewById(R.id.signInText)
        SignInundertext = root.findViewById(R.id.SignInundertext)
        deleteData = root.findViewById(R.id.delete)
        changeLanguage = root.findViewById(R.id.language)
        deleteData.setOnClickListener {
            toast("Delete data is not supported")
        }
        changeLanguage.setOnClickListener {
            val intent = Intent(context, Language::class.java)
            startActivity(intent)
        }
        if (getAuthProviders()) {
            signInText.setText(R.string.sign_out_text)
            SignInundertext.setText(R.string.sign_out_undertext)
            signIn.setOnClickListener {
                auth.signOut()
                signInText.setText(R.string.sign_in)
                SignInundertext.setText(R.string.sign_in_to_audientes_companion)
                signIn.setOnClickListener {
                    val intent = Intent(context, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
        } else {
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

    fun changesignInText(text: String?) {
        signInText.setText(text)
    }

    fun changeSignInundertext(text: String?) {
        SignInundertext.setText(text)
    }

    fun getAuthProviders(): Boolean {
        var returnBoolean: Boolean = false
        val list = auth.currentUser?.providerData
        if (list != null) {
            for (i in list) {
                println(i.email)
                if (i.email != null) {
                    return true
                }
            }
        } else {
            returnBoolean = false
        }
        toast(returnBoolean.toString())
        return returnBoolean
    }

    fun toast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }
}