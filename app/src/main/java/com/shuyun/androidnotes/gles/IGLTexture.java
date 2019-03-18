package com.shuyun.androidnotes.gles;

public interface IGLTexture {

    int genTexture();

    void bindTexture(int textureId);

    void unbindTexture();

    void initTextureParams();

}
