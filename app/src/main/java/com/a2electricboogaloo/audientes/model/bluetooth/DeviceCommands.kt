package com.a2electricboogaloo.audientes.model.bluetooth

enum class DeviceCommands(val hex: Byte) {
    SET_AUDIOGRAM_POST_HEARING_TEST(0x11.toByte()),
    ADD_PROGRAM(0x20.toByte()),
    BACKGROUND_TOO_LOUD_FOR_TEST(0x52.toByte()),
}