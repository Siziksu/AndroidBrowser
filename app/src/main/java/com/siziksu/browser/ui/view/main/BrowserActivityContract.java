package com.siziksu.browser.ui.view.main;

import android.support.v7.app.AppCompatActivity;

public interface BrowserActivityContract {

    AppCompatActivity getActivity();

    void onAttach(BrowserFragment fragment);
}
