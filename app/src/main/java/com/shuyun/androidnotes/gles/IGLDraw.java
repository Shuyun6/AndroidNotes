package com.shuyun.androidnotes.gles;

import android.graphics.Rect;

public interface IGLDraw {

    void clearView();

    void setViewPort(Rect rect);

    void setViewPort(int width, int height);

    void drawTriangle(int from, int to);

    void updateUniformColor(int id, float red, float green, float blue, float alpha);

}
