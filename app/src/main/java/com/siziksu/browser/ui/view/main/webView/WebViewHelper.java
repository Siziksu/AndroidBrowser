package com.siziksu.browser.ui.view.main.webView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.webkit.DownloadListener;
import android.webkit.WebView;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.ui.common.model.Page;

public class WebViewHelper {

    private MainWebView webView;
    private int x;
    private int y;

    @SuppressLint("ClickableViewAccessibility")
    public WebViewHelper(MainWebView mainWebView) {
        this.webView = mainWebView;
        webView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
            }
            return false;
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init(Context context) {
        webView.init(context);
    }

    public void setPageListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished, Consumer<String> pageVisited) {
        webView.setPageListeners(onPageStarted, onPageFinished, pageVisited);
    }

    public void setProgressListener(Consumer<Integer> progress) {
        webView.setProgressListener(progress);
    }

    public void setDownloadListener(DownloadListener listener) {
        webView.setDownloadListener(listener);
    }

    public void reload() {
        webView.reload();
    }

    public void loadUrl(String userSearch) {
        webView.stopLoading();
        webView.loadUrl(userSearch);
    }

    public String getUrlValidated() {
        return webView.getUrlValidated();
    }

    public Page getCurrentPage() {
        return webView.getCurrentPage();
    }

    public WebView.HitTestResult getHitTestResult() {
        return webView.getHitTestResult();
    }

    public boolean canGoForward() {
        return webView.canGoForward();
    }

    public void goForward() {
        if (webView.canGoForward()) {
            webView.goForward();
        }
    }

    public boolean webViewCanGoBack() {
        webView.stopLoading();
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    public void toggleDesktopSite() {
        webView.toggleDesktopSite();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
