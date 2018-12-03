package com.siziksu.browser.presenter.main;

import com.siziksu.browser.presenter.BaseViewContract;

public interface BrowserViewContract extends BaseViewContract {

    void finish();

    void superOnBackPressed();

    void clearText();

    void onPageStarted(String url);

    void onPageFinished(String url);

    void onProgress(int progress);
}
