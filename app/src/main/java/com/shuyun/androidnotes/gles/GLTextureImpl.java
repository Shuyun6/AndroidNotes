package com.shuyun.androidnotes.gles;

import static android.opengl.GLES30.*;

public class GLTextureImpl implements IGLTexture {
    @Override
    public int genTexture() {
        int[] texture = new int[1];
        glGenTextures(1, texture, 0);
        return texture[0];
    }

    @Override
    public void bindTexture(int textureId) {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    @Override
    public void unbindTexture() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void initTextureParams() {
        glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR );
        glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );
        glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE );
        glTexParameteri ( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE );
    }
}
