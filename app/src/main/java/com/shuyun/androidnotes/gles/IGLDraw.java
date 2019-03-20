package com.shuyun.androidnotes.gles;

import android.graphics.Rect;

public interface IGLDraw {

    void clearView();

    void clearColor(int red, int green, int blue, int alpha);

    void setViewPort(Rect rect);

    void setViewPort(int width, int height);

    void drawTriangle(int from, int to);

    void drawTriangleFan(int from, int to);

    void drawTriangleStrip(int from, int to);

    void updateUniformColor(int id, float red, float green, float blue, float alpha);

}
