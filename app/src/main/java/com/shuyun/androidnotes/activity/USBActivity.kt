package com.shuyun.androidnotes.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shuyun.androidnotes.R
import com.shuyun.androidnotes.service.USBService

/**
 * $desc$
 * @author shuyun
 * @created on 2018/6/13 0013 9:22
 * @changed on 2018/6/13 0013 9:22
 */
class USBActivity: AppCompatActivity(){

    lateinit var service: USBService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usb)

    }

    override fun onStart() {
        super.onStart()
        service = USBService(this)
        service.register()
    }

    override fun onStop() {
        super.onStop()
        service.unRegister()
    }

}