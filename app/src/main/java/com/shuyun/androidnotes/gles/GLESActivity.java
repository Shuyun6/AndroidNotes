package com.shuyun.androidnotes.gles;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shuyun.androidnotes.R;

public class GLESActivity extends AppCompatActivity {

    private boolean mIsSupportGLES;
    private ActivityManager activityManager;
    private ConfigurationInfo configurationInfo;
    private GLSurfaceView glSurfaceView;
    private boolean mIsRender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        configurationInfo = activityManager.getDeviceConfigurationInfo();

        mIsSupportGLES = configurationInfo.reqGlEsVersion >= 0X20000;
        if (mIsSupportGLES) {
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(new SimpleRenderer());
            setContentView(glSurfaceView);
            mIsRender = true;
        } else {
            setContentView(R.layout.activity_gles);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsRender) {
            glSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsRender) {
            glSurfaceView.onPause();
        }
    }
}
