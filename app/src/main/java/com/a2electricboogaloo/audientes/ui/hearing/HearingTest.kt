package com.a2electricboogaloo.audientes.ui.hearing

import android.Manifest
import android.content.DialogInterface
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
       audio!!.maxAmplitude
   }

    fun getSound(){
        var maxAmplitude = audio!!.maxAmplitude

        if (maxAmplitude < 500){
            startHearingTest()
            
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Too much background noise!")
                builder.setMessage("Find a more quite place to take the hearing test")
                builder.setPositiveButton("Okay"){
                    dialog: DialogInterface?, which: Int ->
                    Toast.makeText(this, "go back to Audiogram page, and try again", Toast.LENGTH_LONG).show()
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        audio?.stop()
        audio?.reset()
        audio?.release()
    }

    fun startHearingTest(){
        val fragment = HearingTest_running_frag()
        val transaction = getSupportFragmentManager().beginTransaction()
        transaction.add(R.id.fragmentindhold, fragment)
        transaction.commit()
    }
}
