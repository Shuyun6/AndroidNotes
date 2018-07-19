package com.shuyun.androidnotes.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.shuyun.androidnotes.R
import com.shuyun.androidnotes.utils.Log
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Note of Android Permission
 *
 * @author shuyun
 * @created on 2018/6/15 0015 14:09
 * @changed on 2018/6/15 0015 14:09
 */
class PermissionActivity: LifeCircleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvMsg.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE))
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        }

    }

    private fun requestPermission(){
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                Log.Companion.e("shouldShowRequestPermissionRationale")
//            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CAMERA), 1)
//            }
        }
    }

    private fun requestPermissions(permissions: Array<out String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, permissions, 1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            for (index in permissions.indices) {
                Log.Companion.e(permissions[index]+": "+grantResults[index])
            }
        }
    }

}