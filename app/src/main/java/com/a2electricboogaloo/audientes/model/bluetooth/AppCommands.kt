package com.a2electricboogaloo.audientes.model.bluetooth

enum class AppCommands(val hex: Byte) {
    FACTORY_RESET(0x04.toByte()),

    START_TEST(0x10.toByte()),
    CANCEL_TEST(0x12.toByte()),
    TOGGLE_TEST_PAUSE(0x13.toByte()),

    SET_ACTIVE_AUDIOGRAM(0x21.toByte()),

    SET_LEFT_VOLUME(0x34.toByte()),
    SET_RIGHT_VOLUME(0x35.toByte()),
    SET_BOTH_VOLUME(0x36.toByte()),

    ADD_PROGRAM(0x41.toByte()),
    SET_ACTIVE_PROGRAM(0x42.toByte()),

    SET_TONE_HEARD(0x50.toByte()),
    START_TONE_BOTH_EAR(0x51.toByte()),

    SET_SETTINGS(0x70.toByte()),
    GET_SETTINGS(0x71.toByte()),


}