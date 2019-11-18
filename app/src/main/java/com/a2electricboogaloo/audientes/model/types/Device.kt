package com.a2electricboogaloo.audientes.model.types

import com.a2electricboogaloo.audientes.model.firebase.ObjectKeys
import com.google.firebase.firestore.DocumentSnapshot

data class Device (val macAddress: String, var deviceName: String?) {
    constructor(fireDoc: DocumentSnapshot) : this(
        fireDoc.id, fireDoc.get(ObjectKeys.NAME.name) as String
    )

    fun toFirebase() = mutableListOf(
        ObjectKeys.NAME.name to deviceName
    )
}