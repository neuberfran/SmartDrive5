package com.example.neube.smartdrive.controlamotores.modooffline

import android.annotation.SuppressLint
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.util.Log.i
import android.view.KeyEvent
import com.example.neube.smartdrive.device.BoardDefaults.getGPIOForButton04
import com.example.neube.smartdrive.device.BoardDefaults.getGPIOForButton15
import com.example.neube.smartdrive.device.BoardDefaults.getGPIOForButton17
import com.example.neube.smartdrive.device.BoardDefaults.getGPIOForButton18
import com.example.neube.smartdrive.device.BoardDefaults.getGPIOForButton21
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.button.ButtonInputDriver

@SuppressLint("Registered")
class DriverService : Service() {

    private val myBinder = MyLocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
    //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        return myBinder

        Log.i(TAG, "Volto 106.00")

    }

    public lateinit var chuva: ButtonInputDriver
    public lateinit var fcmotoruma: ButtonInputDriver
    public lateinit var fcmotorumb: ButtonInputDriver
    public lateinit var fcmotordoisa: ButtonInputDriver
    public lateinit var fcmotordoisb: ButtonInputDriver


    override fun onCreate() {
        super.onCreate()
        i(TAG, "Starting ModoAutomatico")

       // led

        Log.i(TAG, "Volto 109.00")

        chuva = ButtonInputDriver(getGPIOForButton04(), Button.LogicState.PRESSED_WHEN_LOW, KeyEvent.KEYCODE_DPAD_UP)
        chuva.register()

        fcmotoruma = ButtonInputDriver(getGPIOForButton18(), Button.LogicState.PRESSED_WHEN_LOW, KeyEvent.KEYCODE_DPAD_UP)
        fcmotoruma.register()


        fcmotorumb = ButtonInputDriver(getGPIOForButton21(), Button.LogicState.PRESSED_WHEN_LOW, KeyEvent.KEYCODE_DPAD_UP)
        fcmotorumb.register()

//        fcmotordoisa = ButtonInputDriver(getGPIOForButton15(), Button.LogicState.PRESSED_WHEN_LOW, KeyEvent.KEYCODE_DPAD_UP)
//        fcmotordoisa.register()

        fcmotordoisb = ButtonInputDriver(getGPIOForButton17(), Button.LogicState.PRESSED_WHEN_LOW, KeyEvent.KEYCODE_DPAD_UP)
        fcmotordoisb.register()

        Log.i(TAG, "Volto 110.00")

    }

    inner class MyLocalBinder : Binder() {
        fun getService() : DriverService {
            return this@DriverService
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fcmotoruma.unregister()
        fcmotorumb.unregister()
   //     fcmotordoisa.unregister()
        fcmotordoisb.unregister()
        chuva.unregister()

        Log.i(TAG, "Volto 111.00")
    }

}