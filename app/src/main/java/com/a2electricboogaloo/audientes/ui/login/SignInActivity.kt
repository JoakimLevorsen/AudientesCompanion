package com.a2electricboogaloo.audientes.ui.login

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.R

class SignInActivity : AppCompatActivity() {

    private var buttonSignUp: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        buttonSignUp = findViewById(R.id.buttonSignUp)

    }
}
