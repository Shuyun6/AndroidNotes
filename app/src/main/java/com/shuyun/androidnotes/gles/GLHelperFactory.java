package com.shuyun.androidnotes.gles;

import android.content.Context;

public class GLHelperFactory {

    public static IGLHelper getGLHelper(Context context){
        return new GLHelperImpl(context);
    }

}
