package com.a2electricboogaloo.audientes.ui.hearing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import kotlinx.android.synthetic.main.hearing_test_activity.*
import kotlinx.coroutines.InternalCoroutinesApi

class HearingTest : AppCompatActivity() {

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hearing_test_activity)

        startButton.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.hearing_test_activity_frame, HearingTestRunningFragment())
                .commit()
        }

        cancelButton2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}
