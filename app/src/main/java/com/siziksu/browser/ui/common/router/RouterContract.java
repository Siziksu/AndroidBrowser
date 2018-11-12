package com.siziksu.browser.ui.common.router;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public interface RouterContract {

    void loadFragment(AppCompatActivity activity, int container, Fragment fragment);

    void goToBookmarks(AppCompatActivity activity);
}
