package com.a2electricboogaloo.audientes.ui.programs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.model.types.Program

class ProgramsAdapter :
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
            .inflate(R.layout.programs_cardview, parent, false) as LinearLayout
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
    }

    fun setProgramsAndUpdate(programs: List<Program>) {
        this.data = programs
        this.notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}
