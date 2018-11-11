package com.siziksu.browser.presenter;

import android.app.Activity;

public interface BasePresenterContract<V extends BaseViewContract> {

    void onCreate(Activity activity);

    void onResume(final V view);

    void onDestroy();
}
