package com.example.neube.smartdrive

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.example.neube.smartdrive.SmartDriveDriver
import com.example.neube.smartdrive.SmartDriveDriver.Speed

class MainActivity : Activity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName!!

        private const val I2C_PIN_NAME = "I2C1"
        private const val I2C_ADDRESS_SMARTDRIVE = 0x1B
    }

    private var fan: SmartDriveDriver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fan = SmartDriveDriver(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)
    }

    override fun onResume() {
        super.onResume()

        fan?.let { fan ->

            fan.speed = Speed.LOW

            Log.i(TAG, "Speed: ${fan.speed}")
            Thread.sleep(5000)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fan?.close().also { fan = null }
    }
}


