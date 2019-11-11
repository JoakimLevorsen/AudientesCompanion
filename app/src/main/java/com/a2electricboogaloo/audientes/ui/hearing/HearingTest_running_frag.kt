package com.a2electricboogaloo.audientes.ui.hearing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.a2electricboogaloo.audientes.R

class Hearingtest_running_frag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_hearing_test_running_frag, container, false)

//        // TODO confirm hearing test button not implemented ::DUE TO THIS THERE IS NO NAVIGATION TO "HearingTest_complete_frag"
//        val button = root.findViewById<Button>(R.id.confirmButton4)
//
        val button2 = root.findViewById<Button>(R.id.cancelButton5)
        button2.setOnClickListener {
            activity?.finish()
        }

        return root
    }
}
