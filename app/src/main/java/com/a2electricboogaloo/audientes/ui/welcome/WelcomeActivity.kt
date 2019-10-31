package com.a2electricboogaloo.audientes.ui.welcome

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.a2electricboogaloo.audientes.R

class WelcomeActivity: Activity() {

    private var didStartActivation = false
    private var nextButton: Button? = null
    private var titleText: TextView? = null
    private var contentText: TextView? = null

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {

        nextButton = findViewById<Button>(R.id.button11)
        titleText = findViewById<TextView>(R.id.titleView)
        contentText = findViewById<TextView>(R.id.contentView)

        nextButton?.setOnClickListener {
            titleText?.text = "Loading..."
            contentText?.text = "Connecting to device."

        }

        return super.onCreateView(name, context, attrs)
    }

}