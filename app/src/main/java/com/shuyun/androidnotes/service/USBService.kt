package com.shuyun.androidnotes.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import com.shuyun.androidnotes.utils.Log

/**
 * USB Service for controlling
 * @author shuyun
 * @created on 2018/6/8 0008 19:21
 * @changed on 2018/6/8 0008 19:21
 */
class USBService(var context: Context) {

    private val ACTION_USB_PERMISSION = "com.shuyun.USB_PERMISSION" + hashCode()
    private val ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE"

    private val listOfDevice = ArrayList<UsbDevice>()

    private val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager

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
                    } else {
                        Log.Companion.e("failed to get permission")
                        //failed to use permission
                    }
                }
                UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                    //process attach event
                    Log.Companion.e("ACTION_USB_DEVICE_ATTACHED")
                    val usbDevice: UsbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                    updatePermission(usbDevice)
                }
                UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                    //process detach event
                    Log.Companion.e("ACTION_USB_DEVICE_DETACHED")
                    val usbDevice: UsbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

                }
            }
        }
    }

    private fun test(device: UsbDevice){
        val conn = usbManager.openDevice(device)
        val fs = conn.fileDescriptor
        Log.Companion.e("conn.fileDescriptor "+fs)
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

    private fun updatePermission(device: UsbDevice){
        if (!usbManager.hasPermission(device)) {
            val permissionIntent = PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION), 0)
            usbManager.requestPermission(device, permissionIntent)
        }
    }

    /**
     * list usb devices attached
     */
    fun listDevice(): List<UsbDevice>{
        //list them
        return listOfDevice
    }

}