package com.a2electricboogaloo.audientes.ui.audiograms

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
import com.a2electricboogaloo.audientes.controller.AudiogramController
import com.a2electricboogaloo.audientes.controller.ProgramController
import com.a2electricboogaloo.audientes.model.types.Audiogram
import com.a2electricboogaloo.audientes.model.types.Program
import com.a2electricboogaloo.audientes.ui.programs.EditProgramActivity

class AudiogramListAdapter() :
    RecyclerView.Adapter<AudiogramListAdapter.AudiogramViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class AudiogramViewHolder(layout: LinearLayout) : RecyclerView.ViewHolder(layout)

    private var data: List<Audiogram> = listOf()


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AudiogramListAdapter.AudiogramViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.audiogram_list_item, parent, false) as LinearLayout
        return AudiogramViewHolder(cardView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: AudiogramViewHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.title)
        val checkBox = holder.itemView.findViewById<CheckBox>(R.id.checkBox)
        val removeButton = holder.itemView.findViewById<Button>(R.id.removeButton)

        val audiogram = data[position]

        name.text = audiogram.getName()
        checkBox.isChecked = AudiogramController.sharedInstance.getActiveAudiogram()?.equals(audiogram) ?: false
        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                AudiogramController.selectAudiogram(audiogram)
            } else {
                AudiogramController.selectAudiogram(null)
            }
            // We also need to update the list
            notifyDataSetChanged()
        }
        removeButton.setOnClickListener {
            audiogram.delete()
        }
    }

    fun setAudiogramsAndUpdate(audiograms: List<Audiogram>) {
        this.data = audiograms
        this.notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}