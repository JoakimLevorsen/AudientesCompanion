package com.a2electricboogaloo.audientes

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.a2electricboogaloo.audientes.controller.BluetoothController

import com.a2electricboogaloo.audientes.ui.welcome.SelectDeviceActivity
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        var m_address: String? = null
        private var connectToDevice : BluetoothController.ConnectToDevice? = null
        private var bluetoothController : BluetoothController?=null
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis)
        m_address = intent.getStringExtra(SelectDeviceActivity.EXTRA_ADDRESS)
        BluetoothController.ConnectToDevice(this).execute()


        bluetoothController = BluetoothController()

        sendCommand("0x00")

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_audiograms, R.id.navigation_home, R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this@MainActivity)

        builder.setTitle("Confirm exit")

        builder.setMessage("Are you sure you want to exit the app")

        builder.setPositiveButton("EXIT"){ dialogInterface: DialogInterface, i: Int ->
            finish()
        }
        builder.setNegativeButton("CANCEL"){ dialogInterface: DialogInterface, i: Int ->
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun sendCommand(input: String) {
        bluetoothController?.sendCommand(input)
    }


    private fun disconnect() {
        bluetoothController?.disconnect()
    }

    class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context

        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (BluetoothController.m_bluetoothSocket == null || !BluetoothController.m_isConnected) {
                    BluetoothController.m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = BluetoothController.m_bluetoothAdapter.getRemoteDevice(
                        BluetoothController.m_address
                    )
                    BluetoothController.m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(
                        BluetoothController.m_myUUID
                    )
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    BluetoothController.m_bluetoothSocket!!.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSuccess) {
                Log.i("data", "couldn't connect")
            } else {
                Log.i("data", "connection success")
                BluetoothController.m_isConnected = true

            }
            BluetoothController.m_progress.dismiss()
        }
    }
}
