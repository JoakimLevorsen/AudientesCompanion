package com.a2electricboogaloo.audientes.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signin.view.*
import kotlinx.android.synthetic.main.settings_fragment.*


class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        auth = FirebaseAuth.getInstance()

        buttonSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


        buttonSignInDone.setOnClickListener{
            signIn(
                edittext_email!!
                    .text.toString(),
                edittext_password!!.text.toString()
            )
        }

    }
    fun signIn(email : String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("login", "signInWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Authentication succeeded." + user.toString(),
                        Toast.LENGTH_SHORT).show()
                    signInText.setText("Sign Out")
                    SignInundertext.setText("Sign out of audientes companion")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
