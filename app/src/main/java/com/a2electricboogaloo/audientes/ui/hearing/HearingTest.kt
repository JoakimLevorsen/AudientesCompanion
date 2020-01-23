package com.a2electricboogaloo.audientes.ui.hearing

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import kotlinx.android.synthetic.main.hearing_test_activity.*
import kotlinx.coroutines.InternalCoroutinesApi



class HearingTest : AppCompatActivity() {

    var audio: MediaRecorder? = null

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        this.finish()
        return true
    }

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hearing_test_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkEnvironmentNoise()

        startButton.setOnClickListener {
            getSoundLevel()
        }
        cancelButton2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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

    @InternalCoroutinesApi
    fun getSoundLevel(){
        var maxAmplitude = audio!!.maxAmplitude
        println(maxAmplitude)

        if (maxAmplitude < 4000){
            startHearingTest()

            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Too much background noise!")
                builder.setMessage("Find a more quite place to take the hearing test")
                builder.setPositiveButton("Okay"){
                    dialog: DialogInterface?, which: Int ->
                    finish()
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        audio?.stop()
        audio?.reset()
        audio?.release()
    }

    @InternalCoroutinesApi
    fun startHearingTest(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.hearing_test_activity_frame, HearingTestRunningFragment())
            .commit()
    }
}
