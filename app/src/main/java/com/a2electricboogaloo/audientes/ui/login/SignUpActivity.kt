package com.a2electricboogaloo.audientes.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.a2electricboogaloo.audientes.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailSignUp: EditText
    private lateinit var passwordSignUp: EditText
    private lateinit var email : String
    private lateinit  var password : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        emailSignUp = findViewById(R.id.edittext_emailSignUp) as EditText
        passwordSignUp = findViewById(R.id.edittext_passwordSignUp) as EditText

        buttonSignUpDone.setOnClickListener{
            email = emailSignUp.text.toString()
            password = passwordSignUp.text.toString()
            if(password.isEmpty()||email.isEmpty()){
                toast("email or password was blank ")
            }
            else{
                signUp(
                    email,
                    password)
            }
        }

    }
    fun toast(message : String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
    fun signUp(email : String, password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("login", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Sign up succeeded." + user.toString(),
                        Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("login", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
