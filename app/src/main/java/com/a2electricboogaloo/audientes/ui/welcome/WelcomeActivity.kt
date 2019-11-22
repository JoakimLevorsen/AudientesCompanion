package com.a2electricboogaloo.audientes.ui.welcome

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a2electricboogaloo.audientes.MainActivity
import com.a2electricboogaloo.audientes.R
import java.util.ArrayList


class WelcomeActivity : AppCompatActivity() {

    private var didStartActivation = false
    private var nextButton: Button? = null
    private var titleText: TextView? = null
    private var contentText: TextView? = null
   // private fun PackageManager.missingSystemFeature(name: String): Boolean = !hasSystemFeature(name)
   // private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
    //    val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
     //   bluetoothManager.adapter
    //}
  //  private val BluetoothAdapter.isDisabled: Boolean
    //    get() = !isEnabled


    val REQUEST_ENABLE_BT = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        hideNavBar()

        nextButton = findViewById<Button>(R.id.button11)
        titleText = findViewById<TextView>(R.id.titleView)
        contentText = findViewById<TextView>(R.id.contentView)

       /* packageManager.takeIf { it.missingSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) }
            ?.also {
                Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show()
            }
       bluetoothAdapter?.takeIf { it.isDisabled }?.apply {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            val REQUEST_ENABLE_BT = 1
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
        */


        nextButton?.setOnClickListener {
            activateBT()
        }

        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter (BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
    }



    fun activateBT(){
        titleText?.text = "Loading..."
        contentText?.text = "Connecting to device."

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        bluetoothAdapter?.startDiscovery()

        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            println("forbinder til BT")
        }
        if (bluetoothAdapter?.isEnabled == true){
            println("BT er aktiveret")
            val intent = Intent(this, MainActivity::class.java)
            val lambda = { -> startActivity(intent) }
            finish()
            lambda()
        }
    }


        // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                }
            }
        }
    }

    private fun hideNavBar() {
        this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onDestroy() {
        super.onDestroy()


        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver)
    }
}
