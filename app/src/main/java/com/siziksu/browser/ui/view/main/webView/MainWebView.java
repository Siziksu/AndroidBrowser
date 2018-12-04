package com.siziksu.browser.ui.view.main.webView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.siziksu.browser.App;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.presenter.main.FragmentManagerSupplier;
import com.siziksu.browser.presenter.main.WebViewPresenterContract;
import com.siziksu.browser.presenter.main.WebViewViewContract;
import com.siziksu.browser.ui.common.model.Page;
import com.siziksu.browser.ui.view.main.webView.clients.MainWebChromeClient;
import com.siziksu.browser.ui.view.main.webView.clients.MainWebViewClient;

import javax.inject.Inject;

public final class MainWebView extends WebView implements WebViewViewContract {

    private static final String DESKTOP_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";

    @Inject
    WebViewPresenterContract presenter;

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
        App.get().getApplicationComponent().inject(this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init(Context context) {
        presenter.register(this);

        mainWebViewChromeClient = new MainWebChromeClient();
        mainWebViewChromeClient.setOnBlankLinkListener(this::loadUrl);
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
        getSettings().setAllowFileAccessFromFileURLs(true);
        getSettings().setAllowUniversalAccessFromFileURLs(true);

        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
    }

    public void toggleDesktopSite() {
        enabled = !enabled;
        getSettings().setLoadWithOverviewMode(enabled);
        getSettings().setUseWideViewPort(enabled);
        getSettings().setUserAgentString(enabled ? DESKTOP_AGENT : null);
        reload();
    }

    public void setFragmentManagerSupplier(FragmentManagerSupplier fragmentManagerSupplier) {
        mainWebViewClient.setFragmentManagerSupplier(fragmentManagerSupplier);
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
        mainWebViewChromeClient.setProgressListener(progress);
    }

    @Override
    public void loadUrl(String userSearch) {
        presenter.filterUrl(userSearch);
    }

    public void onUrlFiltered(String url) {
        urlValidated = url;
        mainWebViewClient.isLoadingAnUrl();
        super.stopLoading();
        super.loadUrl(url);
    }

    public String getUrlValidated() {
        return urlValidated;
    }

    public Page getCurrentPage() {
        return mainWebViewClient.getCurrentPage();
    }

    @Override
    public void goBack() {
        mainWebViewClient.isGoingBack();
        super.goBack();
    }
}
