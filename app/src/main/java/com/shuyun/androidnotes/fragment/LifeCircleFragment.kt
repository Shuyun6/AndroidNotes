package com.shuyun.androidnotes.fragment

import android.content.Context
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.shuyun.androidnotes.R
import com.shuyun.androidnotes.utils.Log
import kotlinx.android.synthetic.main.fragment_normal.*

/**
 * $desc$
 * @author shuyun
 * @created on 2018/6/14 0014 11:44
 * @changed on 2018/6/14 0014 11:44
 */
class LifeCircleFragment: BaseFragment(){

    private fun getInstance(): LifeCircleFragment{
        return LifeCircleFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.Companion.e("LifeCircleFragment: onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.Companion.e("LifeCircleFragment: onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.Companion.e("LifeCircleFragment: onCreateView")
        val view = inflater.inflate(R.layout.fragment_normal, container, false)
        val bundle = arguments
        val color = bundle?.getInt("color")?:1
        Log.Companion.e("color "+color)
        val llRoot = view.findViewById<LinearLayout>(R.id.llRoot)
        llRoot.setBackgroundColor(color)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.Companion.e("LifeCircleFragment: onActivityCreated")
//        val bundle = arguments
//        val color = bundle?.getInt("color")?:1
//        Log.Companion.e("color "+color)
//        llRoot.setBackgroundColor(color)
    }

    override fun onStart() {
        super.onStart()
        Log.Companion.e("LifeCircleFragment: onStart")
        tvContent.text = id
    }

    override fun onResume() {
        super.onResume()
        Log.Companion.e("LifeCircleFragment: onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.Companion.e("LifeCircleFragment: onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.Companion.e("LifeCircleFragment: onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.Companion.e("LifeCircleFragment: onDetach")
    }



}
