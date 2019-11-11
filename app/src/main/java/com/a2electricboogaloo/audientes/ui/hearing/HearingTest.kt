package com.a2electricboogaloo.audientes.ui.hearing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.ui.hearing.Hearingtest_running_frag


class HearingTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hearing_test)

        val button = findViewById<Button>(R.id.startButton3)
        val fragment = Hearingtest_running_frag()

        button.setOnClickListener {
            val transaction = getSupportFragmentManager().beginTransaction()
            transaction.add(R.id.fragmentindhold, fragment)
        transaction.commit()}
    }
}
