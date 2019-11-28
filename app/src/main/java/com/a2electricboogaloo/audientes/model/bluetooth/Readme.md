## Flow for hearing test:
1. The user starts the test using 0x51, A tone is played in both ears.
2a. If the background is too noisy, the device responds 0x52
2b. when the user hears it they send 0x10 and the for realzies test begins.
3a. The tone was heard and the user responds 0x50
3b. The user cancelled the test using 0x12
3c. The user paused/unpaused the test using 0x13.
4. The device sends the newly active audiogram using 0x11.
5. The device sends 5 0x20's with new programs for the audiogram, where the 5th is empty.
