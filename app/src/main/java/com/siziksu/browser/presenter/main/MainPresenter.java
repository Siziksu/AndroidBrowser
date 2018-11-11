package com.siziksu.browser.presenter.main;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.siziksu.browser.App;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.domain.main.MainDomainContract;
import com.siziksu.browser.presenter.BaseViewContract;
import com.siziksu.browser.presenter.mapper.BookmarkMapper;
import com.siziksu.browser.presenter.model.Bookmark;
import com.siziksu.browser.ui.manager.DownloaderManager;
import com.siziksu.browser.ui.manager.PermissionsManager;
import com.siziksu.browser.ui.router.RouterContract;

import javax.inject.Inject;

public final class MainPresenter implements MainPresenterContract<BaseViewContract> {

    @Inject
    RouterContract router;
    @Inject
    MainDomainContract domain;

    private BaseViewContract view;
    private ClipboardManager clipboard;

    public MainPresenter() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void onCreate(Activity activity) {
        if (activity == null) { return; }
        PermissionsManager.checkPermissions(activity);
    }

    @Override
    public void onResume(BaseViewContract view) {
        this.view = view;
        if (view != null) {
            clipboard = (ClipboardManager) view.getAppCompatActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        }
        domain.register();
    }

    @Override
    public void onDestroy() {
        view = null;
        domain.unregister();
    }

    @Override
    public void setUrlVisited(String url) {
        if (domain == null) { return; }
        domain.setUrlVisited(url);
    }

    @Override
    public void getLastVisited(Consumer<String> result) {
        if (domain == null) { return; }
        domain.getLastVisited(lastVisited -> {
            if (view == null) { return; }
            result.accept(lastVisited);
        });
    }

    @Override
    public void download(String url) {
        if (view == null) { return; }
        DownloaderManager.download(view.getAppCompatActivity(), url, null);
    }

    @Override
    public void manageBookmark(Bookmark bookmark) {
        if (domain == null) { return; }
        domain.manageBookmark(new BookmarkMapper().unMap(bookmark));
    }

    @Override
    public void checkIfItIsBookmarked(String url, Consumer<Boolean> result) {
        if (domain == null) { return; }
        domain.checkIfItIsBookmarked(url, isBookmarked -> {
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
    public void onBookmarksClick() {
        if (view == null) { return; }
        router.goToBookmarks(view.getAppCompatActivity());
    }
}
