package com.a2electricboogaloo.audientes.ui.home

import android.content.Context.AUDIO_SERVICE
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.controller.ProgramController
import com.a2electricboogaloo.audientes.model.VolumeListener
import com.a2electricboogaloo.audientes.model.VolumeObservable
import com.a2electricboogaloo.audientes.ui.programs.EditProgramActivity
import com.a2electricboogaloo.audientes.ui.programs.ProgramsActivity
import kotlinx.android.synthetic.main.home_fragment.*
import com.a2electricboogaloo.audientes.ui.hearing.HearingTest
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(), VolumeListener {

    private var seekBarOverall: SeekBar? = null
    private var audio: AudioManager? = null
    private var mediaPlayer: MediaPlayer? = null
    private var testAudioPlaying = false
    private var mediaPlayerPrepared = false

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.home_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        VolumeObservable.getShared().addAsListener(this)

        val goButton = root.findViewById<Button>(R.id.goButton)
        goButton.setOnClickListener {
            startActivity(Intent(this.context, HearingTest::class.java))
        }

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setDataSource(resources.openRawResourceFd(R.raw.in_the_hall_of_the_mountain_king))
            isLooping = true
            prepare()
        }
        mediaPlayerPrepared = true
        ProgramController.applySelectedProgram(mediaPlayer!!.audioSessionId)

        val playButton = root.findViewById<Button>(R.id.playPauseTestAudioButton)
        playButton.setOnClickListener {
            if (playButton.text == getString(R.string.play)) {
                if (!mediaPlayerPrepared) {
                    mediaPlayer?.prepare()
                    mediaPlayerPrepared = true
                }
                mediaPlayer?.start()
                testAudioPlaying = true
                playButton.text = getString(R.string.stop)
            } else if (playButton.text == getString(R.string.stop)) {
                mediaPlayer?.stop()
                testAudioPlaying = false
                mediaPlayerPrepared = false
                playButton.text = getString(R.string.play)
            }
        }

        audio = context!!.getSystemService(AUDIO_SERVICE) as AudioManager

        seekBarOverall = root.findViewById(R.id.sliderOverall)!!
        seekBarOverall!!.max = audio!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        seekBarOverall!!.progress = audio!!.getStreamVolume(AudioManager.STREAM_MUSIC)

        seekBarOverall!!.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBarOverall: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                audio!!.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBarOverall: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBarOverall: SeekBar) {
                val currentVolume = seekBarOverall.progress
                val snackyText =
                    Snackbar.make(view!!, "Volume is: $currentVolume", Snackbar.LENGTH_SHORT)
                snackyText.show()
            }
        })

        val seekBarLeft = root.findViewById<SeekBar>(R.id.sliderLeft)!!
        val seekBarRight = root.findViewById<SeekBar>(R.id.sliderRight)!!
        seekBarLeft.isEnabled = false //Disabled, Left and Right ear volume is not implemented
        seekBarRight.isEnabled = false //Disabled, Left and Right ear volume is not implemented

        val programIntent = Intent(this.activity, ProgramsActivity::class.java)
        root.findViewById<Button>(R.id.moreButton)?.setOnClickListener { startActivity(programIntent) }

        return root
    }

    override fun didChange() {
        if (audio != null) {
            seekBarOverall?.progress = audio!!.getStreamVolume(AudioManager.STREAM_MUSIC)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        VolumeObservable.getShared().removeAsListener(this)
        mediaPlayer?.release()
        mediaPlayer = null
    }
}