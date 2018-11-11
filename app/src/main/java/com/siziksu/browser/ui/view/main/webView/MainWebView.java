package com.siziksu.browser.ui.view.main.webView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.UrlUtils;
import com.siziksu.browser.presenter.model.Bookmark;
import com.siziksu.browser.ui.view.main.webView.clients.MainWebChromeClient;
import com.siziksu.browser.ui.view.main.webView.clients.MainWebViewClient;

public class MainWebView extends WebView {

    private MainWebChromeClient mainWebViewChromeClient;
    private MainWebViewClient mainWebViewClient;

    private String urlValidated;
    private boolean enabled;

    public MainWebView(Context context) {
        this(context, null);
    }

    public MainWebView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
    }

    public MainWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init(Context context) {
        mainWebViewChromeClient = new MainWebChromeClient();
        setWebChromeClient(mainWebViewChromeClient);
        mainWebViewClient = new MainWebViewClient();
        setWebViewClient(mainWebViewClient);
        setSaveEnabled(true);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        getSettings().setAppCacheEnabled(true);
        getSettings().setAppCachePath(context.getCacheDir().getPath());
        getSettings().setDomStorageEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setSupportMultipleWindows(true);
        getSettings().setSaveFormData(false);
        getSettings().setDatabaseEnabled(true);
        getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
    }

    public void toggleDesktopSite() {
        enabled = !enabled;
        Log.i(Constants.TAG, "Current User Agent: " + getSettings().getUserAgentString());
        String userAgent = getSettings().getUserAgentString();
        String newUserAgent;
        String substring = getSettings().getUserAgentString().substring(userAgent.indexOf("("), userAgent.indexOf(")") + 1);
        if (enabled) {
            newUserAgent = getSettings().getUserAgentString().replace(substring, "(X11; Linux x86_64)");
        } else {
            newUserAgent = getSettings().getUserAgentString().replace(substring, "(Linux; Android 6.0.1; D6603 Build/23.5.A.1.291; wv)");
        }
        clearCache(true);
        Log.i(Constants.TAG, "New User Agent: " + getSettings().getUserAgentString());
        getSettings().setSupportZoom(enabled);
        getSettings().setBuiltInZoomControls(enabled);
        getSettings().setDisplayZoomControls(false);
        getSettings().setUserAgentString(newUserAgent);
        getSettings().setLoadWithOverviewMode(enabled);
        getSettings().setUseWideViewPort(enabled);
        reload();
    }

    public void setListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished, Consumer<String> urlVisited, Consumer<Integer> progress) {
        mainWebViewClient.setListeners(
                url -> {
                    urlValidated = url;
                    onPageStarted.accept(url);
                },
                onPageFinished,
                urlVisited);
        mainWebViewChromeClient.setListeners(progress);
    }

    public void clearStack() {
        urlValidated = Constants.URL_HOME;
        mainWebViewClient.clearStack();
    }

    public boolean isHome() {
        return Constants.URL_HOME.equals(urlValidated);
    }

    public boolean isHomeElement(String url) {
        return url == null || url.contains(Constants.ASSETS_URL_PREFIX);
    }

    @Override
    public void loadUrl(String userSearch) {
        if (userSearch.length() == 0) {
            userSearch = Constants.URL_GOOGLE_SEARCH;
        }
        String url = UrlUtils.validateUrl(userSearch);
        if (url == null) {
            url = Constants.URL_GOOGLE_SEARCH;
        }
        urlValidated = url;
        super.loadUrl(url);
    }

    public String getUrlValidated() {
        return urlValidated;
    }

    public Bookmark getCurrentPage() {
        return mainWebViewClient.getCurrentPage();
    }
}
