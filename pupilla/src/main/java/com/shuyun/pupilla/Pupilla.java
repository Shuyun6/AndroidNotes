package com.shuyun.pupilla;

/**
 *
 * @author shuyun
 * create at 2018/6/12 0012 15:49
 * change at 2018/6/12 0012 15:49
*/
public class Pupilla {

    static {
        System.loadLibrary("pupilla");
    }

    public native String getConfiguration();

}
