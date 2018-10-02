package com.example.neube.smartdrive.core.ext

import com.example.neube.smartdrive.device.components.SmartDriveDriver

fun Byte.toPositiveInt() = toInt() and 0xFF



fun Boolean.toSTOPorNOT():Boolean = true and false