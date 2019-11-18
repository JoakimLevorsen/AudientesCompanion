package com.a2electricboogaloo.audientes.ui.welcome

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R

class WelcomeActivity : AppCompatActivity() {

    private var didStartActivation = false
    private var nextButton: Button? = null
    private var titleText: TextView? = null
    private var contentText: TextView? = null
    private fun PackageManager.missingSystemFeature(name: String): Boolean = !hasSystemFeature(name)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        nextButton = findViewById<Button>(R.id.button11)
        titleText = findViewById<TextView>(R.id.titleView)
        contentText = findViewById<TextView>(R.id.contentView)

        packageManager.takeIf { it.missingSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) }
            ?.also {
                Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show()

                nextButton?.setOnClickListener {
                    titleText?.text = "Loading..."
                    contentText?.text = "Connecting to device."

                    val intent = Intent(this, MainActivity::class.java)
                    val lambda = { -> startActivity(intent) }

                    lambda()
                }
            }


    }
}
