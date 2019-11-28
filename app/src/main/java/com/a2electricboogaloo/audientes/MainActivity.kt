package com.a2electricboogaloo.audientes

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.a2electricboogaloo.audientes.controller.bluetoothController
import com.a2electricboogaloo.audientes.controller.bluetoothController.Companion.m_address
import com.a2electricboogaloo.audientes.ui.welcome.SelectDeviceActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis)

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
}
