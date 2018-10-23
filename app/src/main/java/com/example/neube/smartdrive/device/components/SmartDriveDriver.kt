package com.example.neube.smartdrive.device.components

import android.content.ContentValues.TAG
import android.graphics.Path
import android.util.Log
import com.example.neube.smartdrive.core.ext.toPositiveInt
import com.example.neube.smartdrive.core.ext.toSTOPorNOT
import com.example.neube.smartdrive.device.MainActivity
import com.google.android.things.pio.I2cDevice
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.database.*
import android.content.ContentValues.TAG
import android.icu.text.Normalizer.NO
import android.icu.text.RelativeDateTimeFormatter
import android.os.Bundle

class SmartDriveDriver(i2cName: String, i2cAddress: Int) : AutoCloseable {

  //   var SmartDrive_Motor: Byte = 0

  //   var direction =  0

    companion object {

        private val TAG = SmartDriveDriver::class.java.simpleName!!

        private const val COMMAND_ON_OFF = 0x01
        private const val COMMAND_SPEED = 0x02

        private const val VALUE_ON = 1.toByte()
        private const val VALUE_OFF = 0.toByte()
        private const val SmartDrive_ADDRESS = 0x1B
        private  const val SmartDrive_VOLTAGE_MULTIPLIER = 212.7
        private  const val SmartDrive_PPR = 360         // PPR - pulses per rotation

        // Motor selection related constants
        private const val SmartDrive_Motor_1        =        0x01
        private const val SmartDrive_Motor_2        =        0x02
        private const val SmartDrive_Motor_Both     =        0x03

        private const val SmartDrive_COMMAND   =  0x41
        private const val SmartDrive_SPEED_M1  =  0x46
        private const val SmartDrive_SPEED_M2  =  0x4E

        // Supported I2C commands
        private const val CMD_R = 0x52
        private const val CMD_S = 0x53

    }

 //   fun start() = device?.writeRegByte(SmartDrive_COMMAND, CMD_R.toByte())

    enum class MotorNumber(var motorneuber: Byte) {
        One(0x46), Two(0x4E);

        companion object {
            fun fromValue(motorneuber: Byte) = MotorNumber.values().firstOrNull { it.motorneuber == motorneuber }?: One

        }

   //     fun value(): Int = this.motorneuber
    }

    enum class Direction(val direcneuber: Int) {
        Right(128), Left(127);

        companion object {
            fun fromValue(direcneuber: Int) = Direction.values().firstOrNull { it.direcneuber == direcneuber } ?: Right
        }

    }

//    enum class StopOrNot(var i2cValue: Boolean) {
//        No( false), Yes(true);
//
//        companion object {
//            fun fromValue(i2cValue: Boolean) :StopOrNot = StopOrNot.values().firstOrNull { it.i2cValue == i2cValue } ?: No
//        }
//    }

    private var device: I2cDevice? = null

    init {

        device = PeripheralManager.getInstance().openI2cDevice(i2cName, i2cAddress)

        Log.i(SmartDriveDriver.TAG, "Volto 45 $device")

    }

    lateinit var motorneuber: MotorNumber

    var direcneuber:Direction

    get() = Direction.fromValue(device?.readRegByte(COMMAND_SPEED)?.toPositiveInt() ?: 0)

 //   protected lateinit var motornumber: MotorNumber

        set(value) {
            if (value != null) {

                Log.i(SmartDriveDriver.TAG, "Volto 46 $value")

                Log.i(SmartDriveDriver.TAG, "Volto 47 $direcneuber")

//             var buffer =   byteArrayOf(0xA1.toByte(), 0x2E.toByte(), 0x38.toByte(), 0xD4.toByte(), 0x89.toByte(), 0xC3.toByte())


                var buffer = byteArrayOf(motorneuber, value.direcneuber.toByte(), 0x05.toByte(), 0x00.toByte(), 0xD1.toByte())

                Log.i(SmartDriveDriver.TAG, "Volto 48 $buffer")

                device?.write(buffer, buffer.size)

                Log.i(SmartDriveDriver.TAG, "Volto 49 ${buffer.size}")

            }
        }

  //  lateinit var motornumber: MotorNumber

  //  lateinit var direction: Direction

 //   lateinit var stopornot: StopOrNot

  //  fun runMotors(motorneuber: Byte, direcneuber: Byte, stopOrNot: StopOrNot) {

   //      var stopornot: StopOrNot

    //    var buffer = byteArrayOf(MotorNumber.Two.motorneuber, Path.Direction.Right.direcneuber, 0x05, 0x00, 0xD1.toByte())


//        var i = 1
//
//             while (i<1120) {
//
//                var buffer = byteArrayOf(MotorNumber.Two.motorneuber, Direction.Right.direcneuber, 0x05, 0x00, 0xD1.toByte())
//
//                device?.write(buffer, buffer.size)
//
//                 i++
//
//            }
//     }

    override fun close() {
        device?.close().also { device = null }
    }

    fun start() = device?.writeRegByte(SmartDrive_COMMAND, CMD_R.toByte())

    fun stop() = device?.writeRegByte(SmartDrive_COMMAND, CMD_R.toByte())

}