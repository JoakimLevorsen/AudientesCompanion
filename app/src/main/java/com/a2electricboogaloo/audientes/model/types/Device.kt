package com.a2electricboogaloo.audientes.model.types

import androidx.lifecycle.MutableLiveData
import com.a2electricboogaloo.audientes.model.firebase.Auth
import com.a2electricboogaloo.audientes.model.firebase.ObjectKeys
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class Device {
    companion object {

        private val userDevices: MutableLiveData<List<Device>> = MutableLiveData()
        private var hasAddedListener = false

        fun getUserDevices(): MutableLiveData<List<Device>> {
            if (!hasAddedListener) {
                val uid = Auth.signedIn.value?.uid
                if (uid == null) {
                    throw java.lang.Error("You must be signed in to load data.")
                }
                FirebaseFirestore.getInstance()
                    .collection(ObjectKeys.DEVICES.name)
                    .whereEqualTo(ObjectKeys.OWNER.name, uid)
                    .orderBy(ObjectKeys.NAME.name)
                    .addSnapshotListener {
                            snapshot, exception ->
                        if (exception != null) {
                            throw exception
                        }
                        if (snapshot != null) {
                            userDevices.value =
                                snapshot.documents.map { Device(it) }
                        }
                    }
            }
            return userDevices
        }
    }

    private val macAddress: String
    private var deviceName: String?
    private val owner: String
    private val documentReference: DocumentReference?

    // We use MAC address as ID, so if a new person registers a device
    // it will override the previous owner.
    constructor(macAddress: String, deviceName: String?) {
        this.macAddress = macAddress
        this.deviceName = deviceName
        this.owner = Auth.getUID()
        this.documentReference = null

        this.save()
    }

    constructor(fireDoc: DocumentSnapshot) {
        val owner = fireDoc.getString(ObjectKeys.OWNER.name)
        if (owner != null) {
            this.macAddress = fireDoc.id
            this.deviceName = fireDoc.getString(ObjectKeys.NAME.name)
            this.owner = owner
            this.documentReference = fireDoc.reference
        } else {
            throw Error("Something about device document format is wrong.")
        }
    }

    fun setName(name: String?) {
        this.deviceName = name
        this.save()
    }

    private fun save() = FirebaseFirestore
        .getInstance()
        .collection(ObjectKeys.DEVICES.name)
        .document(this.macAddress)
        .set(this.toFirebase())

    private fun toFirebase() = mutableListOf(
        ObjectKeys.NAME.name to deviceName,
        ObjectKeys.OWNER.name to owner
    )
}