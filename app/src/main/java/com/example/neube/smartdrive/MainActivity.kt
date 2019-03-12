package com.example.neube.smartdrive

import android.app.Activity
import android.content.ContentValues.TAG
import android.icu.text.Normalizer.NO
import android.icu.text.RelativeDateTimeFormatter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.neube.smartdrive.controlamotores.ModoComFirebase
import com.google.android.things.pio.PeripheralManager
import com.example.neube.smartdrive.core.ext.scanI2cAvailableAddresses
import com.example.neube.smartdrive.device.BoardDefaults
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.I2cDevice
import com.google.firebase.database.*
import java.io.IOException

import java.util.*
import kotlin.experimental.or

class MainActivity : Activity() {

    internal var TAG = MainActivity::class.simpleName!!


    internal var FCMotorDoisA: com.google.android.things.contrib.driver.button.Button? = null
    internal var FCMotorDoisB: com.google.android.things.contrib.driver.button.Button? = null
    internal var FCMotorUmA: com.google.android.things.contrib.driver.button.Button? = null
    internal var FCMotorUmB: com.google.android.things.contrib.driver.button.Button? = null
    internal lateinit var buttonInputDriver: ButtonInputDriver

    var database = FirebaseDatabase.getInstance()

    var mDatabase = database.reference


 //   internal var myRef = mDatabase.getReference()
//    internal var fcmotoruma = myRef.child("fcmotoruma")
//    internal var fcmotorumb = myRef.child("fcmotorumb")
//    internal var fcmotordoisa = myRef.child("fcmotordoisa")
//    internal var fcmotorboisb = myRef.child("fcmotordoisb")
//    internal var pararRef = myRef.child("MotorUm/Parar")


    var janelauma:Long? = null
    var parar:Long? = null

    internal var  I2C_PIN_NAME = "I2C1"
    internal val I2C_ADDRESS_SMARTDRIVE = 0x1B
    internal var SmartDrive_COMMAND     = 0x41

        // Supported I2C commands
    internal var CMD_R = 0x52
    internal var CMD_S = 0x53

    //  val device = PeripheralManager.getInstance().openI2cDevice(I2C_PIN_NAME, I2C_ADDRESS_SMARTDRIVE)

    //    private var mDevice: I2cDevice? = null

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanI2cDevices()

        var modoComFirebase = ModoComFirebase()
        modoComFirebase.getDataInit()

   //     getDataInit()


    }

    // Clean



    @Throws(IOException::class)
    fun CleanRegisters(device: I2cDevice, address: Int) {

       device.writeRegByte(SmartDrive_COMMAND, CMD_S.toByte())
    //    device.writeRegByte(address, limpa)
    }



    public fun getDataInit() {

        var dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                Log.i(TAG, "Configuring GPIO pins")

                Log.i(TAG, "Configuring GPIO pins")


//                var pioService = PeripheralManager.getInstance()
//                var portList = pioService.gpioList
//
//                mLedGpio = pioService.openGpio(BoardDefaults.getGPIOForButton15())
//                mLedGpio!!.setDirection(Gpio.DIRECTION_IN)
//                mLedGpio!!.setActiveType(Gpio.ACTIVE_LOW)


                /// look to /// =
// /               var mDatabase = FirebaseDatabase.getInstance()

                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference()
                
                val fcmotoruma   = myRef.child("fcmotoruma")
                val fcmotorumb   = myRef.child("fcmotorumb")
                val fcmotordoisa = myRef.child("fcmotordoisa")
                val fcmotorboisb = myRef.child("fcmotordoisb")

//  /              var fcmotordoisa = mDatabase.getReference("fcmotordoisa")
//  /              var fcmotordoisb = mDatabase.getReference("fcmotordoisb")
//  /              var fcmotoruma   = mDatabase.getReference("fcmotoruma")
//  /              var fcmotorumb   = mDatabase.getReference("fcmotorumb")


                Log.i(TAG, "Volto 44.11......")

      //          Log.i(TAG, "Volto 44---1 ${mLedGpio!!.value}")

                try {

                    FCMotorUmA = com.google.android.things.contrib.driver.button.Button(BoardDefaults.getGPIOForButton18(),
                            //       mButton = Button(BUTTON_GPIO_PIN,
                            com.google.android.things.contrib.driver.button.Button.LogicState.PRESSED_WHEN_LOW)

                    FCMotorUmA!!.setOnButtonEventListener { button, pressed ->

                        Log.i(TAG, "Volto 45-1: ${fcmotoruma}")

                        if (pressed) {
                            fcmotoruma.setValue(0)

                            Log.i(TAG, "Volto 45.00 ${fcmotoruma}")

                        }
                        else if (!pressed){
                            Log.i(TAG, "Volto 45+1' ${fcmotoruma}")
                            fcmotoruma.setValue(1)
                        }

                        Log.i(TAG, "Volto 45.01010: ${fcmotoruma}")
                    }

                } catch (e: IOException) {
                    // couldn't configure the button...
                }

                try {

                    FCMotorUmB = com.google.android.things.contrib.driver.button.Button(BoardDefaults.getGPIOForButton21(),
                            //       mButton = Button(BUTTON_GPIO_PIN,
                            com.google.android.things.contrib.driver.button.Button.LogicState.PRESSED_WHEN_LOW)

                    FCMotorUmB!!.setOnButtonEventListener { button, pressed ->

                        Log.i(TAG, "Volto 46-1: ${fcmotorumb}")

                        if (pressed) {
                            fcmotorumb.setValue(0)

                            Log.i(TAG, "Volto 46.00 ${fcmotorumb}")

                        }
                        else if (!pressed){
                            Log.i(TAG, "Volto 46+1' ${fcmotorumb}")
                            fcmotorumb.setValue(1)
                        }

                        Log.i(TAG, "Volto 47: ${fcmotorumb}")
                    }

                } catch (e: IOException) {
                    // couldn't configure the button...
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException())
            }
        }

        mDatabase.addValueEventListener(dataListener)

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
 //       fan?.close().also { fan = null }
    }

    private fun scanI2cDevices() {
        Log.i(TAG, "Scanning I2C devices")
        PeripheralManager.getInstance().scanI2cAvailableAddresses(I2C_PIN_NAME)
                .map { String.format(Locale.US, "0x%02X", it) }
                .forEach { address -> Log.i(TAG, "Found: $address") }
    }

    private fun wait1sec() = Thread.sleep(5000)

}