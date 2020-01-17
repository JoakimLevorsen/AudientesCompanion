package com.a2electricboogaloo.audientes.ui.hearing


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.R



class HearingTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hearing_test)

        val button = findViewById<Button>(R.id.startButton3)
        val fragment = HearingTest_running_frag()

        button.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragmentindhold, fragment)
            transaction.commit()
        }
        
    }
 
}
