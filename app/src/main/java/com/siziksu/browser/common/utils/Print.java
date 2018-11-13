package com.siziksu.browser.common.utils;

import android.util.Log;

import com.siziksu.browser.common.Constants;

public class Print {

    public static void info(String string) {
        Log.i(Constants.TAG, string);
    }

    public static void error(String string) {
        Log.e(Constants.TAG, string);
    }

    public static void error(String string, Throwable throwable) {
        Log.e(Constants.TAG, string, throwable);
    }
}
