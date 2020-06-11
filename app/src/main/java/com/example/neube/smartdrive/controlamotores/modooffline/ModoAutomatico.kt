package com.example.neube.smartdrive.controlamotores.modooffline

import android.app.Application
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import com.example.neube.smartdrive.device.BoardDefaults.getGPIOForButton15
import com.google.android.things.contrib.driver.button.ButtonInputDriver

class ModoAutomatico : Application() {

    var myService: DriverService? = null
    var isBound = false

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Starting ModoAutomatico")

        Log.i(TAG, "Volto 101.00")

        val driverService = ComponentName(
                "com.example.neube.smartdrive",
                "com.example.neube.smartdrive.controlamotores.modooffline.DriverService"
        )

        val serviceIntent = Intent()
        serviceIntent.component = driverService
        // Bind to the driver service
        bindService(serviceIntent, callback, BIND_AUTO_CREATE)

        Log.i(TAG, "Volto 104")
    }

    private val callback = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName,
                                           iBinder: IBinder) {

            val binder = iBinder  as DriverService.MyLocalBinder

            myService = binder.getService()
            var teste = binder
            teste.getService()

            isBound = true
            Log.i(TAG, "Volto 105.00 ${teste}")

                    isBound = true

        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            // Driver service disconnected

            Log.i(TAG, "Volto 107.00")

            isBound = false

        }
    }
}