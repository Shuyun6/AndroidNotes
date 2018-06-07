package com.shuyun.androidnotes

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shuyun.androidnotes.data.ListItem
import kotlinx.android.synthetic.main.activity_recyclerview.*

/**
 * $desc$
 * @author shuyun
 * @created on 2018/6/7 0007 17:26
 * @changed on 2018/6/7 0007 17:26
 */
class RecyclerViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        val adapter = MAdapter(this)
        val list = ArrayList<ListItem>()
        (0..15).mapTo(list) { ListItem("title"+ it, "content"+ it) }
        adapter.list = list
        recyclerView.adapter = adapter

    }

    class MAdapter(private val context: Context): RecyclerView.Adapter<MViewHolder>() {

        var list = ArrayList<ListItem>()

        override fun onBindViewHolder(holder: MViewHolder, position: Int) {
            val listItem = list[position]
            holder.title.text = listItem.title
            holder.content.text = listItem.content
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
            val llView = LayoutInflater.from(context).inflate(R.layout.layout_item_listview, parent, false)
            //
            return MViewHolder(llView)
        }
    }

    class MViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title: TextView = itemView.findViewById(R.id.tvTitle)
        var content: TextView = itemView.findViewById(R.id.tvContent)
    }

}