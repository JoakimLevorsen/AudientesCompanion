package com.a2electricboogaloo.audientes.ui.hearing

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Button
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
   }

    fun getSound(){
        audio?.maxAmplitude
        val noiseLevel: Int? = audio?.maxAmplitude


        if (noiseLevel != null && noiseLevel < 100){
            startHearingTest()
            println("new loud is:  $noiseLevel and: " + audio?.maxAmplitude)

            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Too much background noise!")
                builder.setMessage("Find a more quite place to take the hearing test")
                builder.setPositiveButton("Okay"){
                    dialog: DialogInterface?, which: Int ->
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()

                println("too loud $noiseLevel" + " sound is:  " + audio?.maxAmplitude)
            }

       while(audio?.maxAmplitude != null){
            println("input sound: $noiseLevel  and the other: " + audio?.maxAmplitude)
        }



    }

    fun startHearingTest(){
        val fragment = HearingTest_running_frag()
        val transaction = getSupportFragmentManager().beginTransaction()
        transaction.add(R.id.fragmentindhold, fragment)
        transaction.commit()
    }


}
