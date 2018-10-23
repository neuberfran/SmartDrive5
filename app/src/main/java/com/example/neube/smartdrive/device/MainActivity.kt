package com.example.neube.smartdrive.device

import android.app.Activity
import android.content.ContentValues.TAG
import android.icu.text.Normalizer.NO
import android.icu.text.RelativeDateTimeFormatter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.neube.smartdrive.device.components.SmartDriveDriver
import com.google.android.things.pio.PeripheralManager
import com.example.neube.smartdrive.core.ext.scanI2cAvailableAddresses
import com.example.neube.smartdrive.device.components.SmartDriveDriver.*
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.I2cDevice
import com.google.firebase.database.*
import java.io.IOException

import java.util.*
import kotlin.experimental.or

class MainActivity : Activity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName!!

        private const val I2C_PIN_NAME = "I2C1"
        private const val I2C_ADDRESS_SMARTDRIVE = 0x1B
        private const val SmartDrive_COMMAND   =  0x41

        // Supported I2C commands
        private const val CMD_R = 0x52
        private const val CMD_S = 0x53


        lateinit var mDatabase: DatabaseReference

     //  val device = PeripheralManager.getInstance().openI2cDevice(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)

   //    private var mDevice: I2cDevice? = null

    }

    private var fan: SmartDriveDriver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanI2cDevices()

        mDatabase = FirebaseDatabase.getInstance().reference

 //       getDataInit()


        fan = SmartDriveDriver(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)


        //     val smartDriver = SmartDriveDriver(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)
    //    smartDriver.runMotors(0x4E, direcneuber = 128.toByte(), stopOrNot = StopOrNot.No)

    }

    // Clean



    @Throws(IOException::class)
    fun CleanRegisters(device: I2cDevice, address: Int) {

       device.writeRegByte(SmartDrive_COMMAND, CMD_S.toByte())
    //    device.writeRegByte(address, limpa)
    }

    private fun getDataInit() {
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                Log.d(TAG, "volto 1")

                val database = FirebaseDatabase.getInstance()
                mDatabase = FirebaseDatabase.getInstance().reference

                val service = PeripheralManager.getInstance()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException())
            }
        }
        mDatabase.addValueEventListener(dataListener)
    }

    override fun onResume() {
        super.onResume()

   //     fan!!.start()

        fan?.let { fan ->
            fan?.start()
            fan.motorneuber = MotorNumber.One

            Log.i(TAG, "Volto 44: ${fan.motorneuber}")
            fan?.direcneuber =   Direction.Left

            Log.i(TAG, "Volto 44: ${fan?.direcneuber}")
     //       fan.stopornot =   StopOrNot.No

            wait1sec()
           fan?.stop()
        }
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