//package com.example.neube.smartdrive.controlamotores
//
//import android.app.Activity
//import android.content.ContentValues.TAG
//import android.util.Log
//import android.view.KeyEvent
//import com.google.android.things.contrib.driver.button.Button
//import com.google.android.things.contrib.driver.button.ButtonInputDriver
//import com.example.neube.smartdrive.device.BoardDefaults
//import com.example.neube.smartdrive.device.BoardDefaults.getGPIOForButton15
//import com.example.neube.smartdrive.device.BoardDefaults.getGPIOForButton17
//import com.example.neube.smartdrive.device.BoardDefaults.getGPIOForButton18
//import com.example.neube.smartdrive.device.BoardDefaults.getGPIOForButton21
//import com.google.android.things.pio.Gpio
//import com.google.android.things.pio.PeripheralManager
//import java.io.IOException
//
//
//class ModoAutomatico : AutoCloseable {
//
//    private val buttonInputDriver: ButtonInputDriver? = null
////    var mGpio: String = BoardDefaults.getGPIOForButton21()
//    var mGpio =  PeripheralManager.getInstance()
//
//    var fcmotordoisa: Gpio? = mGpio.openGpio(getGPIOForButton15())
//    var fcmotordoisaa = fcmotordoisa?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
//
//    var fcmotordoisb: Gpio? = mGpio.openGpio(getGPIOForButton17())
//    var fcmotordoisbb = fcmotordoisa?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
//
//    var fcmotoruma:   Gpio? = mGpio.openGpio(getGPIOForButton18())
//    var fcmotorumaa = fcmotordoisa?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
//
//    var fcmotorumb:   Gpio? = mGpio.openGpio(getGPIOForButton21())
//    var fcmotorumbb = fcmotordoisa?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
//
//
//    override fun onStart() {
//        super.onStart()
//        buttonInputDriver!!.register()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        buttonInputDriver!!.unregister()
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        return if (keyCode == KeyEvent.KEYCODE_SPACE) {
//            // Handle button pressed event
//            true
//        } else super.onKeyDown(keyCode, event)
//
//    }
//
//    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
//        return if (keyCode == KeyEvent.KEYCODE_SPACE) {
//            // Handle button released event
//            true
//        } else super.onKeyUp(keyCode, event)
//
//    }
//
//    companion object {
//        private val TAG = "ButtonActivity"
//    }
//}

//class ModoAutomatico {
//
//    var mGpio: String = BoardDefaults.getGPIOForButton21()
//
//    // Attempt to access the GPIO
//    mGpio =  PeripheralManager.getInstance().openGpio(BoardDefaults.getGPIOForButton21())
//
//
//}

