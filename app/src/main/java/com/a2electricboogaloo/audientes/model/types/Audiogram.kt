package com.a2electricboogaloo.audientes.model.types

import androidx.lifecycle.MutableLiveData
import com.a2electricboogaloo.audientes.controller.ProgramController
import com.a2electricboogaloo.audientes.model.firebase.Auth
import com.a2electricboogaloo.audientes.model.firebase.ObjectKeys
import com.google.firebase.firestore.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

typealias HearingChannelData = Array<Int>

class Audiogram {
    companion object {
        private val userAudiograms: MutableLiveData<List<Audiogram>> = MutableLiveData()
        private var hasAddedListener = false
        private var loaded = false

        fun hasLoaded() = loaded

        fun getUserAudiograms(): MutableLiveData<List<Audiogram>> {
            if (!hasAddedListener) {
                FirebaseFirestore
                    .getInstance()
                    .collection(ObjectKeys.AUDIOGRAMS.name)
                    .whereEqualTo(ObjectKeys.OWNER.name, Auth.getUID())
                    .orderBy(ObjectKeys.CREATION_DATE.name, Query.Direction.DESCENDING)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null) {
                            throw exception
                        }
                        if (snapshot != null) {
                            loaded = true
                            userAudiograms.value =
                                snapshot.documents.map { Audiogram(it) }
                        }
                    }
            }
            return userAudiograms
        }
    }

    private val leftEar: HearingChannelData
    private val rightEar: HearingChannelData
    private val creationDate: Date
    private val documentReference: DocumentReference
    private val owner: String

    val left get() = leftEar
    val right get() = rightEar
    val date get() = creationDate
    val id get() = documentReference.id

    constructor(
        leftEar: HearingChannelData,
        rightEar: HearingChannelData,
        recordDate: Date,
        createPrograms: Boolean = true
    ) {
        // Check array sizes are correct
        if (leftEar.size != 5 && rightEar.size != 5) throw Error("Only exactly 5 values are allowed for each ear")
        this.leftEar = leftEar
        this.rightEar = rightEar
        this.creationDate = recordDate
        this.owner = Auth.getUID()
        this.documentReference = FirebaseFirestore
            .getInstance()
            .collection(ObjectKeys.AUDIOGRAMS.name)
            .document()

        if (createPrograms) {
            ProgramController.generatePrograms(this)
        }

        // We need to save the object, so the data doesn't get lost on a new snapshot.
        this.save()
    }

    constructor(fireDoc: DocumentSnapshot) {
        val leftEar = (fireDoc.get(ObjectKeys.LEFT_EAR.name) as? List<Int>)?.toTypedArray()
        val rightEar = (fireDoc.get(ObjectKeys.RIGHT_EAR.name) as? List<Int>)?.toTypedArray()
        val recordDate = fireDoc.getDate(ObjectKeys.CREATION_DATE.name)
        val owner = fireDoc.getString(ObjectKeys.OWNER.name)
        if (
            leftEar != null
            && rightEar != null
            && recordDate != null
            && owner != null
        ) {
            this.leftEar = leftEar
            this.rightEar = rightEar
            this.creationDate = recordDate
            this.owner = owner
            this.documentReference = fireDoc.reference
        } else {
            throw Error("Something about audiogram document format is wrong.")
        }
    }

    private fun save() = this.documentReference.set(this.toFirebase())

    fun newestAudiogram(): Audiogram? {
        if (!Audiogram.loaded) return null
        val list = Audiogram.userAudiograms.value?.sortedByDescending { it.creationDate }
        if (list == null || list.size == 0) return null
        return list[0]
    }

    fun amountOfAudiograms(): Int? {
        if (!Audiogram.loaded || Audiogram.userAudiograms.value == null) return null
        return Audiogram.userAudiograms.value!!.size
    }


    fun delete(didFinish: (state: Boolean) -> Unit) {
        val task = this.documentReference.delete()
        task.addOnSuccessListener { didFinish(true) }
        task.addOnFailureListener { didFinish(false) }
        // This code was used when programs were tied to audiograms, this is no longer the case
        /*val id = this.id
        val db = FirebaseFirestore.getInstance()
        val thisRef = this.documentReference
        GlobalScope.launch {
            val request = db.collection("programs")
                .whereEqualTo("audiogramID", id)
                .get()
            request.onSuccessTask {
                val batch = db.batch()
                batch.delete(thisRef)
                for (program in it?.documents ?: listOf()) {
                    batch.delete(program.reference)
                }
                val commit = batch.commit()
                commit.addOnFailureListener { didFinish(false); println("Delete failed with $it") }
                commit.addOnSuccessListener { didFinish(true) }
            }
            request.addOnFailureListener{
                didFinish(false)
                println("Delete failed with $it")
            }

        }*/
    }

    private fun toFirebase(): Map<String, Any> = mutableMapOf(
        ObjectKeys.LEFT_EAR.name to leftEar.toList(),
        ObjectKeys.RIGHT_EAR.name to rightEar.toList(),
        ObjectKeys.CREATION_DATE.name to creationDate,
        ObjectKeys.OWNER.name to owner
    )

}
