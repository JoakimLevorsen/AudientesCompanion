package com.a2electricboogaloo.audientes.ui.hearing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.ui.audiograms.AudiogramListFragment

class HearingTestCompleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.hearing_test_complete_fragment, container, false)

        // The "see results button" is currently not supported, as the emulation of the hearing test doesn't generate a graphic audiogram
        /*val seeResultsButton = root.findViewById<Button>(R.id.seeResultsButton)
        seeResultsButton.setOnClickListener {
            activity!!
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.hearing_test_activity_frame, AudiogramListFragment())
                .commit()
        }*/

        val homeButton = root.findViewById<Button>(R.id.homeButton)
        homeButton.setOnClickListener {
            startActivity(Intent(this.context, MainActivity::class.java))
            activity!!.finish()
        }
        return root
    }
}
