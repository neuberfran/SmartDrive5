package com.example.neube.smartdrive.device.components

import android.content.ContentValues.TAG
import android.util.Log
import com.example.neube.smartdrive.core.ext.toPositiveInt
import com.google.android.things.pio.I2cDevice
import com.google.android.things.pio.PeripheralManager;
import java.io.File
import java.io.IOException

class SmartDriveDriver(i2cName: String, i2cAddress: Int) : AutoCloseable {

    companion object {

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

        private const val  SmartDrive_CONTROL_BRK   =    0x10
        private const val SmartDrive_CONTROL_TIME  =     0x40
        private const val SmartDrive_CONTROL_GO   =      0x80

        private const val SmartDrive_COMMAND   =  0x41
        private const val SmartDrive_SPEED_M1  =  0x46
        private const val SmartDrive_SPEED_M2  =  0x4E

        // Supported I2C commands
        private const val CMD_R = 0x52
        private const val CMD_S = 0x53
        private const val CMD_a = 0x61
        private const val CMD_b = 0x62
        private const val CMD_c = 0x63
        private const val CMD_A = 0x41
        private const val CMD_B = 0x42
        private const val CMD_C = 0x43
    }

    enum class MotorNumber(val i2cValue: Int) {
        One(40), Two(90), Both(110);

        companion object {
            fun fromValue(i2cValue: Int) = MotorNumber.values().firstOrNull { it.i2cValue == i2cValue } ?: Two
        }
    }

    enum class Direction(val i2cValue: Int) {
        Right(40), Left(90), Stopm(110);

        companion object {
            fun fromValue(i2cValue: Int) = Direction.values().firstOrNull { it.i2cValue == i2cValue } ?: Right
        }
    }

    private var device: I2cDevice? = null

    init {
     //   I2cDevice device = PeripheralManager.getInstance().openI2cDevice(i2cName, i2cAddress)
    //    val device = PeripheralManager.getInstance().openI2cDevice(i2cName, i2cAddress)

        device = PeripheralManager.getInstance().openI2cDevice(i2cName, i2cAddress)

    }

    var motornumber: MotorNumber

    get() = MotorNumber.fromValue(device?.readRegByte(COMMAND_SPEED)?.toPositiveInt() ?: 0)

    set(value) {

        val buffer = byteArrayOf(0x4E, 128.toByte(), 0x05, 0x00, 0xD1.toByte())

        device?.write(buffer, buffer.size)

     }

    var direction: Direction

        get() = Direction.fromValue(device?.readRegByte(COMMAND_SPEED)?.toPositiveInt() ?: 0)

        set(value) {

            device?.writeRegByte(COMMAND_SPEED, value.i2cValue.toByte())
        }

    override fun close() {
        device?.close().also { device = null }
    }

    fun start() = device?.writeRegByte(SmartDrive_COMMAND, CMD_S.toByte())

    fun stop() = device?.writeRegByte(COMMAND_ON_OFF, VALUE_OFF)

}