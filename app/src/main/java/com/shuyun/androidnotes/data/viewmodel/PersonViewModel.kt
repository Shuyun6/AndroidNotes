package com.shuyun.androidnotes.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableDouble
import android.databinding.ObservableField
import android.databinding.ObservableInt

/**
 * A ViewModel instance for Databinding
 * @author shuyun
 * @created on 2018/8/13 0013 11:29
 * @changed on 2018/8/13 0013 11:29
 */
class PersonViewModel(application: Application): AndroidViewModel(application) {

    val name = ObservableField<String>()
    val sex = ObservableInt()
    val weight = ObservableDouble()


}