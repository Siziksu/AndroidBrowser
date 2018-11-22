package com.siziksu.browser.presenter.main;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.siziksu.browser.App;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.main.BrowserDomainContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.ui.common.manager.DownloaderManager;
import com.siziksu.browser.ui.common.mapper.PageMapper;
import com.siziksu.browser.ui.common.model.Page;
import com.siziksu.browser.ui.common.router.RouterContract;

import javax.inject.Inject;

public final class BrowserPresenter implements BrowserPresenterContract<BaseViewContract> {

    @Inject
    RouterContract router;
    @Inject
    BrowserDomainContract domain;

    private BaseViewContract view;
    private ClipboardManager clipboard;

    public BrowserPresenter() {
        App.get().getApplicationComponent().inject(this);
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
        domain.unregister();
    }

    @Override
    public void setPageVisited(String url) {
        if (domain == null) { return; }
        getLastPageVisited(last -> {
            if (last == null || !last.equals(url)) {
                domain.setPageVisited(url);
            }
        });
    }

    @Override
    public void getLastPageVisited(Consumer<String> result) {
        if (domain == null) { return; }
        domain.getLastPageVisited(lastVisited -> {
            if (view == null) { return; }
            result.accept(lastVisited);
        });
    }

    @Override
    public void clearLastPageVisited() {
        if (domain != null) {
            domain.clearLastPageVisited();
        }
    }

    @Override
    public void download(String url) {
        if (view == null) { return; }
        DownloaderManager.download(view.getAppCompatActivity(), url, null);
    }

    @Override
    public void manageBookmark(Page page) {
        if (domain == null) { return; }
        domain.manageBookmark(new PageMapper().unMap(page));
    }

    @Override
    public void isUrlBookmarked(String url, Consumer<Boolean> result) {
        if (domain == null) { return; }
        domain.isUrlBookmarked(url, isBookmarked -> {
            if (view == null) { return; }
            result.accept(isBookmarked);
        });
    }

    @Override
    public void copyToClipboard(String title, String text) {
        ClipData clip = ClipData.newPlainText(title, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

    @Override
    public void onBookmarksButtonClick() {
        if (view == null) { return; }
        router.goToBookmarks(view.getAppCompatActivity());
    }
}
