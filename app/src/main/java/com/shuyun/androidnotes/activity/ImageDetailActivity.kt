package com.shuyun.androidnotes.activity

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import com.shuyun.androidnotes.R
import kotlinx.android.synthetic.main.activity_imagedetail.*

class ImageDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagedetail)

        ViewCompat.setTransitionName(ivImageDetail, "A")
//        tvContent.alpha = 0f
        var anim = AlphaAnimation(0f, 1f)
        anim.duration = 800
        tvContent.animation = anim
        tvContent.animation.start()

    }

}