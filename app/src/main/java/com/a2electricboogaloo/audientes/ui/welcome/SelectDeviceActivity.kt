package com.a2electricboogaloo.audientes.ui.welcome

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import kotlinx.android.synthetic.main.activity_select_device.*

class SelectDeviceActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var adapter: DeviceListAdapter? = null
    internal var btdevicelist: ArrayList<BluetoothDevice> = ArrayList()
    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private val REQUEST_ENABLE_BLUETOOTH = 1
    private var connected = false

    companion object {
        var instance: SelectDeviceActivity? = null
        fun setGlobalInstance(activity: SelectDeviceActivity) {
            instance = activity
        }
        const val EXTRA_ADDRESS: String = "Device_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setGlobalInstance(this)
        setContentView(R.layout.activity_select_device)
        run()
        button_devicelist.setOnClickListener { discoverDevices() }
    }
    private fun run(){
        registerBroadcast()
        isBluetoothSupported()
        enableBluetooth()
        addBondedDevices()
        accessCoarseLocation()
        discoverDevices()
    }
    private val receiver = object : BroadcastReceiver() {

        @SuppressLint("NewApi")
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "bluetooth devices found", Toast.LENGTH_LONG).show()
            val action: String = intent.action
            if (BluetoothDevice.ACTION_ACL_CONNECTED == action) {
                //Device is now connected
                connected = true
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED == action) {
                //Device has disconnected
                connected = false
            }
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (deviceIsNotOnList(device)) {
                        btdevicelist.add(device)
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun deviceIsNotOnList(device: BluetoothDevice): Boolean {
        var addToList = true
        for (i in btdevicelist.indices) {
            if (device.address == btdevicelist[i].address) {
                addToList = false
                if (btdevicelist[i].name == btdevicelist[i].address && device.name != btdevicelist[i].name) {
                    btdevicelist.drop(i)
                    adapter!!.notifyDataSetChanged()
                    addToList = true
                }
            }
        }
        return addToList
    }

    private fun registerBroadcast() {
        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
        Toast.makeText(this, "bluetooth searching", Toast.LENGTH_LONG).show()
    }

    private fun accessCoarseLocation() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            12345
        )
    }

    private fun addBondedDevices() {
        val m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        if (m_pairedDevices.isNotEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                btdevicelist.add(device)
                Log.i("device", "" + device)
            }
        } else {
            Toast.makeText(this, "no paired bluetooth devices found", Toast.LENGTH_LONG).show()
        }
    }

    private fun enableBluetooth() {
        if (!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }
    }

    private fun isBluetoothSupported() {
        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (m_bluetoothAdapter == null) {
            Toast.makeText(this, "device doesn't support bluetooth", Toast.LENGTH_LONG).show()
            return
        }
    }

    private fun convertList(BTDList: ArrayList<BluetoothDevice>): ArrayList<Device> {
        val list: ArrayList<Device> = ArrayList()
        for (device in BTDList) {
            println(device.name + "  " + device.address)
            if (device.name == null) {
                list.add(Device(device.address, device.address))
            } else {
                list.add(Device(device.name, device.address))
            }
        }
        return list
    }

    private fun makeList() {
        adapter = DeviceListAdapter(convertList(btdevicelist), applicationContext)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
    }

    private fun discoverDevices() {
        m_bluetoothAdapter!!.startDiscovery()
        makeList()
        Toast.makeText(this, "Bluetooth has been enabled", Toast.LENGTH_LONG).show()
    }

    fun connect(position: Int) {
        var device: BluetoothDevice = btdevicelist[position]
        val address: String = device.address
        device = m_bluetoothAdapter!!.getRemoteDevice(address)
        device.createBond()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_ADDRESS, address)
        startActivity(intent)
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
                Toast.makeText(this, "Bluetooth enabling has been canceled", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        m_bluetoothAdapter!!.cancelDiscovery()
        unregisterReceiver(receiver)
    }
}



