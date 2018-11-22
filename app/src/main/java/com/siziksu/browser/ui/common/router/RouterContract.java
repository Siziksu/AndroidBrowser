package com.siziksu.browser.ui.common.router;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public interface RouterContract {

    void loadFragment(AppCompatActivity activity, int container, Fragment fragment);

    void goToMainActivity(AppCompatActivity activity);

    void goToMainActivity(AppCompatActivity activity, Bundle options);

    void goToBookmarks(AppCompatActivity activity);
}
