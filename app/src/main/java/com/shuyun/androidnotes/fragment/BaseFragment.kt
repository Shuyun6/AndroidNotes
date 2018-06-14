package com.shuyun.androidnotes.fragment

import android.app.Fragment
import android.widget.Toast

/**
 * $desc$
 * @author shuyun
 * @created on 2018/6/14 0014 11:55
 * @changed on 2018/6/14 0014 11:55
 */
open class BaseFragment: Fragment() {

    protected fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}