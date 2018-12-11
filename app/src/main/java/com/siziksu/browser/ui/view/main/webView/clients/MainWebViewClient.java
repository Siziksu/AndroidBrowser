package com.siziksu.browser.ui.view.main.webView.clients;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.Print;
import com.siziksu.browser.presenter.main.FragmentManagerSupplier;
import com.siziksu.browser.ui.common.model.BrowserActivity;
import com.siziksu.browser.ui.common.model.Page;
import com.siziksu.browser.ui.view.main.menu.dialog.LoginDialogFragment;

import java.util.Map;

import javax.inject.Inject;

public class MainWebViewClient extends WebViewClient {

    private static final long HALF_SECOND = 500;

    private Consumer<String> onPageStarted;
    private Consumer<String> onPageFinished;
    private Consumer<String> pageVisited;
    private Consumer<BrowserActivity> onActivity;
    private Page page = new Page();
    private BrowserActivity activity = new BrowserActivity();
    private String currentUrl;
    private FragmentManagerSupplier fragmentManagerSupplier;
    private boolean isGoingBack;

    @Inject
    public MainWebViewClient() {}

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (isGoingBack) { return false; }
        if (url.toLowerCase().equals(currentUrl.toLowerCase())) { return false; }
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
        currentUrl = url;
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

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        LoginDialogFragment fragment = new LoginDialogFragment();
        fragment.setListener(credentials -> handler.proceed(credentials.user, credentials.password));
        fragment.setDismissListener(() -> super.onReceivedHttpAuthRequest(view, handler, host, realm));
        fragment.setCancelable(false);
        fragment.show(fragmentManagerSupplier.supplySupportFragmentManager(), Constants.CREDENTIALS_POPUP);
    }

    public void setFragmentManagerSupplier(FragmentManagerSupplier fragmentManagerSupplier) {
        this.fragmentManagerSupplier = fragmentManagerSupplier;
    }

    public void setPageListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished, Consumer<String> pageVisited) {
        this.onPageStarted = onPageStarted;
        this.onPageFinished = onPageFinished;
        this.pageVisited = pageVisited;
    }

    public void setActivityListener(Consumer<BrowserActivity> onActivity) {
        this.onActivity = onActivity;
    }

    public Page getCurrentPage() {
        return page;
    }

    public void isLoadingAnUrl() {
        isGoingBack = false;
    }

    public void isGoingBack() {
        isGoingBack = true;
    }

    private void onPageStarted(WebView view) {
        activity.date = System.currentTimeMillis();
        activity.title = view.getUrl();
        activity.url = view.getUrl();
        page.title = view.getUrl();
        page.url = view.getUrl();
        if (onPageStarted != null) {
            onPageStarted.accept(page.url);
        }
        onActivity();
        pageVisited();
    }

    private void onPageFinished(WebView view) {
        activity.date = System.currentTimeMillis();
        activity.title = view.getTitle();
        activity.url = view.getUrl();
        page.title = view.getTitle();
        page.url = view.getUrl();
        if (onPageFinished != null) {
            onPageFinished.accept(page.url);
        }
        onActivity();
        pageVisited();
    }

    private void pageVisited() {
        if (pageVisited != null) {
            pageVisited.accept(page.url);
        }
    }

    private void onActivity() {
        if (onActivity != null) {
            onActivity.accept(activity);
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
