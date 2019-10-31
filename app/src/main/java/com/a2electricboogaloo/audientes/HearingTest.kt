package com.a2electricboogaloo.audientes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.a2electricboogaloo.audientes.ui.hearing.hearingtest_running_frag
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.fragment.app.FragmentManager


class HearingTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hearing_test)

        val button = findViewById<Button>(R.id.startButton3)
        val fragment = hearingtest_running_frag()
        val transaction = getSupportFragmentManager().beginTransaction()

        button.setOnClickListener { transaction.replace(R.id.startButton3, fragment)}
        transaction.commit()



        /*val button = findViewById<Button>(R.id.startButton3)
        val intent = Intent(this, hearingtest_running_frag::class.java)
        button.setOnClickListener { startActivity(intent) }

         */



    }
}
