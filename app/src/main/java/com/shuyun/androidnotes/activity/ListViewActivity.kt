package com.shuyun.androidnotes.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.shuyun.androidnotes.R
import com.shuyun.androidnotes.data.bean.ListItem
import kotlinx.android.synthetic.main.activity_listview.*
import kotlinx.android.synthetic.main.layout_item_listview.view.*

/**
 * Activity for ListView instance
 * @author shuyun
 * @created on 2018/6/7 0007 16:25
 * @changed on 2018/6/7 0007 16:25
 */
class ListViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)
        val adapter = ListViewAdapter(this)
        val list = ArrayList<ListItem>()
        (0..15).mapTo(list) { ListItem("title" + it, "content" + it) }

        adapter.list = list
        lvContent.adapter = adapter

    }

    /**
     * adapter for ListView
     */
    class ListViewAdapter(private var context: Context) : BaseAdapter() {

        var list: ArrayList<ListItem> = ArrayList()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val llView: LinearLayout
            val viewHolder: ViewHolder
            //if convertView is null(first view or recycled view), then initialize it
            //best performance for ListView
            if (null == convertView) {
                llView = LayoutInflater.from(context).inflate(R.layout.layout_item_listview, parent, false) as LinearLayout
                viewHolder = ViewHolder()
                viewHolder.tvTitle = llView.tvTitle
                viewHolder.tvContent = llView.tvContent
                //set TAG in View for searching next recycle
                llView.tag = viewHolder
            } else {
                llView = convertView as LinearLayout
                //if getting a recycled view, then get the ViewHolder for setting
                viewHolder = llView.tag as ViewHolder
            }
            //get view item then set the values for showing
            viewHolder.tvTitle.text = list[position].title
            viewHolder.tvContent.text = list[position].content
            return llView
        }

        override fun getItem(position: Int): Any {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }

    }

    /**
     * Customized ViewHolder for ListViewAdapter
     */
    class ViewHolder{
        lateinit var tvTitle: TextView
        lateinit var tvContent: TextView
    }

}