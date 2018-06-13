package com.shuyun.androidnotes.service

import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.widget.Toast
import com.shuyun.androidnotes.utils.Log
import com.shuyun.pupilla.Pupilla

/**
 * USB Service for controlling
 * @author shuyun
 * @created on 2018/6/8 0008 19:21
 * @changed on 2018/6/8 0008 19:21
 */
class USBService(var context: Context) {

    val ACTION_USB_PERMISSION = "com.shuyun.USB_PERMISSION" + hashCode()
    val ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE"
    val USB_CONNECTED = "connected"

    private val listOfDevice = ArrayList<UsbDevice>()

    val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager

    /**
     * use 'object' like Java anonymous internal class
     */
    private val usbReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.Companion.e("onReceive() "+intent.action)
            when (intent.action) {
                ACTION_USB_PERMISSION -> {
                    val usbDevice: UsbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        Log.Companion.e("get permission")
                        //use permission
                        test(usbDevice)
                    } else {
                        Log.Companion.e("failed to get permission")
                        //failed to use permission
                    }
                }
                UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                    Log.Companion.e("ACTION_USB_DEVICE_ATTACHED")
                    val usbDevice: UsbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                    if (!usbManager.hasPermission(usbDevice)) {
                        val permissionIntent = PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION), 0)
                        usbManager.requestPermission(usbDevice, permissionIntent)
                    }
                    //process attach event
                }
                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    Log.Companion.e("ACTION_USB_DEVICE_DETACHED")
                    val usbDevice: UsbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

                    //process detach event
                }
            }
        }
    }

    private fun test(device: UsbDevice){
        val conn = usbManager.openDevice(device)
        val fs = conn.fileDescriptor
        Log.Companion.e("conn.fileDescriptor "+fs)
        val res = Pupilla().configuration
        Log.Companion.e("configuration "+res)
    }

    /**
     * Register USB Receiver
     */
    fun register(){
        val filter = IntentFilter(ACTION_USB_STATE)
        //ACTION_USB_ACCESSORY_ATTACHED may no comes?
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED)
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        filter.addAction(ACTION_USB_PERMISSION)
        context.registerReceiver(usbReceiver, filter)
    }

    /**
     * Unregister USB Receiver and release source
     */
    fun unRegister(){
        context.unregisterReceiver(usbReceiver)
    }

    /**
     * list usb devices attached
     */
    fun listDevice(): List<UsbDevice>{
        //list them
        return listOfDevice
    }

}