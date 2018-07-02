package com.example.neube.smartdrive.device

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.example.neube.smartdrive.device.components.SmartDriveDriver
import com.example.neube.smartdrive.device.components.SmartDriveDriver.MotorNumber
import com.google.android.things.pio.PeripheralManager
import com.google.android.things.pio.I2cDevice;
import java.io.IOException
import android.content.ContentValues.TAG

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
        fan = SmartDriveDriver(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)

//        val manager = PeripheralManager.getInstance()
//        val deviceList = manager.getI2cBusList()
//        if (deviceList.isEmpty()) {
//            Log.i(TAG, "No I2C bus available on this device.")
//        } else {
//            Log.i(TAG, "List of available devices: " + deviceList)
//        }
//
//        try {
//            val manager = PeripheralManager.getInstance()
//            mDevice = manager.openI2cDevice(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)
//        } catch (e: IOException) {
//            Log.w(TAG, "Unable to access I2C device", e)
//        }
    }

    override fun onResume() {
        super.onResume()

        fan?.let { fan ->
            fan.start()
            fan.motornumber = MotorNumber.One
            Log.i(TAG, "Speed: ${fan.motornumber}")
            Thread.sleep(5000)
          fan.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fan?.close().also { fan = null }

//        if (mDevice != null) {
//            try {
//                mDevice!!.close()
//                mDevice = null
//            } catch (e: IOException) {
//                Log.w(TAG, "Unable to close I2C device", e)
//            }
//        }
    }
}


