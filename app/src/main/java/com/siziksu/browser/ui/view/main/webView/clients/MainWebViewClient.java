package com.siziksu.browser.ui.view.main.webView.clients;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.Print;
import com.siziksu.browser.ui.common.model.Page;

import java.util.Map;

public class MainWebViewClient extends WebViewClient {

    private static final long HALF_SECOND = 500;

    private Consumer<String> onPageStarted;
    private Consumer<String> onPageFinished;
    private Consumer<String> pageVisited;
    private Page page = new Page();
    private String currentUrl;

    public MainWebViewClient() {}

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.toLowerCase().equals(currentUrl)) { return false; }
        view.postDelayed(() -> view.loadUrl(url), HALF_SECOND);
        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Print.error("SSL error for the url: " + error.getUrl());
        handler.proceed();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        currentUrl = url.toLowerCase();
        onPageStarted(view);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        onPageFinished(view);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Print.error("Internet connection error, error code: " + error.getErrorCode() + ", description: " + error.getDescription());
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        Print.error("Http error, status code: " + errorResponse.getStatusCode() + ", url: " + view.getUrl());
        printErrorHeaders(errorResponse);
    }

    public void setPageListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished, Consumer<String> pageVisited) {
        this.onPageStarted = onPageStarted;
        this.onPageFinished = onPageFinished;
        this.pageVisited = pageVisited;
    }

    public Page getCurrentPage() {
        return page;
    }

    private void onPageStarted(WebView view) {
        page.title = view.getUrl();
        page.url = view.getUrl();
        if (onPageStarted != null) {
            onPageStarted.accept(page.url);
        }
        pageVisited();
    }

    private void onPageFinished(WebView view) {
        page.title = view.getTitle();
        page.url = view.getUrl();
        if (onPageFinished != null) {
            onPageFinished.accept(page.url);
        }
        pageVisited();
    }

    private void pageVisited() {
        if (pageVisited != null) {
            pageVisited.accept(page.url);
        }
    }

    private void printErrorHeaders(WebResourceResponse errorResponse) {
        Map<String, String> headers = errorResponse.getResponseHeaders();
        StringBuilder builder = new StringBuilder();
        builder.append("Error headers:\n{\n");
        for (String key : headers.keySet()) {
            builder.append("  ").append(key).append(" : ").append(headers.get(key)).append(",\n");
        }
        builder.append("}");
        Print.error(builder.toString());
    }
}
