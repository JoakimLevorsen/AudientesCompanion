package com.a2electricboogaloo.audientes.ui.settings.language

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import com.a2electricboogaloo.audientes.R

class Language : Activity() {

    private lateinit var uk_english: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.language_activity)


    }

}



