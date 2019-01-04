package com.shuyun.androidnotes.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.shuyun.androidnotes.R
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity: AppCompatActivity() {

    val context: Context = this
    val activity: AppCompatActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        ivImage.setOnClickListener {
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair(ivImage, "A"))
            startActivity(Intent(context, ImageDetailActivity::class.java), option.toBundle())
        }

    }

}