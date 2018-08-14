package com.shuyun.androidnotes.components;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.shuyun.androidnotes.utils.Log;

/**
 * $desc$
 *
 * @author shuyun
 * @created on 2018/8/1 0001 11:43
 * @changed on 2018/8/1 0001 11:43
 */
public class TestService extends Service {

    private Binder binder;
    private Handler handler;

    class ABinder extends Binder{

        public String getConfiguration(String msg){
            return msg;
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.Companion.e("TestService onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        Log.Companion.e("TestService onCreate");
        binder = new Binder();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.Companion.e("TestService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.Companion.e("TestService onRebind");
        super.onRebind(intent);

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.Companion.e("TestService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.Companion.e("TestService onDestroy");
        super.onDestroy();
    }


}
