package com.a2electricboogaloo.audientes.ui.welcome

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import kotlinx.android.synthetic.main.activity_select_device.*

class SelectDeviceActivity: AppCompatActivity(), ListeItemClickListener {

    private var recyclerView: RecyclerView? = null
    private var adapter: DeviceListAdapter? = null
    internal var list = java.util.ArrayList<Device>()
    internal var BTDevicelist : ArrayList<BluetoothDevice> = ArrayList()
    private var m_bluetoothAdapter: BluetoothAdapter? = null

    private val REQUEST_ENABLE_BLUETOOTH = 1
    private val mhandler: Handler? = null
    private val runnable: Runnable? = null

    companion object {
        var instance: SelectDeviceActivity? = null

        fun setGlobalInstance(activity: SelectDeviceActivity) {
            instance = activity
        }

        val EXTRA_ADDRESS: String = "Device_address"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setGlobalInstance(this)

        setContentView(R.layout.activity_select_device)

        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
        Toast.makeText(this, "bluetooth seaching", Toast.LENGTH_LONG).show()

        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(m_bluetoothAdapter == null) {
            Toast.makeText(this, "device doesn't support bluetooth", Toast.LENGTH_LONG).show()
            return
        }
        if(!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)

        }

        var m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        if (!m_pairedDevices.isEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                BTDevicelist.add(device)
                list.add(Device(device.name, device.address))
                Log.i("device", "" + device)
            }
        } else {
            Toast.makeText(this, "no paired bluetooth devices found", Toast.LENGTH_LONG).show()
        }
        adapter = DeviceListAdapter(list, applicationContext)
        var layoutManager = LinearLayoutManager(applicationContext)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            12345
        )

        button_devicelist.setOnClickListener{discoverDevices()}
    }

    private fun discoverDevices() {
        m_bluetoothAdapter!!.startDiscovery()
    }


    fun connect(position: Int){
        val device: BluetoothDevice = BTDevicelist[position]
        val address: String = device.address

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_ADDRESS, address)
        startActivity(intent)
    }

    override fun onListeItemClickListener(view: View, pos: Int) {
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show()
        connect(pos)
    }

    private val receiver = object : BroadcastReceiver(){

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "bluetooth devices found", Toast.LENGTH_LONG).show()
            val action: String = intent.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                    BTDevicelist.add(device)


                    if(device.name==null) {
                        list.add(Device(device.address, device.address))
                    }
                    else{
                        list.add(Device(device.name, device.address))
                    }
                    adapter!!.notifyDataSetChanged()
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (m_bluetoothAdapter!!.isEnabled) {
                    Toast.makeText(this, "Bluetooth has been enabled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Bluetooth has been disabled", Toast.LENGTH_LONG).show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Bluetooth enabling has been canceled", Toast.LENGTH_LONG).show()
            }
        }
    }
}
