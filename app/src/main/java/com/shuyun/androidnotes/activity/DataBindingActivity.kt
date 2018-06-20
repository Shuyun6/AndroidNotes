package com.shuyun.androidnotes.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shuyun.androidnotes.R
import com.shuyun.androidnotes.data.bean.Person
import com.shuyun.androidnotes.databinding.ActivityDatabindingBinding

/**
 * $desc$
 * @author shuyun
 * @created on 2018/6/20 0020 15:49
 * @changed on 2018/6/20 0020 15:49
 */
class DataBindingActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityDatabindingBinding>(this, R.layout.activity_databinding)
        val person = Person("Shuyun", 1)
        binding.person = person


    }

}