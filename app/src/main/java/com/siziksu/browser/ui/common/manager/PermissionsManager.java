package com.siziksu.browser.ui.common.manager;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

public class PermissionsManager {

    private PermissionsManager() {}

    public static void checkPermissions(Activity context) {
        String[] PERMISSIONS = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ActivityCompat.requestPermissions(context, PERMISSIONS, 1);
    }

}
