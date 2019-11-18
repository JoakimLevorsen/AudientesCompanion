package com.a2electricboogaloo.audientes.model.types

import com.a2electricboogaloo.audientes.model.firebase.ObjectKeys
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*

typealias HearingChannelData = Array<Byte>

class Audiogram {
    private val leftEar: HearingChannelData
    private val rightEar: HearingChannelData
    private val creationDate: Date

    val left get() = leftEar
    val right get() = rightEar
    val date get() = creationDate

    constructor(leftEar: HearingChannelData, rightEar: HearingChannelData, recordDate: Date) {
        // Check array sizes are correct
        if (leftEar.size != 5 && rightEar.size != 5) throw Error("Only exactly 5 values are allowed for each ear")
        this.leftEar = leftEar
        this.rightEar = rightEar
        this.creationDate = recordDate
    }

    constructor(fireDoc: DocumentSnapshot) {
        val leftEar = fireDoc.get(ObjectKeys.LEFT_EAR.name) as? Array<Byte>
        val rightEar = fireDoc.get(ObjectKeys.RIGHT_EAR.name) as? Array<Byte>
        val recordDate = fireDoc.getDate(ObjectKeys.CREATION_DATE.name)
        if (leftEar != null && rightEar != null && recordDate != null) {
            this.leftEar = leftEar
            this.rightEar = rightEar
            this.creationDate = recordDate
        } else {
            throw Error("Something about audiogram document format is wrong.")
        }
    }

    fun toFirebase() = mutableMapOf(
        ObjectKeys.LEFT_EAR.name to leftEar,
        ObjectKeys.RIGHT_EAR.name to rightEar,
        ObjectKeys.CREATION_DATE.name to creationDate
    )

}
