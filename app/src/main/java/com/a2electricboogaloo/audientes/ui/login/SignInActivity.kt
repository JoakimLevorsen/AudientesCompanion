package com.a2electricboogaloo.audientes.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.ui.settings.SettingsFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin.*


class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailSignIn: EditText
    private lateinit var passwordSignIn: EditText
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var textSignIn: TextView
    private lateinit var undertextSignIn: TextView
    private lateinit var settingsfragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        auth = FirebaseAuth.getInstance()
        settingsfragment = SettingsFragment.instance!!
        emailSignIn = findViewById(R.id.edittext_email) as EditText
        passwordSignIn = findViewById(R.id.edittext_password) as EditText
        buttonSignInDone.setOnClickListener {
            email = emailSignIn.text.toString()
            password = passwordSignIn.text.toString()
            if (password.isEmpty() || email.isEmpty()) {
                toast("email or password was blank ")
            } else {
                signIn(
                    email,
                    password
                )
            }
        }
        buttonSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("login", "signInWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext, "Authentication succeeded." + user.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    settingsfragment.changesignInText(R.string.sign_out_text.toString())
                    settingsfragment.changeSignInundertext(R.string.sign_out_undertext.toString())
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
