package com.shuyun.androidnotes.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class AutoFitSurfaceView extends SurfaceView {
    public AutoFitSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float rate = 0f;

    public void setRate(int w, int h) {
        rate = (float)w/(float)h;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        if (rate == 0) {
            setMeasuredDimension(w, h);
        } else {
            int ww , hh;
            float r = w>h ? rate : 1f/rate;
            if (w < h * r) {
                hh = h;
                ww = (int) (h * r);
            } else {
                ww = w;
                hh = (int) (w / r);
            }
            setMeasuredDimension(ww, hh);
        }

    }
}
