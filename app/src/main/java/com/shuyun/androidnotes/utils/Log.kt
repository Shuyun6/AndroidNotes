package com.shuyun.androidnotes.utils

import android.util.Log

/**
 * Customized Log class
 * @author shuyun
 * @created on 2018/6/11 0011 18:42
 * @changed on 2018/6/11 0011 18:42
 */
class Log {

    private val debug = true

    companion object {

        private val TAG = "test"

        fun e(msg: String) {
            Log.e(TAG, msg)
        }

        fun e(tag: String, msg: String) {
            Log.e(tag, msg)
        }

    }

}