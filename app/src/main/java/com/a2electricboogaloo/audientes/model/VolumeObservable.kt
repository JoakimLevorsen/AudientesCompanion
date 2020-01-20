package com.a2electricboogaloo.audientes.model

class VolumeObservable {
    companion object {
        private val instance = VolumeObservable()

        fun getShared() = instance
    }

    private var listeners: MutableList<VolumeListener> = mutableListOf()

    fun addAsListener(listener: VolumeListener) {
        listeners.add(listener)
    }

    fun removeAsListener(listener: VolumeListener) {
        listeners.remove(listener)
    }

    fun didUpdate() {
        for (listener in listeners) {
            listener.didChange()
        }
    }
}

interface VolumeListener {
    fun didChange()
}