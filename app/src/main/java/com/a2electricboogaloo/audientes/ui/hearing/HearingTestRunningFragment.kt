package com.a2electricboogaloo.audientes.ui.hearing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.model.types.Audiogram
import com.a2electricboogaloo.audientes.model.types.HearingChannelData
import kotlinx.coroutines.*
import net.mabboud.android_tone_player.OneTimeBuzzer
import java.util.*

@InternalCoroutinesApi
class HearingTestRunningFragment : Fragment() {
    private lateinit var job: Job
    private val player = OneTimeBuzzer()
    private val toneDuration = 2
    private val delayDuration = 500L
    private val frequencies = intArrayOf(500, 1000, 2000, 4000, 8000)
    private val volumes = IntArray(20) {
        it * 5 + 5
    }
    private val volumeFrequencyHeardAt = HearingChannelData(5) {
        0
    }
    private var heardVolume = 0
    private var freqIndex = 0
    private var confirmButtonPressed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.hearing_test_running_fragment, container, false)
        println("Hearing test running")

        val confirmButton = root.findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            if (!confirmButtonPressed) {
                confirmButtonPressed = true
                playTone(freqIndex)
            } else {
                volumeFrequencyHeardAt[freqIndex] = (heardVolume)
                println("Frequency ${frequencies[freqIndex]} heard at volume ${volumeFrequencyHeardAt[freqIndex]}")
                increaseFrequency()
            }
        }

        val cancelButton = root.findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            activity!!
                .supportFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()
        }

        return root
    }

    private fun playTone(freqIndex: Int) {
        job = GlobalScope.launch {
            player.duration = toneDuration.toDouble()
            player.toneFreqInHz = frequencies[freqIndex].toDouble()
            heardVolume = 0

            loop@ for (volume in volumes) {
                player.volume = volume
                player.play()
                println("Playing tone for freq ${frequencies[freqIndex]} at volume $volume")

                heardVolume = volume
                delay((toneDuration * 1000) + (delayDuration))
                if (!isActive) break@loop
            }

            if (volumeFrequencyHeardAt[freqIndex] == 100) {
                println("Frequency ${frequencies[freqIndex]} not heard")
                increaseFrequency()
            }
        }
    }

    private fun stopTone() {
        //player.stop()
        job.cancel()
    }

    private fun increaseFrequency() {
        stopTone()
        freqIndex++

        if (freqIndex < frequencies.size) {
            playTone(freqIndex)
        } else {
            //TODO: java.lang.Error: You must be signed in to save data.
            Audiogram(volumeFrequencyHeardAt, volumeFrequencyHeardAt, Date())
            activity!!
                .supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentindhold, HearingTestCompleteFragment())
                .remove(this)
                .commit()
        }
    }
}