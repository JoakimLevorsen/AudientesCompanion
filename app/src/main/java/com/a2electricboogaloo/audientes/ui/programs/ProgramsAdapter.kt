package com.a2electricboogaloo.audientes.ui.programs

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.controller.ProgramController
import com.a2electricboogaloo.audientes.model.types.Program

class ProgramsAdapter(val container: AppCompatActivity) :
    RecyclerView.Adapter<ProgramsAdapter.ProgramsViewHolder>() {

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
    ): ProgramsAdapter.ProgramsViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.programs_listview, parent, false) as LinearLayout
        // TODO: set the view's size, margins, paddings and layout parameters
        return ProgramsViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ProgramsViewHolder, position: Int) {
        // TODO: get element from your dataset at this position
        // TODO: replace the contents of the view with that element
        val name = holder.itemView.findViewById<TextView>(R.id.programName)
        val program = data[position]
        name.text = program.getName()
        val checkBox = holder.itemView.findViewById<CheckBox>(R.id.checkBox)
        checkBox.isChecked = ProgramController.sharedInstance.getActiveProgram()?.equals(program) ?: false
        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                ProgramController.queueProgram(program)
            } else {
                ProgramController.queueProgram(null)
            }
        }
        val editButton = holder.itemView.findViewById<Button>(R.id.editButton)
        editButton.setOnClickListener {
            ProgramController.sharedInstance.setProgramToEdit(program)
            this.container.startActivity(Intent(this.container, EditProgramActivity::class.java))
        }
        val deleteButton = holder.itemView.findViewById<Button>(R.id.removeButton)
        deleteButton.setOnClickListener {
            program.delete()
        }
    }

    fun setProgramsAndUpdate(programs: List<Program>) {
        this.data = programs
        this.notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}
