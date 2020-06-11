package com.example.neube.smartdrive.controlamotores

import android.app.Activity
import android.content.ContentValues.TAG
import android.graphics.Path
import android.util.Log
import com.example.neube.smartdrive.core.ext.toPositiveInt
import com.example.neube.smartdrive.core.ext.toSTOPorNOT
import com.example.neube.smartdrive.MainActivity
import com.google.android.things.pio.I2cDevice
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.database.*
import android.content.ContentValues.TAG
import android.icu.text.Normalizer.NO
import android.icu.text.RelativeDateTimeFormatter
import android.os.Bundle
import com.example.neube.smartdrive.device.BoardDefaults
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.pio.Gpio
import java.io.IOException

class ModoComFirebase(): AutoCloseable {
    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var TAG = ModoComFirebase::class.simpleName!!

      private var mButtonInputDriver: ButtonInputDriver? = null

      public var I2C_PIN_NAME = "I2C1"
      public var I2C_ADDRESS_SMARTDRIVE = 0x1B
      public var SmartDrive_COMMAND     =  0x41

      // Supported I2C commands
      public var CMD_R = 0x52
      public var CMD_S = 0x53

      var database = FirebaseDatabase.getInstance()

      var mDatabase = database.reference

      private var mLedGpio: Gpio? = null

      public var FCMotorDoisA: Button? = null
      public var FCMotorDoisB: Button? = null
      public var FCMotorUmA:   Button? = null
      public var FCMotorUmB:   Button? = null


   //    lateinit var motornumber: MotorNumber
   //    var direcmotor:Direction
   //    get() = Direction.fromValue(device?.readRegByte(COMMAND_SPEED)?.toPositiveInt() ?: 0)
   //    var buffer =   byteArrayOf(0xA1.toByte(), 0x2E.toByte(), 0x38.toByte(), 0xD4.toByte(), 0x89.toByte(), 0xC3.toByte())
   //    var buffer = byteArrayOf(motornumber.motornumber, value.direcmotor.toByte(), 0x05.toByte(), 0x00.toByte(), 0xD1.toByte())
   //    Log.i(ModoComFirebase.TAG, "Volto 48 $buffer")
   //    device?.write(buffer, buffer.size)
   //    Log.i(ModoComFirebase.TAG, "Volto 49 ${buffer.size}")

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

}