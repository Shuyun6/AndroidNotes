package com.shuyun.androidnotes.fragment

import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * $desc$
 * @author shuyun
 * @created on 2018/6/14 0014 11:55
 * @changed on 2018/6/14 0014 11:55
 */
open class BaseFragment: Fragment() {

    protected var id: String = "default_id"

    open fun setID(id: String) {
        this.id = id
    }

    protected fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}