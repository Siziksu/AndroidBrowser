package com.siziksu.browser.ui.view.main.webView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.webkit.DownloadListener;
import android.webkit.WebView;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.model.Bookmark;

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

    public void setListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished, Consumer<String> urlVisited, Consumer<Integer> progress) {
        webView.setListeners(onPageStarted, onPageFinished, urlVisited, progress);
    }

    public void setDownloadListener(DownloadListener listener) {
        webView.setDownloadListener(listener);
    }

    public void clearStack() {
        webView.clearStack();
    }

    public boolean isHome() {
        return webView.isHome();
    }

    public boolean isHomeElement(String url) {
        return webView.isHomeElement(url);
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

    public Bookmark getCurrentPage() {
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

    public boolean canNotGoBack() {
        if (webView.canGoBack()) {
            webView.stopLoading();
            webView.goBack();
            return false;
        }
        return true;
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
