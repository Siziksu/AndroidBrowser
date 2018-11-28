package com.siziksu.browser.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.pm.PackageInfoCompat;

import java.util.Locale;

public class SystemUtils {

    private SystemUtils() {}

    public static String getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            return String.format(Locale.getDefault(), "v%s (%d)", packageInfo.versionName, PackageInfoCompat.getLongVersionCode(packageInfo));
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }
}
