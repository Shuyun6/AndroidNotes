package com.shuyun.androidnotes.gles;

import android.content.Context;

public class GLHelperFactory {

    private static volatile IGLHelper helper;

    public static IGLHelper getGLHelper(Context context){
        if (null == helper) {
            synchronized (GLHelperFactory.class) {
                if (null == helper) {
                    helper = new GLHelperProxy(context);
                }
            }
        }
        return helper;
    }

}
