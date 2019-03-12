package com.example.neube.smartdrive.controlamotores

import android.app.Activity
import android.util.Log
import android.view.KeyEvent

import com.example.neube.smartdrive.MainActivity
import com.example.neube.smartdrive.device.BoardDefaults
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.Gpio.DIRECTION_OUT_INITIALLY_LOW
import com.google.android.things.pio.PeripheralManager

import java.io.IOException

class esse : Activity() {

    internal var ledGpio = PeripheralManager.getInstance().openGpio("BCM21")

    // Alternatively, we can also get the LED value
    internal var isOn = ledGpio.setDirection(DIRECTION_OUT_INITIALLY_LOW)
}// To turn the LED on, we set the value to true
// To turn the LED off, we set its value to false
// And we close the peripheral, because we are good guys