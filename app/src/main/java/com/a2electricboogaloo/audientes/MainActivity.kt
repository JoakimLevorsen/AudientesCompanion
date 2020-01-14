package com.a2electricboogaloo.audientes


import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.a2electricboogaloo.audientes.controller.BluetoothController
import com.a2electricboogaloo.audientes.services.VolumeService

import com.a2electricboogaloo.audientes.ui.welcome.SelectDeviceActivity


class MainActivity : AppCompatActivity() {

    companion object {
        private var bluetoothController: BluetoothController? = null
        var address: String? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of IDs because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_audiograms, R.id.navigation_home, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        address = intent.getStringExtra(SelectDeviceActivity.EXTRA_ADDRESS)

        bluetoothController = BluetoothController()
        bluetoothController!!.sendCommand("0x00")

        startService(Intent(this, VolumeService::class.java))
//TODO STOP SERVICE SOMEWHERE
    }
}
