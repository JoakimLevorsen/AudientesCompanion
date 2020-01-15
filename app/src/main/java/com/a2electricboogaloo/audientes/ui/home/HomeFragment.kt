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
import android.view.KeyEvent


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val button = root.findViewById<Button>(R.id.goButton)
        val intent = Intent(this.context, HearingTest::class.java)
        button?.setOnClickListener { startActivity(intent) }


        val audio = context!!.getSystemService(AUDIO_SERVICE) as AudioManager

        val seekBarOverall = root.findViewById<SeekBar>(R.id.sliderOverall)!!
        seekBarOverall.max = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        seekBarOverall.progress = audio.getStreamVolume(AudioManager.STREAM_MUSIC)


        //TODO TIM: THE BELOW FUNCTION IS SUPPOSED TO EXISTS SOMEWHERE AND BE GOD FUCKING DAMN OVERWRITTEN
//        override fun onKeyDown(keyCode:Int, event: KeyEvent):Boolean {
//            if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN))
//            {
//                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
//                //TODO Test: setting the slider
//                seekBarOverall.progress = audio.getStreamVolume(AudioManager.STREAM_MUSIC)
//            } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
//                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
//                //TODO Test: setting the slider
//                seekBarOverall.progress = audio.getStreamVolume(AudioManager.STREAM_MUSIC)
//            }
//            return true
//        }

        seekBarOverall.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seekBarOverall: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                audio.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI)
            }

            override fun onStartTrackingTouch(seekBarOverall: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBarOverall: SeekBar) {
                var currentVolume = seekBarOverall.progress
                val snackyText = Snackbar.make(view!!, "Volume is: ${currentVolume}", Snackbar.LENGTH_SHORT)
                snackyText.show()
            }
        })

        return root
    }
}