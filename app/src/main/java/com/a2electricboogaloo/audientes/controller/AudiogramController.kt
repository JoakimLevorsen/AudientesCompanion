package com.a2electricboogaloo.audientes.controller

import com.a2electricboogaloo.audientes.model.types.Audiogram

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

