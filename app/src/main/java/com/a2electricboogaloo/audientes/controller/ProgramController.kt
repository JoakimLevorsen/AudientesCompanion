package com.a2electricboogaloo.audientes.controller

import android.media.audiofx.Equalizer
import com.a2electricboogaloo.audientes.model.types.Audiogram
import com.a2electricboogaloo.audientes.model.types.HearingChannelData
import com.a2electricboogaloo.audientes.model.types.Program

class ProgramController {
    companion object {

        val sharedInstance = ProgramController()

        fun useProgram(program: Program, toAudioSessionID: Int) {
            val eq = Equalizer(1, toAudioSessionID)
            // Now we get the number of bands we need to generate
            sharedInstance.activeProgram = program
            val left = splitProgramsToLevel(program.getLeftEar(), eq.numberOfBands.toInt())
            val right = splitProgramsToLevel(program.getRightEar(), eq.numberOfBands.toInt())
            left.forEachIndexed { index, i ->
                eq.setBandLevel(index.toShort(), i.toShort())
            }
            eq.enabled = true
        }

        fun applySelectedProgram(audioSessionID: Int) {
            val program = sharedInstance.activeProgram
            if (program != null) {
                useProgram(program, audioSessionID)
            }
        }

        fun queueProgram(program: Program?) {
            sharedInstance.activeProgram = program
        }

        fun generatePrograms(audiogram: Audiogram) {
            val audiID = audiogram.id
            // We create two programs parabola programs where mids are higher or lower than the rest.
            Program(
                "Auto 1",
                0,
                arrayOf(-1000, 0, 1000, 0, -1000),
                arrayOf(-1000, 0, 1000, 0, -1000),
                audiID
            )
            Program(
                "Auto 2",
                1,
                arrayOf(1000, 0, -1000, 0, 1000),
                arrayOf(1000, 0, -1000, 0, 1000),
                audiID
            )
            // Then we make one with a higher bass, and one with a higher treble
            Program(
                "Auto 3",
                2,
                arrayOf(-1000, -500, 0, 500, 1000),
                arrayOf(-1000, -500, 0, 500, 1000),
                audiID
            )
            Program(
                "Auto 4",
                3,
                arrayOf(1000, 500, 0, 0, -1000),
                arrayOf(-1000, 0, 1000, 0, -1000),
                audiID
            )
        }

        private fun splitProgramsToLevel(program: HearingChannelData, levels: Int): List<Int> {
            if (levels === program.size) return program.toList()
            // First we add the last and first elements, since they're unchanged
            val start = program[0]
            val end = program[4]
            // Now we imagine straight lines that go from n to n + 1 with the base data.
            // That creates one contiguos line with a total length of 4, for any amount of bands we split this line
            // Into sections of (4 / (n - 1)) samples to get the new equalizer.
            val sampleCoordinates = emptyList<Int>().toMutableList()
            // How much do we increase between samples
            val sample = levels / (levels - 1)
            for (i in sample until (levels - 1) step sample) {
                val specificSample = sampleAtIndex(program, (i / (levels.toDouble() - 1)) * 4)
                sampleCoordinates.add(specificSample)
            }
            val returnable = mutableListOf(start)
            returnable.addAll(sampleCoordinates)
            returnable.add(end)

            return returnable.toList()
        }

        private fun sampleAtIndex(base: HearingChannelData, samplePoint: Double): Int {
            val lowerIndex = Math.floor(samplePoint).toInt()
            if (lowerIndex >= 5) throw Error("Out of bounds")
            // We get the intercept (b)
            val intersect = base[lowerIndex]
            val destination = base[lowerIndex + 1]
            // Slope of the line
            val slope = destination - intersect
            // Now we get the amount of x we need to go in
            val x = samplePoint - lowerIndex
            // Now we can get our sample
            return (slope * x + intersect).toInt()
        }
    }

    private var activeProgram: Program? = null

    private var programToEdit: Program? = null

    fun getActiveProgram() = activeProgram
    fun getProgramToEdit() = programToEdit
    fun setProgramToEdit(program: Program?) {
        programToEdit = program
    }
}

