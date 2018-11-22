package com.siziksu.browser.domain.main;

import com.siziksu.browser.App;
import com.siziksu.browser.common.DisposablesManager;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.Print;
import com.siziksu.browser.data.RepositoryContract;
import com.siziksu.browser.data.model.PageData;
import com.siziksu.browser.domain.mapper.PageMapper;
import com.siziksu.browser.domain.model.PageDomain;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class BrowserDomain implements BrowserDomainContract {

    @Inject
    RepositoryContract repository;

    private DisposablesManager disposablesManager;

    public BrowserDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register() {
        disposablesManager = new DisposablesManager(7);
    }

    @Override
    public void unregister() {
        disposablesManager.dispose();
    }

    @Override
    public void setPageVisited(String url) {
        if (repository == null) { return; }
        disposablesManager.add(0, repository.setPageVisited(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, throwable -> Print.error("Error setting a visited page: " + throwable.getMessage(), throwable))
        );
    }

    @Override
    public void getLastPageVisited(Consumer<String> result) {
        if (repository == null) { return; }
        disposablesManager.add(1, repository.getLastPageVisited()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result::accept,
                        throwable -> Print.error("Error getting the last visited page: " + throwable.getMessage(), throwable)
                )
        );
    }

    @Override
    public void clearLastPageVisited() {
        if (repository == null) { return; }
        repository.clearLastPageVisited();
    }

    @Override
    public void manageBookmark(PageDomain bookmark) {
        if (repository == null) { return; }
        disposablesManager.add(2, repository.manageBookmark(new PageMapper().unMap(bookmark))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, throwable -> Print.error("Error managing a bookmark: " + throwable.getMessage(), throwable))
        );
    }

    @Override
    public void isUrlBookmarked(String url, Consumer<Boolean> result) {
        if (repository == null) { return; }
        disposablesManager.add(3, repository.getBookmarks()
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

}
