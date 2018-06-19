package com.shuyun.androidnotes.activity

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shuyun.androidnotes.R
import kotlinx.android.synthetic.main.activity_svg.*

/**
 * activity for vector animation
 * @author shuyun
 * @created on 2018/6/19 0019 10:02
 * @changed on 2018/6/19 0019 10:02
 */
class SVGActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svg)

        val anim = ivBell.drawable as Animatable
        ivBell.setOnClickListener { anim.start() }

    }

}