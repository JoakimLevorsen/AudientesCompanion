package com.a2electricboogaloo.audientes.model.bluetooth

import com.a2electricboogaloo.audientes.model.types.HearingChannelData
import com.a2electricboogaloo.audientes.model.types.Program

class ProgramSerializer {
    /*
    companion object {
        fun buildProgram(rawRightChannel: HearingChannelData, audiogramID: String): (Array<Int>) -> Program?  {
            val (deviceIndex, rightChannel) = extractProgramData(rawRightChannel, true)
            return { rawLeftChannel: HearingChannelData ->
                val (otherDeviceIndex, leftChannel) = extractProgramData(rawLeftChannel, false)
                if (deviceIndex != otherDeviceIndex) {
                    throw Error("FoundDevice indexes for streams did not match")
                }
                Program(rightChannel, leftChannel, deviceIndex.toString(), audiogramID, deviceIndex)
            }
        }

        fun extractProgramData(program: Array<Int>, isRightChannel: Boolean): Pair<Int, Array<Int>> {
            if (program.size != 8) {
                throw Error("Invalid program")
            }
            if (program[0].toByte() == AppCommands.ADD_PROGRAM.hex) {
                if (
                    (isRightChannel && program[2] == 0x00/*.toByte()*/)
                    || (!isRightChannel && program[2] == 0x01/*.toByte()*/)
                ) {
                    val deviceIndex = program[1].toInt()
                    val channel = program.copyOfRange(3, 7)
                    return Pair(deviceIndex, channel)
                } else throw Error("Wrong channel")
            } else throw Error("Invalid first byte")
        }

        fun programToByteStream(program: Program): Pair<Array<Int>, Array<Int>> {
            val rightBase = Array(4) {0/*.toByte()*/}
            rightBase[0] = AppCommands.ADD_PROGRAM.hex.toInt()
            rightBase[1] = 0x01/*.toByte()*/
            val deviceIndex = (
                    program.getDeviceIndex()
                        ?: throw Error("FoundDevice index must be set before transforming to byte stream")
                    )/*.toByte()*/
            rightBase[2] = deviceIndex
            val leftBase = rightBase.clone()
            rightBase[3] = 0x00/*.toByte()*/
            leftBase[3] = 0x01/*.toByte()*/
            return Pair(rightBase + program.getRightEar(), leftBase + program.getLeftEar())
        }
    }

     */
}