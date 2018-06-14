package com.shuyun.androidnotes.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shuyun.androidnotes.R
import com.shuyun.androidnotes.utils.Log

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
        Log.Companion.e("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.Companion.e("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.Companion.e("onCreateView")
        return inflater.inflate(R.layout.fragment_normal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.Companion.e("onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.Companion.e("onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.Companion.e("onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.Companion.e("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.Companion.e("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.Companion.e("onDetach")
    }



}
