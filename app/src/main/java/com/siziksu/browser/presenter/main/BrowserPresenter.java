package com.siziksu.browser.presenter.main;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.View;
import android.webkit.WebView;

import com.siziksu.browser.App;
import com.siziksu.browser.R;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.main.BrowserDomainContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.mapper.PageMapper;
import com.siziksu.browser.ui.common.manager.DownloaderManager;
import com.siziksu.browser.ui.common.model.OverflowMenuItem;
import com.siziksu.browser.ui.common.model.Page;
import com.siziksu.browser.ui.common.model.WebViewBack;
import com.siziksu.browser.ui.common.router.RouterContract;
import com.siziksu.browser.ui.common.utils.ActivityUtils;
import com.siziksu.browser.ui.view.main.menu.ImageMenu;
import com.siziksu.browser.ui.view.main.menu.LinkMenu;
import com.siziksu.browser.ui.view.main.menu.OverflowMenu;
import com.siziksu.browser.ui.view.main.webView.MainWebView;
import com.siziksu.browser.ui.view.main.webView.WebViewHelper;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public final class BrowserPresenter implements BrowserPresenterContract<BaseViewContract>, FragmentManagerSupplier {

    private static final String URL = "url";

    @Inject
    RouterContract router;
    @Inject
    BrowserDomainContract domain;

    private BaseViewContract view;
    private String itemUrl;
    private ClipboardManager clipboard;
    private WebViewHelper webViewHelper;
    private OverflowMenu overflowMenu;
    private ImageMenu imageMenu;
    private LinkMenu linkMenu;
    private boolean isExternalLink;
    private WebViewBack webViewBack;

    public BrowserPresenter() {
        App.get().getApplicationComponent().inject(this);
        webViewBack = new WebViewBack();
    }

    @Override
    public void onCreate(Activity activity) {
        clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public void onResume(BaseViewContract view) {
        this.view = view;
        if (domain != null) {
            domain.register();
        }
    }

    @Override
    public void onDestroy() {
        view = null;
        webViewHelper.onDestroy();
        domain.unregister();
    }

    @Override
    public void init(MainWebView webView, View actionMoreView, Action onHomeClickListener, Action clearTextListener) {
        webViewHelper = new WebViewHelper(webView);
        if (view != null) {
            webViewHelper.init(view.getAppCompatActivity());
            imageMenu = new ImageMenu.Builder()
                    .setActivity(view.getAppCompatActivity())
                    .setListener(this::onImageMenuClick)
                    .setCancelable(true)
                    .create();
            linkMenu = new LinkMenu.Builder()
                    .setActivity(view.getAppCompatActivity())
                    .setListener(this::onLinkMenuClick)
                    .setCancelable(true)
                    .create();
            List<OverflowMenuItem> items = Arrays.asList(
                    new OverflowMenuItem(R.id.actionBookmarks, view.getAppCompatActivity().getString(R.string.bookmarks), OverflowMenuItem.DEFAULT),
                    new OverflowMenuItem(R.id.actionDesktopSite, view.getAppCompatActivity().getString(R.string.desktop_site), OverflowMenuItem.CHECKBOX),
                    new OverflowMenuItem(R.id.actionGoogle, view.getAppCompatActivity().getString(R.string.google), OverflowMenuItem.DEFAULT));
            overflowMenu = new OverflowMenu.Builder()
                    .setActivity(view.getAppCompatActivity())
                    .setSourceView(actionMoreView)
                    .setListener(id -> onOverflowMenuClick(id, onHomeClickListener))
                    .setCancelable(true)
                    .setItems(items)
                    .create();
            webViewHelper.setFragmentManagerSupplier(this);
            webViewHelper.setDownloadListener((url, userAgent, contentDisposition, mimeType, contentLength) -> download(url));
        }
    }

    @Override
    public void setPageListeners(Consumer<String> onPageStarted, Consumer<String> onPageFinished) {
        webViewHelper.setPageListeners(onPageStarted, url -> {
            if (view != null) {
                ActivityUtils.hideKeyboard(view.getAppCompatActivity());
            }
            onPageFinished.accept(url);
        }, this::setPageVisited);
    }

    @Override
    public void setProgressListener(Consumer<Integer> progress) {
        webViewHelper.setProgressListener(progress);
    }

    @Override
    public void setIntentData(Bundle bundle) {
        if (bundle != null && bundle.containsKey(Constants.EXTRA_KEY_EXTERNAL_LINK)) {
            isExternalLink = true;
            loadUrl(bundle.getString(Constants.EXTRA_KEY_EXTERNAL_LINK));
        }
    }

    @Override
    public void webViewCanGoBack(Consumer<WebViewBack> callback, Action finishListener) {
        webViewHelper.setFinishListener(finishListener);
        webViewBack.webViewCanGoBack = webViewHelper.webViewCanGoBack();
        webViewBack.isExternalLink = isExternalLink;
        callback.accept(webViewBack);
    }

    @Override
    public void onRefresh(Action stopRefreshing) {
        if (TextUtils.isEmpty(webViewHelper.getUrlValidated())) {
            stopRefreshing.execute();
        } else {
            webViewHelper.reload();
        }
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
    public void onActionMoreClick(Action onMenuShown, Action onMenuDismiss) {
        String url = getUrlValidated();
        isUrlBookmarked(
                url,
                isBookmarked -> {
                    onMenuShown.execute();
                    overflowMenu
                            .setUrlValidated(url)
                            .setOnDismissListener(onMenuDismiss)
                            .setCanGoForward(webViewHelper.canGoForward())
                            .setIsBookmarked(isBookmarked)
                            .show();
                }
        );
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        WebView.HitTestResult hitTestResult = webViewHelper.getHitTestResult();
        itemUrl = hitTestResult.getExtra();
        switch (hitTestResult.getType()) {
            case WebView.HitTestResult.IMAGE_TYPE:
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                isDataImage(itemUrl, isDataImage -> {
                    if (!isDataImage) {
                        imageMenu.setTouchPosition(webViewHelper.getX(), webViewHelper.getY()).show();
                    }
                });
                break;
            case WebView.HitTestResult.SRC_ANCHOR_TYPE:
                linkMenu.setTouchPosition(webViewHelper.getX(), webViewHelper.getY()).show();
            default:
                break;
        }
    }

    @Override
    public FragmentManager supplySupportFragmentManager() {
        return view.getAppCompatActivity().getSupportFragmentManager();
    }

    @Override
    public void onKeyboardGoClick(String url) {
        if (view != null) {
            ActivityUtils.hideKeyboard(view.getAppCompatActivity());
        }
        loadUrl(url);
    }

    private String getUrlValidated() {
        return webViewHelper.getUrlValidated();
    }

    private void isDataImage(String url, Consumer<Boolean> result) {
        if (view == null) { return; }
        domain.isDataImage(url, isDataImage -> {
            if (view == null) { return; }
            result.accept(isDataImage);
        });
    }

    private void isUrlBookmarked(String url, Consumer<Boolean> result) {
        if (domain == null) { return; }
        domain.isUrlBookmarked(url, isBookmarked -> {
            if (view == null) { return; }
            result.accept(isBookmarked);
        });
    }

    private void onOverflowMenuClick(int id, Action onHomeClickListener) {
        switch (id) {
            case R.id.actionHome:
                onHomeClickListener.execute();
                break;
            case R.id.actionBookmark:
                manageBookmark(webViewHelper.getCurrentPage());
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
                onBookmarksButtonClick();
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
                download(itemUrl);
                break;
            case R.id.actionCopyUrl:
                copyToClipboard(URL, itemUrl);
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
                copyToClipboard(URL, itemUrl);
                break;
            default:
                break;
        }
    }

    private void copyToClipboard(String title, String text) {
        ClipData clip = ClipData.newPlainText(title, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

    private void download(String url) {
        if (view == null) { return; }
        DownloaderManager.download(view.getAppCompatActivity(), url, null);
    }

    private void setPageVisited(String url) {
        if (domain == null) { return; }
        getLastPageVisited(last -> {
            if (last == null || !last.equals(url)) {
                domain.setLastPageVisited(url);
            }
        });
    }

    private void getLastPageVisited(Consumer<String> result) {
        if (domain == null) { return; }
        domain.getLastPageVisited(lastVisited -> {
            if (view == null) { return; }
            result.accept(lastVisited);
        });
    }

    private void manageBookmark(Page page) {
        if (domain == null) { return; }
        domain.manageBookmark(new PageMapper().unMap(page));
    }

    private void onBookmarksButtonClick() {
        if (view == null) { return; }
        router.goToBookmarks(view.getAppCompatActivity());
    }

    private void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        webViewHelper.loadUrl(url);
    }
}
