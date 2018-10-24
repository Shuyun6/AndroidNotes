package com.shuyun.androidnotes.components.lifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shuyun.androidnotes.R;
import com.shuyun.androidnotes.utils.Log;

/**
 * $desc$
 *
 * @author shuyun
 * @created on 2018/10/24 0024 9:38
 * @changed on 2018/10/24 0024 9:38
 */
public class LifeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.Companion.e("LifeActivity onCreate1");
        getLifecycle().addObserver(new LifePresenter());

        Log.Companion.e("LifeActivity onCreate2");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.Companion.e("LifeActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.Companion.e("LifeActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.Companion.e("LifeActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.Companion.e("LifeActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.Companion.e("LifeActivity onDestroy");
    }
}
