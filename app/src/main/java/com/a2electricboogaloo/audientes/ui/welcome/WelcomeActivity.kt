package com.a2electricboogaloo.audientes.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R

class WelcomeActivity : AppCompatActivity() {

    private var didStartActivation = false
    private var nextButton: Button? = null
    private var titleText: TextView? = null
    private var contentText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        nextButton = findViewById<Button>(R.id.button11)
        titleText = findViewById<TextView>(R.id.titleView)
        contentText = findViewById<TextView>(R.id.contentView)

        nextButton?.setOnClickListener {
            titleText?.text = "Loading..."
            contentText?.text = "Connecting to device."

            val intent = Intent(this, MainActivity::class.java)
            val lambda = { -> startActivity(intent) }

            lambda()
        }
    }
}
