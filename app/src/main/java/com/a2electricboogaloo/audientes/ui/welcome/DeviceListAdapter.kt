package com.a2electricboogaloo.audientes.ui.welcome

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.ui.welcome.SelectDeviceActivity.Companion.EXTRA_ADDRESS
import java.util.ArrayList
import org.jetbrains.anko.toast
import android.app.Activity


class DeviceListAdapter(private val DeviceList: ArrayList<Device>, var context: Context) :
    RecyclerView.Adapter<DeviceListAdapter.ListeViewHolder>() {

    class ListeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var name: TextView
        var listeItemClickListener : ListeItemClickListener? = null

        init {
            name = itemView.findViewById(R.id.textView_deviceList)
            itemView.setOnClickListener(this)
        }
        fun setOnListItemClickListener(itemClickListener:ListeItemClickListener){
            this.listeItemClickListener = itemClickListener
        }
        override fun onClick(p0: View?) {
            this.listeItemClickListener!!.onListeItemClickListener(p0!!, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.devicelist, parent, false)
        return ListeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListeViewHolder, position: Int) {
        val item = DeviceList[position]
        var name = item.name

        holder.name.text = "Device: "+name

        holder.setOnListItemClickListener(object :ListeItemClickListener{
            override fun onListeItemClickListener(view: View, pos: Int) {
                Toast.makeText(context,"Device: "+pos,Toast.LENGTH_LONG).show()//debug
            }
        })

    }


    override fun getItemCount(): Int {
        return DeviceList.size
    }
}
