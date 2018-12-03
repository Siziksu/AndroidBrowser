package com.siziksu.browser.ui.view.main;

import android.content.Intent;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.WebViewBack;

public interface BrowserFragmentContract {

    void webViewCanGoBack(Consumer<WebViewBack> callback);

    void onActionMoreClick();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
