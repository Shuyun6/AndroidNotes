package com.shuyun.androidnotes.gles;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import static android.opengl.GLES30.*;

public class GLTextureImpl implements IGLTexture {
    @Override
    public int genTexture() {
        int[] texture = new int[1];
        //n: number of texture you want to generate
        glGenTextures(1, texture, 0);
        return texture[0];
    }

    @Override
    public void bindTexture(int textureId) {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    @Override
    public void setLocation(int location) {
        glUniform1i(location, 0);
    }

    @Override
    public void unbindTexture() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void initTextureParams() {
        glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR );
        glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );
//        glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE );
//        glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE );
    }

    @Override
    public void putBitmap(Bitmap bitmap) {
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
}
