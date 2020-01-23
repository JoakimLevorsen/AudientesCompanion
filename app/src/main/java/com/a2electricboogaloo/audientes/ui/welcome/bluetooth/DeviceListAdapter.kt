package com.a2electricboogaloo.audientes.ui.welcome.bluetooth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.model.types.FoundDevice
import java.util.*


class DeviceListAdapter(private val foundDeviceList: ArrayList<FoundDevice>, var context: Context) :
    RecyclerView.Adapter<DeviceListAdapter.ListeViewHolder>() {
    class ListeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var name: TextView
        var listItemClickListener: ListItemClickListener? = null

        init {
            name = itemView.findViewById(R.id.textView_deviceList)
            itemView.setOnClickListener(this)
        }

        fun setOnListItemClickListener(itemClickListener: ListItemClickListener) {
            this.listItemClickListener = itemClickListener
        }

        override fun onClick(p0: View?) {
            this.listItemClickListener!!.onListItemClickListener(p0!!, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.devicelist, parent, false)
        return ListeViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ListeViewHolder, position: Int) {
        val item = foundDeviceList[position]
        var name = item.name
        var address = item.address

        holder.name.text = "Device: " + name

        holder.setOnListItemClickListener(object :
            ListItemClickListener {
            override fun onListItemClickListener(view: View, pos: Int) {
                SelectDeviceActivity.instance!!.connect(pos)
            }
        })

    }


    override fun getItemCount(): Int {
        return foundDeviceList.size
    }
}
