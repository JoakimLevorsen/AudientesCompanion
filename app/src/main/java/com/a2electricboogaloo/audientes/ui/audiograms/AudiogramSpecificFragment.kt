package com.a2electricboogaloo.audientes.ui.audiograms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.a2electricboogaloo.audientes.R

class AudiogramSpecificFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.audiogram_specific_fragment, container, false)

        val forwardButton = root.findViewById<Button>(R.id.forwardButton)
        forwardButton.setOnClickListener {
            //TODO: Implement forward button
        }

        val backButton = root.findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            //TODO: Implement back button
        }

        return root
    }
}