package com.siziksu.browser.ui.view.main.webView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.UrlUtils;
import com.siziksu.browser.ui.common.model.Page;
import com.siziksu.browser.ui.view.main.webView.clients.MainWebChromeClient;
import com.siziksu.browser.ui.view.main.webView.clients.MainWebViewClient;

public class MainWebView extends WebView {

    private static final String DESKTOP_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";

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
        mainWebViewClient = new MainWebViewClient();

        setWebChromeClient(mainWebViewChromeClient);
        setWebViewClient(mainWebViewClient);
        setSaveEnabled(true);

        getSettings().setJavaScriptEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        getSettings().setAppCachePath(context.getCacheDir().getPath());
        getSettings().setAppCacheEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setSupportMultipleWindows(true);
        getSettings().setSaveFormData(false);
        getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setDisplayZoomControls(false);

        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
    }

    public void toggleDesktopSite() {
        enabled = !enabled;
        getSettings().setLoadWithOverviewMode(enabled);
        getSettings().setUseWideViewPort(enabled);
        getSettings().setUserAgentString(enabled ? DESKTOP_AGENT : null);
        reload();
    }

    public void setPageListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished, Consumer<String> pageVisited) {
        mainWebViewClient.setPageListeners(
                url -> {
                    urlValidated = url;
                    onPageStarted.accept(url);
                },
                onPageFinished,
                pageVisited
        );
    }

    public void setProgressListener(Consumer<Integer> progress) {
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

    public Page getCurrentPage() {
        return mainWebViewClient.getCurrentPage();
    }
}
