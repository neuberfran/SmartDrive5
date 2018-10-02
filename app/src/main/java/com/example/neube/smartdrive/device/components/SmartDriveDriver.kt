package com.example.neube.smartdrive.device.components

import com.example.neube.smartdrive.core.ext.toPositiveInt
import com.example.neube.smartdrive.core.ext.toSTOPorNOT
import com.google.android.things.pio.I2cDevice
import com.google.android.things.pio.PeripheralManager;

class SmartDriveDriver(i2cName: String, i2cAddress: Int) : AutoCloseable {

     var SmartDrive_Motor: Byte = 0

  //   var direction =  0

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

    enum class MotorNumber(var motorneuber: Byte) {
        One(0x46), Two(0x4E);

        companion object {
            fun fromValue(motorneuber: Byte) = MotorNumber.values().firstOrNull { it.motorneuber == motorneuber } ?: Two
        }
    }

    enum class Direction(var direcneuber: Byte) {
        Right(128.toByte()), Left(129.toByte());

        companion object {
            fun fromValue(direcneuber: Byte) = Direction.values().firstOrNull { it.direcneuber == direcneuber } ?: Right
        }
    }

    enum class StopOrNot(var i2cValue: Boolean) {
        No( false), Yes(true);

        companion object {
            fun fromValue(i2cValue: Boolean) :StopOrNot = StopOrNot.values().firstOrNull { it.i2cValue == i2cValue } ?: No
        }
    }

    private var device: I2cDevice? = null

    init {
     //   I2cDevice device = PeripheralManager.getInstance().openI2cDevice(i2cName, i2cAddress)
    //    val device = PeripheralManager.getInstance().openI2cDevice(i2cName, i2cAddress)

        device = PeripheralManager.getInstance().openI2cDevice(i2cName, i2cAddress)

    }

    lateinit var motornumber: MotorNumber

//    get() = MotorNumber.fromValue(device?.readRegByte(COMMAND_SPEED)?.toPositiveInt() ?: 0)




    lateinit var direction: Direction

 //       get() = Direction.fromValue(device?.readRegByte(COMMAND_SPEED)?.toPositiveInt() ?: 0)

//        set(value) {
//
//            if (direction == Direction.Right) {
//                var Direction = 128.toByte()
//            } else {
//                var Direction = 129.toByte()
//            }
//        }

    lateinit var stopornot: StopOrNot

     //  get() = StopOrNot.fromValue(device?.equals(StopOrNot.No)?.toSTOPorNOT() ?: false)

   //     set(value) {

            while (stopornot == StopOrNot.No) {

                var SmartDrive_Motor=MotorNumber.Two.motorneuber
                var Direction = Direction.Right.direcneuber

                var buffer = byteArrayOf(SmartDrive_Motor, Direction, 0x05, 0x00, 0xD1.toByte())

                device?.write(buffer, buffer.size)
            }
 //       }

    override fun close() {
        device?.close().also { device = null }
    }

    fun start() = device?.writeRegByte(SmartDrive_COMMAND, CMD_S.toByte())

    fun stop() = device?.writeRegByte(COMMAND_ON_OFF, VALUE_OFF)

}