package com.a2electricboogaloo.audientes.model.bluetooth

import com.a2electricboogaloo.audientes.model.types.Audiogram
import com.a2electricboogaloo.audientes.model.types.HearingChannelData
import java.util.*

class AudiogramSerializer {
    companion object {
        fun buildAudiogram(audiogram: Array<Int>): Audiogram {
            val leftEar: HearingChannelData
            val rightEar: HearingChannelData
            val recordDate = Date()

            if (audiogram.size != 12) {
                throw Error("Invalid audiogram")
            }
            if (audiogram[0].toByte() == AppCommands.SET_ACTIVE_AUDIOGRAM.hex) {
                leftEar = audiogram.copyOfRange(2, 6)
                rightEar = audiogram.copyOfRange(7, 11)
                return Audiogram(leftEar, rightEar, recordDate)
            } else throw Error ("Invalid first byte")
        }

        fun programToByteStream(audiogram: Audiogram): Array<Int> {
            val audiogramBase = Array(2) {0/*.toByte()*/}
            audiogramBase[0] = AppCommands.SET_ACTIVE_AUDIOGRAM.hex.toInt()
            audiogramBase[1] = 0x01/*.toByte()*/
            return audiogramBase + audiogram.left + audiogram.right
        }
    }
}