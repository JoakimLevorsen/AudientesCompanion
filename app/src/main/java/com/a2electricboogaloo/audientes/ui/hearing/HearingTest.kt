package com.a2electricboogaloo.audientes.ui.hearing

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.a2electricboogaloo.audientes.R



class HearingTest : AppCompatActivity() {

    var audio: MediaRecorder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hearing_test)

        val button = findViewById<Button>(R.id.startButton3)
        checkPermission()

        button.setOnClickListener {
            getSound()
        }
    }

   fun checkPermission() {
       if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
           != PackageManager.PERMISSION_GRANTED) {

           ActivityCompat.requestPermissions(
               this,
               arrayOf(Manifest.permission.RECORD_AUDIO),
               12345
           )
           checkEnvironmentNoise()

       } else {
        checkEnvironmentNoise()
       }
   }

   fun checkEnvironmentNoise() {
           audio = MediaRecorder()
           audio?.setAudioSource(MediaRecorder.AudioSource.MIC)
           audio?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
           audio?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
           audio?.setOutputFile("/dev/null")
           audio?.prepare()
           audio?.start()

           println("sound level is:  " + audio?.maxAmplitude)
   }

    fun getSound(){
            audio?.maxAmplitude
            println("max AMPLITUDE for AUDIO: " + audio?.maxAmplitude)
           // startHearingTest()
    }

    fun startHearingTest(){
        val fragment = HearingTest_running_frag()
        val transaction = getSupportFragmentManager().beginTransaction()
        transaction.add(R.id.fragmentindhold, fragment)
        transaction.commit()
    }
}
