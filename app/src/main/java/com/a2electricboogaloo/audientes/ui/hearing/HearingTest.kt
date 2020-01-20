package com.a2electricboogaloo.audientes.ui.hearing

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            stop()
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
       if (audio == null) {
           audio?.setAudioSource(MediaRecorder.AudioSource.MIC)
           audio?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
           audio?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
           audio?.setOutputFile("/dev/null")
           audio?.prepare()
           audio?.start()

            println("checking sound level")
       } else println("no audio input")
/*
      else if (audio != null) {

       } else {
           println("NO AUDIO INPUT")
       }

 */

       //audio?.startRecording()

/*
       if (audio == null) {
           Toast.makeText(this, "DER ER INGEN LYD, du kan godt tage en test nu", Toast.LENGTH_LONG)
               .show()
           audio?.stop()
           println("HER ER RESULTATET" + audio?.release())

       } else if (audio != null) {
           Toast.makeText(this, "du kan IKKE tage en test nu", Toast.LENGTH_LONG).show()

           println("RES FRA MEDIArecorder" + audio?.maxAmplitude)

           // val intent = Intent(this, MainActivity::class.java)
           //startActivity(intent)

           audio?.stop()
           // println( "HER ER DET 2. RESULTAT " + audio?.release())
       }

 */
   }

    fun stop(){

            audio?.stop()
            audio?.release()
            audio = null
            println("stopping recorder")

    }

    fun getSound(){

            audio?.maxAmplitude
            println("max AMPLITUDE for AUDIO: " + audio?.maxAmplitude)

    }

    fun startHearingTest(){
        val fragment = HearingTest_complete_frag()
        val transaction = getSupportFragmentManager().beginTransaction()
        transaction.add(R.id.fragmentindhold, fragment)
        transaction.commit()
    }
 
}
