package com.siziksu.browser.presenter.main;

import com.siziksu.browser.presenter.BaseViewContract;

public interface BrowserViewContract extends BaseViewContract {

    void finish();

    void superOnBackPressed();
}
