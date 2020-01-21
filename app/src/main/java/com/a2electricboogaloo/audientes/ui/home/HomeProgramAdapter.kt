package com.a2electricboogaloo.audientes.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.controller.ProgramController
import com.a2electricboogaloo.audientes.model.types.Program
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeProgramAdapter(
    private val context: Context,
    private val getAudioSessionID: () -> Int?,
    private val forceReload: (adapter: HomeProgramAdapter) -> Unit
) :
    RecyclerView.Adapter<HomeProgramAdapter.ProgramsViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ProgramsViewHolder(layout: LinearLayout) : RecyclerView.ViewHolder(layout)

    private var data: List<Program> = listOf()

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeProgramAdapter.ProgramsViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_program_list, parent, false) as LinearLayout
        return ProgramsViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ProgramsViewHolder, position: Int) {
        // Get some colors
        val selectedColor = ContextCompat.getColor(context, R.color.primary)
        val nonSelectedColor = ContextCompat.getColor(context, R.color.gray4)

        val program = data[position]
        val wasChecked = ProgramController
            .sharedInstance
            .getActiveProgram()
            ?.equals(program) == true
        val toggleButton = holder.itemView.findViewById<Button>(R.id.toggleButton)
        toggleButton.setBackgroundColor(
            if (wasChecked) {
                selectedColor
            } else nonSelectedColor
        )
        toggleButton.text = program.getName()
        toggleButton.setOnClickListener {
            println("Did press")
            val id = getAudioSessionID()
            if (id == null) {
                if (!wasChecked) {
                    ProgramController.queueProgram(program)
                } else {
                    ProgramController.queueProgram(null)
                }
            } else {
                if (!wasChecked) {
                    ProgramController.useProgram(program, id)
                } else {
                    ProgramController.removeEqualizer()
                }
            }
            forceReload(this)
        }
    }

    fun setProgramsAndUpdate(programs: List<Program>) {
        if (programs.size <= 6) {
            this.data = programs
        } else {
            val latestPrograms = programs.sortedByDescending { it.modified }.subList(0, 5)
            // If there are no active programs, we just use the latest
            val active = ProgramController.sharedInstance.getActiveProgram()
            if (active == null) {
                this.data = latestPrograms
            } else {
                // If the active program is in our list, we use it
                if (latestPrograms.any { active.equals(it) }) {
                    this.data = latestPrograms
                } else {
                    val programsToShow = mutableListOf(active)
                    programsToShow.addAll(1, latestPrograms.subList(0, 4))
                    this.data = programsToShow
                }
            }
        }
        this.notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}
