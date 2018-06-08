package com.shuyun.androidnotes.customize

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView

/**
 * $desc$
 * @author shuyun
 * @created on 2018/6/8 0008 9:49
 * @changed on 2018/6/8 0008 9:49
 */
class DividerItemDecoration(private var orientation: Int): RecyclerView.ItemDecoration() {

    val attrs = 1
    lateinit var divider: Drawable

}
