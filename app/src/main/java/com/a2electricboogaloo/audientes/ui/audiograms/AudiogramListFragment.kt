package com.a2electricboogaloo.audientes.ui.audiograms

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.model.types.Audiogram
import com.a2electricboogaloo.audientes.ui.hearing.HearingTest

class AudiogramListFragment : Fragment() {

    private lateinit var audiogramViewModel: AudiogramViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        audiogramViewModel =
            ViewModelProviders.of(this).get(AudiogramViewModel::class.java)
        val root = inflater.inflate(R.layout.audiogram_list_fragment, container, false)
        audiogramViewModel.text.observe(this, Observer {
            //textView.text = it
        })
        val button = root.findViewById<Button>(R.id.newTestButton)
        button?.setOnClickListener { startActivity(Intent(this.context, HearingTest::class.java)) }

        val audiogramList = root.findViewById<RecyclerView>(R.id.audiogramList)
        val context = this.context
        val viewAdapter = AudiogramListAdapter()

        Audiogram.getUserAudiograms().observe(this, Observer { audiograms ->
            viewAdapter.setAudiogramsAndUpdate(audiograms)
        })

        audiogramList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
        }

        return root
    }
}