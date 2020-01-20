package com.a2electricboogaloo.audientes.model.types

import androidx.lifecycle.MutableLiveData
import com.a2electricboogaloo.audientes.model.firebase.Auth
import com.a2electricboogaloo.audientes.model.firebase.ObjectKeys
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class Program {
    companion object {
        private val userPrograms: MutableLiveData<List<Program>> = MutableLiveData()
        private var hasAddedListener = false

        fun getUserPrograms(): MutableLiveData<List<Program>> {
            if (!hasAddedListener) {
                val uid = Auth.signedIn.value?.uid
                if (uid == null) {
                    throw java.lang.Error("You must be signed in to load data.")
                }
                FirebaseFirestore
                    .getInstance()
                    .collection(ObjectKeys.PROGRAMS.name)
                    .whereEqualTo(ObjectKeys.OWNER.name, uid)
                    .orderBy(ObjectKeys.NAME.name)
                    .addSnapshotListener {
                        snapshot, exception ->
                        if (exception != null) {
                            throw exception
                        }
                        if (snapshot != null) {
                            userPrograms.value =
                                snapshot.documents.map { Program(it) }
                        }
                    }
            }
            return userPrograms
        }
    }
    private var leftEar: HearingChannelData
    private var rightEar: HearingChannelData
    private var creationDate: Date
    private var modificationDate: Date
    private var name: String
    private var deviceIndex: Int?
    private val audiogramID: String
    private val owner: String
    private val documentReference: DocumentReference

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
        this.owner = Auth.getUID()
        this.documentReference = FirebaseFirestore
            .getInstance()
            .collection(ObjectKeys.PROGRAMS.name).document()

        // We need to save the object, so the data doesn't get lost on a new snapshot.
        this.save()
    }

    constructor(fireDoc: DocumentSnapshot) {
        val leftEar = fireDoc.get(ObjectKeys.LEFT_EAR.name) as? HearingChannelData
        val rightEar = fireDoc.get(ObjectKeys.RIGHT_EAR.name) as? HearingChannelData
        val creationDate = fireDoc.getDate(ObjectKeys.CREATION_DATE.name)
        val modificationDate = fireDoc.getDate(ObjectKeys.MODIFICATION_DATE.name)
        val name = fireDoc.getString(ObjectKeys.NAME.name)
        val deviceIndex = fireDoc.get(ObjectKeys.DEVICE_INDEX.name) as? Int
        val audiogramID = fireDoc.getString(ObjectKeys.AUDIOGRAM_ID.name)
        val owner = fireDoc.getString(ObjectKeys.OWNER.name)
        // Note: we do not check DeviceIndex since it's allowed to be null
        if (
            leftEar != null
            && rightEar != null
            && creationDate != null
            && modificationDate != null
            && name != null
            && audiogramID != null
            && owner != null
        ) {
            this.leftEar = leftEar
            this.rightEar = rightEar
            this.creationDate = creationDate
            this.modificationDate = modificationDate
            this.name = name
            this.deviceIndex = deviceIndex
            this.audiogramID = audiogramID
            this.owner = owner
            this.documentReference = fireDoc.reference
        } else {
            throw Error("Something about program document format is wrong.")
        }
    }

    private fun save() = this.documentReference.set(this.toFirebase())

    private fun toFirebase() = mutableMapOf(
        ObjectKeys.LEFT_EAR.name to leftEar,
        ObjectKeys.RIGHT_EAR.name to rightEar,
        ObjectKeys.CREATION_DATE.name to creationDate,
        ObjectKeys.MODIFICATION_DATE.name to modificationDate,
        ObjectKeys.NAME.name to name,
        ObjectKeys.DEVICE_INDEX to deviceIndex,
        ObjectKeys.AUDIOGRAM_ID to audiogramID,
        ObjectKeys.OWNER.name to owner
    )

    fun getLeftEar() = leftEar
    fun getRightEar() = rightEar
    fun getCreationDate() = creationDate
    fun getModificationDate() = modificationDate
    fun getName() = name
    fun getDeviceIndex() = deviceIndex
    fun getAudiogramID() = audiogramID
}