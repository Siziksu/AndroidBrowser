package com.siziksu.browser.presenter.launch;

import android.app.Activity;
import android.widget.EditText;

import com.siziksu.browser.presenter.BasePresenterContract;
import com.siziksu.browser.presenter.BaseViewContract;

public interface LaunchPresenterContract<V extends BaseViewContract> extends BasePresenterContract<V> {

    void setIntentData(Activity activity);

    void onUrlEditTextClick(EditText urlEditText);

    void clearLastPageVisited();
}
