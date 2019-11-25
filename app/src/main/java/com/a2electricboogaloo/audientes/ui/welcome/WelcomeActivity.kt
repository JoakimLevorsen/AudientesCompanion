package com.a2electricboogaloo.audientes.ui.welcome

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.R
import kotlinx.android.synthetic.main.activity_welcome.*
import java.util.*

/**
 * @author Sersan Aslan
 **/
class WelcomeActivity : AppCompatActivity() {

    private var nextButton: Button? = null
    private var titleText: TextView? = null
    private var contentText: TextView? = null
    private var requestCodeForBT: Int? = null
    private var btEnablingIntent: Intent? = null
    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var deviceList: ListView? = null
    private var stringArrayList: ArrayList<String>? = null
    private var arrayAdapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportActionBar?.hide()

        nextButton = buttonNext
        titleText = titleView
        contentText = contentView
        deviceList = listOfDevices

        btEnablingIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        requestCodeForBT = 1

        bluetoothActivate()
    }

    /**
     * @author Sersan Aslan
     **/
    private fun bluetoothActivate() {
        nextButton?.setOnClickListener {
            if (bluetoothAdapter == null) {
                Toast.makeText(
                    application,
                    "Bluetooth does not support this device",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (!bluetoothAdapter.isEnabled) {
                    startActivityForResult(btEnablingIntent, requestCodeForBT!!)
                }
            }
        }
    }


    /**
     *@method can be used, if wanted to know already bonded BT devices
     * @author Sersan Aslan
     **/
    private fun showListOfBondedDevices() {
        var setBT: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
        var arrayBT = arrayOfNulls<String>(setBT.size)
        var index = 0

        if (setBT.size > 0) {
            for (device: BluetoothDevice in setBT) {
                arrayBT[index] = device.name
                index++
            }
            var arrayAdapter =
                ArrayAdapter<String>(applicationContext, R.layout.bt_list_items, arrayBT)
            deviceList?.adapter = arrayAdapter
        }
    }

    /**
     * @author Sersan Aslan
     **/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestCodeForBT) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(applicationContext, "Bluetooth is enabled", Toast.LENGTH_LONG).show()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(applicationContext, "Bluetooth is canceled", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


}
