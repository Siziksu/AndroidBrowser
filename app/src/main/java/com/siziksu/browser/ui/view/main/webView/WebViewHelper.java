package com.siziksu.browser.ui.view.main.webView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.main.FragmentManagerSupplier;
import com.siziksu.browser.ui.common.model.BrowserActivity;
import com.siziksu.browser.ui.common.model.Page;

import javax.inject.Inject;

public final class WebViewHelper {

    private MainWebView webView;
    private int x;
    private int y;

    @Inject
    public WebViewHelper() {}

    @SuppressLint("SetJavaScriptEnabled,ClickableViewAccessibility")
    public void init(Context context, MainWebView mainWebView) {
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
        webView.init(context);
    }

    public void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
    }

    public void setFragmentManagerSupplier(FragmentManagerSupplier fragmentManagerSupplier) {
        webView.setFragmentManagerSupplier(fragmentManagerSupplier);
    }

    public void setPageListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished, Consumer<String> pageVisited) {
        webView.setPageListeners(onPageStarted, onPageFinished, pageVisited);
    }

    public void setActivityListener(Consumer<BrowserActivity> onActivity) {
        webView.setActivityListener(onActivity);
    }

    public void setProgressListener(Consumer<Integer> progress) {
        webView.setProgressListener(progress);
    }

    public void setVideoViewShowingListener(Consumer<View> videoViewListener) {
        webView.setVideoViewShowingListener(videoViewListener);
    }

    public void setVideoViewHidingListener(Consumer<View> videoViewHidingListener) {
        webView.setVideoViewHidingListener(videoViewHidingListener);
    }

    public void setDownloadListener(DownloadListener listener) {
        webView.setDownloadListener(listener);
    }

    public void reload() {
        webView.reload();
    }

    public void loadUrl(String userSearch) {
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

    public boolean canNotGoBack() {
        if (webView == null) { return true; }
        return !webView.canGoBack();
    }

    public void goBack() {
        webView.goBack();
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
