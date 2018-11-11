package com.siziksu.browser.ui.view.main.webView.clients;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.siziksu.browser.common.function.Consumer;

public class MainWebChromeClient extends WebChromeClient {

    private Consumer<Integer> progress;

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (progress != null) {
            progress.accept(newProgress);
        }
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        result.confirm();
        return true;
    }

    public void setListeners(Consumer<Integer> progress) {
        this.progress = progress;
    }
}
