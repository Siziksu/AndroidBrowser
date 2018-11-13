package com.siziksu.browser.ui.view.main.webView.clients;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.Print;
import com.siziksu.browser.ui.common.model.Page;

import java.util.Map;

public class MainWebViewClient extends WebViewClient {

    private Consumer<String> onPageStarted;
    private Consumer<String> onPageFinished;
    private Consumer<String> pageVisited;
    private boolean clearStack;
    private Page page = new Page();

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Print.error("SSL Error");
        handler.proceed();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        page.title = view.getUrl();
        page.url = view.getUrl();
        onPageStarted();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (clearStack) {
            clearStack = false;
            setHomePage();
            view.clearHistory();
        } else {
            page.title = view.getTitle();
            page.url = view.getUrl();
        }
        onPageFinished();
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Print.error("Internet connection error, error code: " + error.getErrorCode() + ", description: " + error.getDescription());
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        Print.error("Http error, status code: " + errorResponse.getStatusCode());
        Map<String, String> headers = errorResponse.getResponseHeaders();
        for (String key : headers.keySet()) {
            Print.error(key + " -> " + headers.get(key));
        }
        super.onReceivedHttpError(view, request, errorResponse);
    }

    public void setListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished, Consumer<String> pageVisited) {
        this.onPageStarted = onPageStarted;
        this.onPageFinished = onPageFinished;
        this.pageVisited = pageVisited;
    }

    public void clearStack() {
        clearStack = true;
        onPageFinished();
    }

    public Page getCurrentPage() {
        return page;
    }

    private void onPageStarted() {
        if (onPageStarted != null) {
            onPageStarted.accept(page.url);
        }
        pageVisited();
    }

    private void onPageFinished() {
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

    private void setHomePage() {
        page.title = Constants.URL_HOME_TITLE;
        page.url = Constants.URL_HOME;
    }
}
