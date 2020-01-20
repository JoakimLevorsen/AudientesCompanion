package com.a2electricboogaloo.audientes.ui.home

import android.content.Intent
import android.media.AudioManager
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
import com.a2electricboogaloo.audientes.ui.hearing.HearingTest
import com.google.android.material.snackbar.Snackbar
import android.content.Context.AUDIO_SERVICE
import com.a2electricboogaloo.audientes.model.VolumeListener
import com.a2electricboogaloo.audientes.model.VolumeObservable
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), VolumeListener {

    private var seekBarOverall: SeekBar? = null
    private var audio: AudioManager? = null

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

        val button = root.findViewById<Button>(R.id.goButton)
        val intent = Intent(this.context, HearingTest::class.java)
        button?.setOnClickListener { startActivity(intent) }


        audio = context!!.getSystemService(AUDIO_SERVICE) as AudioManager

        seekBarOverall = root.findViewById<SeekBar>(R.id.sliderOverall)!!
        seekBarOverall!!.max = audio!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        seekBarOverall!!.progress = audio!!.getStreamVolume(AudioManager.STREAM_MUSIC)

        seekBarOverall!!.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seekBarOverall: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                audio!!.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBarOverall: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBarOverall: SeekBar) {
                var currentVolume = seekBarOverall.progress
                val snackyText = Snackbar.make(view!!, "Volume is: ${currentVolume}", Snackbar.LENGTH_SHORT)
                snackyText.show()
            }
        })

        var seekBarLeft = root.findViewById<SeekBar>(R.id.sliderLeft)!!
        var seekbarRight = root.findViewById<SeekBar>(R.id.sliderRight)!!
        seekBarLeft.isEnabled = false //Disabled, Left and Right ear volume is not implemented
        seekbarRight.isEnabled = false //Disabled, Left and Right ear volume is not implemented

        return root
    }

    override fun didChange() {
        if (audio != null ) {
            seekBarOverall?.progress = audio!!.getStreamVolume(AudioManager.STREAM_MUSIC)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        VolumeObservable.getShared().removeAsListener(this)
    }
}