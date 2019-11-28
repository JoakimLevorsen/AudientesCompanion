package com.a2electricboogaloo.audientes.ui.hearing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.a2electricboogaloo.audientes.R

class HearingTest_complete_frag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_hearing_test_complete_frag, container, false)

//        //TODO See my results-button not implemented
//        val button = root.findViewById<Button>(R.id.button9)
//
//        //TODO Amazing home button not implemented
//        val button2 = root.findViewById<Button>(R.id.button10)

        return root
    }

}
