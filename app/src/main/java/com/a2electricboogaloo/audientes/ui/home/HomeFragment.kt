package com.a2electricboogaloo.audientes.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.ui.hearing.HearingTest
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*

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

        val seekBarOverall = root.findViewById<SeekBar>(R.id.sliderOverall)!!
        seekBarOverall.max = 200
        seekBarOverall.progress = 100 //Default progress value



        seekBarOverall.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBarOverall: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                //TODO write custom code for progress is changed
            }

            override fun onStartTrackingTouch(seekBarOverall: SeekBar) {
                //TODO write custom code for progress is started
            }

            override fun onStopTrackingTouch(seekBarOverall: SeekBar) {
                //TODO write custom code for progress is stopped
                val snackyText = Snackbar.make(view!!, "Volume is: ${seekBarOverall.progress / 10}", Snackbar.LENGTH_SHORT)
                snackyText.show()
            }
        })


        return root
    }
}