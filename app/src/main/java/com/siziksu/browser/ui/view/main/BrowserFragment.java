package com.siziksu.browser.ui.view.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.UrlUtils;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.main.BrowserPresenterContract;
import com.siziksu.browser.ui.common.model.OverflowMenuItem;
import com.siziksu.browser.ui.common.model.Page;
import com.siziksu.browser.ui.common.model.WebViewBack;
import com.siziksu.browser.ui.common.utils.ActivityUtils;
import com.siziksu.browser.ui.view.main.menu.ImageMenu;
import com.siziksu.browser.ui.view.main.menu.LinkMenu;
import com.siziksu.browser.ui.view.main.menu.OverflowMenu;
import com.siziksu.browser.ui.view.main.webView.MainWebView;
import com.siziksu.browser.ui.view.main.webView.WebViewHelper;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowserFragment extends Fragment implements BaseViewContract, BrowserFragmentContract {

    private static final String URL = "url";
    private static final Integer MAX_SWIPE_REFRESH_PROGRESS_VALUE = 60;

    @BindView(R.id.webViewProgressBar)
    ProgressBar webViewProgressBar;
    @BindView(R.id.webSwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.webView)
    MainWebView webView;

    @Inject
    BrowserPresenterContract<BaseViewContract> presenter;

    private boolean alreadyStarted;
    private String itemUrl;
    private OverflowMenu overflowMenu;
    private ImageMenu imageMenu;
    private LinkMenu linkMenu;
    private WebViewHelper webViewHelper;
    private BrowserActivityContract browserActivity;
    private boolean isExternalLink;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            browserActivity = (BrowserActivityContract) context;
            browserActivity.onAttach(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get().getApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_browser, container, false);
        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeViews();
        presenter.onCreate(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(this);
        if (!alreadyStarted) {
            alreadyStarted = true;
            if (getActivity() != null) {
                Uri url = getActivity().getIntent().getData();
                if (url != null) {
                    isExternalLink = true;
                    loadUrl(url.toString());
                } else {
                    presenter.getLastPageVisited(this::loadUrl);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    WebViewBack webViewBack = new WebViewBack();

    @Override
    public void webViewCanGoBack(Consumer<WebViewBack> callback) {
        webViewBack.webViewCanGoBack = webViewHelper.webViewCanGoBack();
        webViewBack.isExternalLink = isExternalLink;
        callback.accept(webViewBack);
    }

    @Override
    public void onActionMoreClick() {
        String url = webViewHelper.getUrlValidated();
        presenter.isUrlBookmarked(
                url,
                isBookmarked -> {
                    browserActivity.onMenuShow();
                    overflowMenu
                            .setUrlValidated(url)
                            .setOnDismissListener(() -> browserActivity.onMenuDismiss())
                            .setCanGoForward(webViewHelper.canGoForward())
                            .setIsBookmarked(isBookmarked)
                            .show();
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_BOOKMARKS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Page page = data.getParcelableExtra(Constants.EXTRA_KEY_BOOKMARK);
                    loadUrl(page.url);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return browserActivity.getActivity();
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        WebView.HitTestResult hitTestResult = webViewHelper.getHitTestResult();
        itemUrl = hitTestResult.getExtra();
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

    private void initializeViews() {
        registerForContextMenu(webView);
        webViewHelper = new WebViewHelper(webView);
        webViewHelper.init(getActivity());
        webViewHelper.setPageListeners(
                url -> {
                    browserActivity.getEditTed().setText(UrlUtils.getUrlToShow(url));
                    swipeRefreshLayout.setRefreshing(true);
                    webViewProgressBar.setVisibility(View.VISIBLE);
                },
                url -> {
                    if (getActivity() != null) {
                        ActivityUtils.hideKeyboard(getActivity());
                    }
                    browserActivity.getEditTed().setText(UrlUtils.getUrlToShow(url));
                    swipeRefreshLayout.setRefreshing(false);
                    webViewProgressBar.setVisibility(View.GONE);
                },
                url -> presenter.setPageVisited(url)
        );
        webViewHelper.setProgressListener(
                progress -> {
                    webViewProgressBar.setProgress(progress);
                    if (progress >= MAX_SWIPE_REFRESH_PROGRESS_VALUE && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        webViewHelper.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> presenter.download(url));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (TextUtils.isEmpty(webViewHelper.getUrlValidated())) {
                swipeRefreshLayout.setRefreshing(false);
            } else {
                webViewHelper.reload();
            }
        });
        List<OverflowMenuItem> items = Arrays.asList(
                new OverflowMenuItem(R.id.actionBookmarks, getString(R.string.bookmarks), OverflowMenuItem.DEFAULT),
                new OverflowMenuItem(R.id.actionDesktopSite, getString(R.string.desktop_site), OverflowMenuItem.CHECKBOX),
                new OverflowMenuItem(R.id.actionGoogle, getString(R.string.google), OverflowMenuItem.DEFAULT));
        overflowMenu = new OverflowMenu.Builder()
                .setActivity(getAppCompatActivity())
                .setSourceView(browserActivity.getActionMoreView())
                .setListener(this::onOverflowMenuClick)
                .setCancelable(true)
                .setItems(items)
                .create();
        browserActivity.getEditTed().setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEARCH) {
                        loadUrl();
                    }
                    return false;
                }
        );
        imageMenu = new ImageMenu.Builder()
                .setActivity(getAppCompatActivity())
                .setListener(this::onImageMenuClick)
                .setCancelable(true)
                .create();
        linkMenu = new LinkMenu.Builder()
                .setActivity(getAppCompatActivity())
                .setListener(this::onLinkMenuClick)
                .setCancelable(true)
                .create();
    }

    private void onOverflowMenuClick(int id) {
        switch (id) {
            case R.id.actionHome:

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
            case R.id.actionGoogle:
                webViewHelper.loadUrl(Constants.URL_GOOGLE);
                break;
            case R.id.actionReload:
                webViewHelper.reload();
                break;
            case R.id.actionBookmarks:
                presenter.onBookmarksButtonClick();
                break;
            default:
                break;
        }
    }

    private void onImageMenuClick(Integer id) {
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
    }

    private void onLinkMenuClick(Integer id) {
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
    }

    private void loadUrl() {
        if (getActivity() != null) {
            ActivityUtils.hideKeyboard(getActivity());
        }
        loadUrl(browserActivity.getEditTed().getText().toString());
    }

    private void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        webViewHelper.loadUrl(url);
    }
}
