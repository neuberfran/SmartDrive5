package com.example.neube.smartdrive.device

import android.app.Activity
import android.icu.text.RelativeDateTimeFormatter
import android.os.Bundle
import android.util.Log
import com.example.neube.smartdrive.device.components.SmartDriveDriver
import com.google.android.things.pio.PeripheralManager
import com.example.neube.smartdrive.core.ext.scanI2cAvailableAddresses
import com.example.neube.smartdrive.device.components.SmartDriveDriver.*
import java.util.*

class MainActivity : Activity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName!!

        private const val I2C_PIN_NAME = "I2C1"
        private const val I2C_ADDRESS_SMARTDRIVE = 0x1B

     //  val device = PeripheralManager.getInstance().openI2cDevice(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)

   //    private var mDevice: I2cDevice? = null

    }

    private var fan: SmartDriveDriver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanI2cDevices()
        fan = SmartDriveDriver(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)

    }

    override fun onResume() {
        super.onResume()

        fan?.let { fan ->
            fan.start()
            fan.motornumber = MotorNumber.Two
            fan.direction =   Direction.Right
            fan.stopornot =   StopOrNot.No
            Log.i(TAG, "Speed: ${fan.motornumber}")
            wait1sec()
      //     fan.stop()
        }

        fan!!.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        fan?.close().also { fan = null }
    }

    private fun scanI2cDevices() {
        Log.i(TAG, "Scanning I2C devices")
        PeripheralManager.getInstance().scanI2cAvailableAddresses(I2C_PIN_NAME)
                .map { String.format(Locale.US, "0x%02X", it) }
                .forEach { address -> Log.i(TAG, "Found: $address") }
    }

    private fun wait1sec() = Thread.sleep(5000)
}