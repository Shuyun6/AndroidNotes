package com.shuyun.androidnotes

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.shuyun.androidnotes.fragment.LifeCircleFragment
import com.shuyun.androidnotes.utils.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val life1 = LifeCircleFragment()
    val life2 = LifeCircleFragment()
    val manager = supportFragmentManager
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        life1.setID("life1")
        life2.setID("life2")
        val bundle1 = Bundle()
        val bundle2 = Bundle()
        bundle1.putInt("color", resources.getColor(R.color.colorAccent))
        bundle2.putInt("color", resources.getColor(R.color.colorPrimary))
        life1.arguments = bundle1
        life2.arguments = bundle2
        if (manager.findFragmentByTag("life1") == null) {
            manager.beginTransaction()
                    .add(R.id.llContent, life1, "life1")
                    .addToBackStack(null)
                    .commit()
        }
        tvMsg.setOnClickListener {
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_right_in,
                            R.anim.slide_left_out,
                            R.anim.slide_left_in,
                            R.anim.slide_right_out)
                    .replace(R.id.llContent, life2, "life2").addToBackStack(null).commit()
        }

    }

    override fun onResume() {
        super.onResume()
        Log.Companion.e("MainActivity: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.Companion.e("MainActivity: onPause")
    }

    override fun onBackPressed() {
        Log.Companion.e("MainActivity: onBackPressed "+tvMsg.visibility)
        super.onBackPressed()
    }

}
