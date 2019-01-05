package com.shuyun.androidnotes.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.shuyun.androidnotes.utils.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * $desc$
 * @author shuyun
 * @created on 2018/6/11 0011 18:40
 * @changed on 2018/6/11 0011 18:40
 */
open class LifeCircleActivity: AppCompatActivity() {

    lateinit var user: String
    lateinit var password: String

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.Companion.e("onCreate")
        user = "shuyun"
        password = "123"
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    AlertDialog.Builder(this)
                            .setMessage("MSG")
                            .create().show()
                }
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.e("onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.e("onSaveInstanceState "+outState?.toString())

    }

}