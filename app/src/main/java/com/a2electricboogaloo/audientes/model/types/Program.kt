package com.a2electricboogaloo.audientes.model.types

import com.a2electricboogaloo.audientes.model.firebase.ObjectKeys
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*

class Program {
    private var leftEar: HearingChannelData
    private var rightEar: HearingChannelData
    private var creationDate: Date
    private var modificationDate: Date
    private var name: String
    private var deviceIndex: Int?
    private val audiogramID: String

    val onDevice get() = deviceIndex != null
    val modified get() = creationDate.time != modificationDate.time

    constructor(
        leftEar: HearingChannelData,
        rightEar: HearingChannelData,
        name: String,
        audiogramID: String,
        deviceIndex: Int?
    ) {
        if (leftEar.size != 5 && rightEar.size != 5) throw Error("Only exactly 5 values are allowed for each ear")
        this.leftEar = leftEar
        this.rightEar = rightEar
        this.creationDate = Date()
        this.modificationDate = creationDate
        this.name = name
        this.audiogramID = audiogramID
        this.deviceIndex = deviceIndex
    }

    constructor(fireDoc: DocumentSnapshot) {
        val leftEar = fireDoc.get(ObjectKeys.LEFT_EAR.name) as? HearingChannelData
        val rightEar = fireDoc.get(ObjectKeys.RIGHT_EAR.name) as? HearingChannelData
        val creationDate = fireDoc.getDate(ObjectKeys.CREATION_DATE.name)
        val modificationDate = fireDoc.getDate(ObjectKeys.MODIFICATION_DATE.name)
        val name = fireDoc.get(ObjectKeys.NAME.name) as? String
        val deviceIndex = fireDoc.get(ObjectKeys.DEVICE_INDEX.name) as? Int
        val audiogramID = fireDoc.get(ObjectKeys.AUDIOGRAM_ID.name) as? String
        // Note: we do not check DeviceIndex since it's allowed to be null
        if (
            leftEar != null
            && rightEar != null
            && creationDate != null
            && modificationDate != null
            && name != null
            && audiogramID != null
        ) {
            this.leftEar = leftEar
            this.rightEar = rightEar
            this.creationDate = creationDate
            this.modificationDate = modificationDate
            this.name = name
            this.deviceIndex = deviceIndex
            this.audiogramID = audiogramID
        } else {
            throw Error("Something about program document format is wrong.")
        }
    }

    fun toFirebase() = mutableMapOf(
        ObjectKeys.LEFT_EAR.name to leftEar,
        ObjectKeys.RIGHT_EAR.name to rightEar,
        ObjectKeys.CREATION_DATE.name to creationDate,
        ObjectKeys.MODIFICATION_DATE.name to modificationDate,
        ObjectKeys.NAME.name to name,
        ObjectKeys.DEVICE_INDEX to deviceIndex,
        ObjectKeys.AUDIOGRAM_ID to audiogramID
    )

    fun getLeftEar() = leftEar
    fun getRightEar() = rightEar
    fun getCreationDate() = creationDate
    fun getModificationDate() = modificationDate
    fun getName() = name
    fun getDeviceIndex() = deviceIndex
    fun getAudiogramID() = audiogramID
}