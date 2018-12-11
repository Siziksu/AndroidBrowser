package com.siziksu.browser.domain.main;

import com.siziksu.browser.App;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.Print;
import com.siziksu.browser.data.RepositoryContract;
import com.siziksu.browser.data.model.PageData;
import com.siziksu.browser.domain.manager.DisposablesManager;
import com.siziksu.browser.domain.mapper.BrowserActivityMapper;
import com.siziksu.browser.domain.mapper.PageMapper;
import com.siziksu.browser.domain.model.BrowserActivityDomain;
import com.siziksu.browser.domain.model.PageDomain;
import com.siziksu.browser.domain.utils.UrlUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class BrowserDomain implements BrowserDomainContract {

    @Inject
    RepositoryContract repository;
    @Inject
    UrlUtils urlUtils;
    @Inject
    DisposablesManager disposablesManager;

    public BrowserDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register() {
        disposablesManager.setSize(4);
    }

    @Override
    public void unregister() {
        disposablesManager.dispose();
    }

    @Override
    public void onPageVisited(String url) {
        if (repository == null) { return; }
        disposablesManager.add(0, repository.setLastPageVisited(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, throwable -> Print.error("Error setting the visited page: " + throwable.getMessage(), throwable))
        );
    }

    @Override
    public void onBookmarkButtonClicked(PageDomain page) {
        if (repository == null) { return; }
        disposablesManager.add(1, repository.insertBookmark(new PageMapper().unMap(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, throwable -> Print.error("Error managing a bookmark: " + throwable.getMessage(), throwable))
        );
    }

    @Override
    public void isUrlBookmarked(String url, Consumer<Boolean> result) {
        if (repository == null) { return; }
        disposablesManager.add(2, repository.getBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookmarks -> {
                    boolean isBookmarked = false;
                    if (url != null) {
                        for (PageData bookmark : bookmarks) {
                            if (bookmark.url.equals(url)) {
                                isBookmarked = true;
                                break;
                            }
                        }
                    }
                    result.accept(isBookmarked);
                }, throwable -> Print.error("Error checking if a url is bookmarked: " + throwable.getMessage(), throwable))
        );
    }

    @Override
    public void onBrowserActivity(BrowserActivityDomain activity) {
        if (repository == null) { return; }
        disposablesManager.add(3, repository.insertHistoryItem(new BrowserActivityMapper().unMap(activity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, throwable -> Print.error("Error managing the browser activity: " + throwable.getMessage(), throwable))
        );
    }

    @Override
    public void isDataImage(String url, Consumer<Boolean> result) {
        result.accept(urlUtils.isDataImage(url));
    }
}
