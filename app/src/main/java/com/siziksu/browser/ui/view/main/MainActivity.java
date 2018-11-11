package com.siziksu.browser.ui.view.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.utils.UrlUtils;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.main.MainPresenterContract;
import com.siziksu.browser.presenter.model.Bookmark;
import com.siziksu.browser.ui.utils.ActivityUtils;
import com.siziksu.browser.ui.view.main.menu.ImageMenu;
import com.siziksu.browser.ui.view.main.menu.LinkMenu;
import com.siziksu.browser.ui.view.main.menu.OverflowMenu;
import com.siziksu.browser.ui.view.main.webView.MainWebView;
import com.siziksu.browser.ui.view.main.webView.WebViewHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public final class MainActivity extends AppCompatActivity implements BaseViewContract {

    private static final String URL = "url";
    private static final Integer MAX_SWIPE_REFRESH_PROGRESS_VALUE = 60;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.urlEditText)
    EditText urlEditText;
    @BindView(R.id.webViewProgressBar)
    ProgressBar webViewProgressBar;
    @BindView(R.id.actionMore)
    ImageView actionMore;
    @BindView(R.id.webSwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.webView)
    MainWebView webView;

    @Inject
    MainPresenterContract<BaseViewContract> presenter;

    private boolean alreadyStarted;
    private String itemUrl;
    private OverflowMenu overflowMenu;
    private ImageMenu imageMenu;
    private LinkMenu linkMenu;
    private WebViewHelper webViewHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.setWhiteStatusBar(getWindow());
        setContentView(R.layout.activity_main);
        App.get().getApplicationComponent().inject(this);
        presenter.onCreate(this);
        initializeViews();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(this);
        if (!alreadyStarted) {
            alreadyStarted = true;
            Uri url = getIntent().getData();
            if (url != null) {
                loadUrl(url.toString());
            } else {
                presenter.getLastVisited(this::loadUrl);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webViewHelper.canNotGoBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return this;
    }

    @OnClick(R.id.actionMore)
    public void onActionMoreClick() {
        presenter.checkIfItIsBookmarked(
                webViewHelper.getUrlValidated(),
                isBookmarked -> overflowMenu
                        .setIsHome(webViewHelper.isHome())
                        .setCanGoForward(webViewHelper.canGoForward())
                        .setIsBookmarked(isBookmarked)
                        .show()
        );
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        WebView.HitTestResult hitTestResult = webViewHelper.getHitTestResult();
        itemUrl = hitTestResult.getExtra();
        if (webViewHelper.isHomeElement(itemUrl)) { return; }
        switch (hitTestResult.getType()) {
            case WebView.HitTestResult.IMAGE_TYPE:
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                if (!UrlUtils.isDataImage(itemUrl)) {
                    imageMenu.setTouchPosition(webViewHelper.getX(), webViewHelper.getY()).show();
                }
                break;
            case WebView.HitTestResult.SRC_ANCHOR_TYPE:
                linkMenu.setTouchPosition(webViewHelper.getX(), webViewHelper.getY()).show();
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_BOOKMARKS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Bookmark bookmark = data.getParcelableExtra(Constants.EXTRA_KEY_BOOKMARK);
                    loadUrl(bookmark.url);
                    break;
                default:
                    break;
            }
        }
    }

    private void initializeViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        registerForContextMenu(webView);
        webViewHelper = new WebViewHelper(webView);
        webViewHelper.init(this);
        webViewHelper.setListeners(
                url -> {
                    urlEditText.setText(UrlUtils.getUrlToShow(url));
                    swipeRefreshLayout.setRefreshing(true);
                    webViewProgressBar.setVisibility(View.VISIBLE);
                },
                url -> {
                    urlEditText.setText(UrlUtils.getUrlToShow(url));
                    swipeRefreshLayout.setRefreshing(false);
                    webViewProgressBar.setVisibility(View.GONE);
                },
                url -> presenter.setUrlVisited(url),
                progress -> {
                    webViewProgressBar.setProgress(progress);
                    if (progress >= MAX_SWIPE_REFRESH_PROGRESS_VALUE && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        webViewHelper.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> presenter.download(url));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> webViewHelper.reload());
        urlEditText.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEARCH) {
                        loadUrl();
                    }
                    return false;
                }
        );
        overflowMenu = new OverflowMenu.Builder()
                .setActivity(this)
                .setSourceView(actionMore)
                .setListener(id -> {
                    switch (id) {
                        case R.id.actionHome:
                            loadUrl(Constants.URL_HOME);
                            break;
                        case R.id.actionBookmark:
                            presenter.manageBookmark(webViewHelper.getCurrentPage());
                            break;
                        case R.id.actionForward:
                            webViewHelper.goForward();
                            break;
                        case R.id.actionDesktopSite:
                            webViewHelper.toggleDesktopSite();
                            break;
                        case R.id.actionReload:
                            webViewHelper.reload();
                            break;
                        case R.id.actionBookmarks:
                            presenter.onBookmarksClick();
                            break;
                        case R.id.actionHtml5:
                            loadUrl(Constants.URL_HTML5_TEST);
                            break;
                        default:
                            break;
                    }
                })
                .setCancelable(true)
                .create();
        imageMenu = new ImageMenu.Builder()
                .setActivity(this)
                .setListener(id -> {
                    switch (id) {
                        case R.id.actionVisitUrl:
                            loadUrl(itemUrl);
                            break;
                        case R.id.actionDownloadImage:
                            presenter.download(itemUrl);
                            break;
                        case R.id.actionCopyUrl:
                            presenter.copyToClipboard(URL, itemUrl);
                            break;
                        default:
                            break;
                    }
                })
                .setCancelable(true)
                .create();
        linkMenu = new LinkMenu.Builder()
                .setActivity(this)
                .setListener(id -> {
                    switch (id) {
                        case R.id.actionVisitUrl:
                            loadUrl(itemUrl);
                            break;
                        case R.id.actionCopyUrl:
                            presenter.copyToClipboard(URL, itemUrl);
                            break;
                        default:
                            break;
                    }
                })
                .setCancelable(true)
                .create();
    }

    private void loadUrl() {
        ActivityUtils.hideKeyboard(this);
        loadUrl(urlEditText.getText().toString());
    }

    private void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            url = Constants.URL_HOME;
        }
        if (Constants.URL_HOME.equals(url)) {
            webViewHelper.clearStack();
        }
        webViewHelper.loadUrl(url);
    }
}
