package com.shuyun.androidnotes.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shuyun.androidnotes.utils.Log

/**
 * $desc$
 * @author shuyun
 * @created on 2018/6/11 0011 18:40
 * @changed on 2018/6/11 0011 18:40
 */
open class LifeCircleActivity: AppCompatActivity() {

    lateinit var user: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.Companion.e("onCreate")
        user = "shuyun"
        password = "123"
    }

    override fun onRestart() {
        super.onRestart()
        Log.Companion.e("onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.Companion.e("onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.Companion.e("onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.Companion.e("onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.Companion.e("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.Companion.e("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.Companion.e("onSaveInstanceState")

    }

}