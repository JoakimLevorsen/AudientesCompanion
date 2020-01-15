package com.a2electricboogaloo.audientes.ui.hearing

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import org.jetbrains.anko.toast
import java.lang.Exception


class HearingTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hearing_test)

        val button = findViewById<Button>(R.id.startButton3)
        val fragment = HearingTest_running_frag()

        button.setOnClickListener {
            val transaction = getSupportFragmentManager().beginTransaction()
            transaction.add(R.id.fragmentindhold, fragment)
            transaction.commit()
        }
        //checkPermission()

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
       }
       else {
        checkEnvironmentNoise()
       }
   }

   fun checkEnvironmentNoise(){
        var audio: AudioRecord? = null
        val sampleSize = 8000
        try {
            var size = AudioRecord.getMinBufferSize(
                sampleSize,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            audio = AudioRecord(MediaRecorder.AudioSource.MIC, sampleSize, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, size)

        } catch (e: Exception){
            println("ingen lyd")
        }

        audio?.startRecording()

        if (audio == null){
            Toast.makeText(this, "du kan godt tage en test nu", Toast.LENGTH_LONG).show()

        } else if (audio != null){
            Toast.makeText(this, "du kan IKKE tage en test nu", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            audio.stop()
        }
    }

 
}
