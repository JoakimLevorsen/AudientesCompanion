package com.a2electricboogaloo.audientes.ui.welcome

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.model.firebase.Auth
import com.a2electricboogaloo.audientes.ui.welcome.bluetooth.SelectDeviceActivity

class WelcomeActivity : AppCompatActivity() {
    private var didStartActivation = false
    private var nextButton: Button? = null
    private var titleText: TextView? = null
    private var contentText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.welcome_activity)

        Auth.signInAnonymously()

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            12345
        )

        nextButton = findViewById(R.id.button11)
        titleText = findViewById(R.id.titleView)
        contentText = findViewById(R.id.welcomeText)
        nextButton?.setOnClickListener {
            titleText?.text = "Loading..."
            contentText?.text = "Connecting to device."

            //indsæt bluetooth check
            val intent = Intent(this, SelectDeviceActivity::class.java)
            //val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
