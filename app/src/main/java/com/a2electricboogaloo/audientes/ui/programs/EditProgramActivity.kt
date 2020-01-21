package com.a2electricboogaloo.audientes.ui.programs

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.controller.ProgramController
import com.a2electricboogaloo.audientes.model.types.Program
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar
import kotlinx.android.synthetic.main.edit_program_activity.*

class EditProgramActivity: AppCompatActivity() {

    private lateinit var program: Program
    private var leftChannelSelected = true
    private val selectedColor = resources.getColor(R.color.primary)
    private val nonSelectedColor = resources.getColor(R.color.gray4)
    private lateinit var sliders: List<VerticalSeekBar>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_program_activity)

        program = ProgramController.sharedInstance.getProgramToEdit() ?: throw Error("Must set program to edit before EditProgram is opened")

        sliders = listOf(vol1, vol2, vol3, vol4, vol5)

        saveButton.isEnabled = false

        for ((index, slider) in sliders.withIndex()) {
            slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    val newProgress = getActiveChannel().toMutableList()
                    newProgress[index] = progress - 1000
                    if (leftChannelSelected)
                        program.setLeftEar(newProgress.toTypedArray())
                    else
                        program.setRightEar(newProgress.toTypedArray())
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
        }

        leftButton.setOnClickListener {
            leftButton.setBackgroundColor(selectedColor)
            rightButton.setBackgroundColor(nonSelectedColor)
            leftChannelSelected = true
            updateSliders()
        }

        rightButton.setOnClickListener {
            leftButton.setBackgroundColor(nonSelectedColor)
            rightButton.setBackgroundColor(selectedColor)
            leftChannelSelected = false
            updateSliders()
        }

        saveButton.setOnClickListener {
            program.save()
            saveButton.isEnabled = false
        }

        updateSliders()
    }

    private fun getActiveChannel() = if (leftChannelSelected) program.getLeftEar() else program.getRightEar()

    private fun updateSliders() {
        val channelData = getActiveChannel()
        saveButton.isEnabled = true
        for (i in 0..5) {
            // When the progress is added we need to add the possible negative numbers
            sliders[i].progress = channelData[i] + 1000
        }
    }
}