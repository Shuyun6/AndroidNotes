package com.shuyun.androidnotes.gles;

import android.graphics.Bitmap;

public interface IGLTexture {

    int genTexture();

    void bindTexture(int textureId);

    void setLocation(int location);

    void unbindTexture();

    void initTextureParams();

    void putBitmap(Bitmap bitmap);

}
