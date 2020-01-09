package com.a2electricboogaloo.audientes.ui.welcome

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
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
        registerBroadcast()
        isBluetoothSupported()
        enableBluetooth()
        addBondedDevices()
        makeList()
        accessCoarseLocation()
        discoverDevices()
        button_devicelist.setOnClickListener{discoverDevices()}
    }

    private val receiver = object : BroadcastReceiver(){

        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "bluetooth devices found", Toast.LENGTH_LONG).show()
            val action: String = intent.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
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

    private fun checkDevice() {
        //add method to onreceive

        //add code that checks whether a device is already on the list.{
        // if true dont add unless the name is the same as the address{
        // if the name is available update it}
        //}
    }

    private fun registerBroadcast(){
        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
        Toast.makeText(this, "bluetooth searching", Toast.LENGTH_LONG).show()
    }

    private fun accessCoarseLocation(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            12345
        )
    }

    private fun addBondedDevices(){
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
    }

    private fun enableBluetooth(){
        if(!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }
    }

    private fun isBluetoothSupported(){
        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(m_bluetoothAdapter == null) {
            Toast.makeText(this, "device doesn't support bluetooth", Toast.LENGTH_LONG).show()
            return
        }
    }

    private fun makeList(){
        adapter = DeviceListAdapter(list, applicationContext)
        var layoutManager = LinearLayoutManager(applicationContext)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
    }

    private fun discoverDevices() {
        m_bluetoothAdapter!!.startDiscovery()
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun connect(position: Int){
        var device: BluetoothDevice = BTDevicelist[position]
        val address: String = device.address
        device  = m_bluetoothAdapter!!.getRemoteDevice(address)
        device.createBond()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_ADDRESS, address)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onListeItemClickListener(view: View, pos: Int) {
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show()
        connect(pos)
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

    override fun onDestroy() {
        super.onDestroy()
        m_bluetoothAdapter!!.cancelDiscovery()
        unregisterReceiver(receiver)
    }
}
