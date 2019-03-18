package com.shuyun.androidnotes.gles;

import android.graphics.Rect;

import static android.opengl.GLES30.*;

public class GLDrawImpl implements IGLDraw {
    @Override
    public void clearView() {
        glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void setViewPort(Rect rect) {
        glViewport(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override
    public void setViewPort(int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void drawTriangle(int from, int to) {
        glDrawArrays(GL_TRIANGLES, from, to);
    }

    @Override
    public void updateUniformColor(int id, float red, float green, float blue, float alpha) {
        glUniform4f(id, red, green, blue, alpha);
    }
}
