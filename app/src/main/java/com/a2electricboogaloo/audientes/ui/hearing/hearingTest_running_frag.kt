package com.a2electricboogaloo.audientes.ui.hearing

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import com.a2electricboogaloo.audientes.HearingTest

import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.ui.audiograms.AudiogramFragment

class hearingtest_running_frag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_hearing_test_running_frag, container, false)

        //confirm hearing test button not implemented ::DUE TO THIS THERE IS NO NAVIGATION TO "hearingTest_complete_frag"
        val button = root.findViewById<Button>(R.id.confirmButton4)

        //Cancel hearing test button not implemented ::DUE TO THIS THERE IS NO NAVIGATION TO "hearingTest_complete_frag"
        val button2 = root.findViewById<Button>(R.id.cancelButton5)


        return root
    }
}
