package com.shuyun.androidnotes.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager

/**
 * USB Service for controlling
 * @author shuyun
 * @created on 2018/6/8 0008 19:21
 * @changed on 2018/6/8 0008 19:21
 */
class USBService {

    val ACTION_USB_PERMISSION = "com.shuyun.USB_PERMISSION" + hashCode()

    lateinit var context: Context
    private val listOfDevice = ArrayList<UsbDevice>()

    /**
     * use 'object' like Java anonymous internal class
     */
    private val usbReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                ACTION_USB_PERMISSION -> {
                    val usbDevice: UsbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        //use permission
                    } else {
                        //failed to use permission
                    }
                }
                UsbManager.ACTION_USB_ACCESSORY_ATTACHED -> {
                    val usbDevice: UsbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                    //process attach event
                }
                UsbManager.ACTION_USB_ACCESSORY_DETACHED -> {
                    val usbDevice: UsbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                    //process detach event
                }
            }
        }
    }

    /**
     * Register USB Receiver
     */
    fun register(){
        val permissionIntent = PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION), 0)
        val filter = IntentFilter(ACTION_USB_PERMISSION)
        //ACTION_USB_ACCESSORY_ATTACHED may no comes?
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED)
        context.registerReceiver(usbReceiver, filter)
    }

    /**
     * Unregister USB Receiver and release source
     */
    fun unRegister(){}

    /**
     * list usb devices attached
     */
    fun listDevice(): List<UsbDevice>{
        //list them
        return listOfDevice
    }

}