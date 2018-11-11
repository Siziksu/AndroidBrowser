package com.siziksu.browser.ui.view.main.webView.clients;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.model.Bookmark;

public class MainWebViewClient extends WebViewClient {

    private Consumer<String> onPageStarted;
    private Consumer<String> onPageFinished;
    private Consumer<String> urlVisited;
    private boolean clearStack;
    private Bookmark current = new Bookmark();

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Log.e(Constants.TAG, "SSL Error");
        handler.proceed();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        onPageStarted(url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (clearStack) {
            clearStack = false;
            current = getHomePage();
            view.clearHistory();
        } else {
            current.title = view.getTitle();
            current.url = view.getOriginalUrl();
        }
        onPageFinished(current);
    }

    public void setListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished, Consumer<String> urlVisited) {
        this.onPageStarted = onPageStarted;
        this.onPageFinished = onPageFinished;
        this.urlVisited = urlVisited;
    }

    public void clearStack() {
        clearStack = true;
        onPageFinished(getHomePage());
    }

    public Bookmark getCurrentPage() {
        return current;
    }

    private void onPageStarted(String url) {
        if (onPageStarted != null) {
            onPageStarted.accept(url);
        }
    }

    private void onPageFinished(Bookmark bookmark) {
        if (onPageFinished != null) {
            onPageFinished.accept(bookmark.url);
        }
        if (urlVisited != null) {
            urlVisited.accept(bookmark.url);
        }
    }

    @NonNull
    private Bookmark getHomePage() {
        Bookmark home = new Bookmark();
        home.title = Constants.URL_HOME_TITLE;
        home.url = Constants.URL_HOME;
        return home;
    }
}
