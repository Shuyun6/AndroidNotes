package com.shuyun.androidnotes.components.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.shuyun.androidnotes.utils.Log;

/**
 * $desc$
 *
 * @author shuyun
 * @created on 2018/10/24 0024 9:38
 * @changed on 2018/10/24 0024 9:38
 */
public class LifePresenter implements LifecycleObserver{

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(){
        Log.Companion.e("LifePresenter onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(){
        Log.Companion.e("LifePresenter onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        Log.Companion.e("LifePresenter onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        Log.Companion.e("LifePresenter onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        Log.Companion.e("LifePresenter onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        Log.Companion.e("LifePresenter onDestroy");
    }

}
