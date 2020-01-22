package com.a2electricboogaloo.audientes.controller

import android.media.audiofx.Equalizer
import com.a2electricboogaloo.audientes.model.types.Audiogram
import com.a2electricboogaloo.audientes.model.types.HearingChannelData
import com.a2electricboogaloo.audientes.model.types.Program

class AudiogramController {
    companion object {

        val sharedInstance = AudiogramController()

        fun selectAudiogram(audiogram: Audiogram?) {
            this.sharedInstance.activeAudiogram = audiogram
        }
    }

    private var activeAudiogram: Audiogram? = null

    fun getActiveAudiogram() = activeAudiogram
}

