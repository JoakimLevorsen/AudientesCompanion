package com.a2electricboogaloo.audientes.ui.audiograms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.a2electricboogaloo.audientes.R

class AudiogramFragment : Fragment() {

    private lateinit var audiogramViewModel: AudiogramViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        audiogramViewModel =
            ViewModelProviders.of(this).get(AudiogramViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_audiograms, container, false)
        audiogramViewModel.text.observe(this, Observer {
            //textView.text = it
        })
        return root
    }
}