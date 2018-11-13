package com.siziksu.browser.domain.main;

import com.siziksu.browser.App;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.common.utils.Print;
import com.siziksu.browser.data.RepositoryContract;
import com.siziksu.browser.data.model.PageData;
import com.siziksu.browser.domain.mapper.PageMapper;
import com.siziksu.browser.domain.model.PageDomain;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class MainDomain implements MainDomainContract {

    @Inject
    RepositoryContract repository;

    private Disposable[] disposables;

    public MainDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register() {
        disposables = new Disposable[4];
    }

    @Override
    public void unregister() {
        dispose();
    }

    @Override
    public void setPageVisited(String url) {
        if (repository == null) { return; }
        addDisposable(0, repository.setPageVisited(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, throwable -> Print.error("Error setting a visited page: " + throwable.getMessage(), throwable))
        );
    }

    @Override
    public void getLastPageVisited(Consumer<String> result) {
        if (repository == null) { return; }
        addDisposable(1, repository.getLastPageVisited()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result::accept,
                        throwable -> Print.error("Error getting last visited page: " + throwable.getMessage(), throwable)
                )
        );
    }

    @Override
    public void manageBookmark(PageDomain bookmark) {
        if (repository == null) { return; }
        addDisposable(2, repository.manageBookmark(new PageMapper().unMap(bookmark))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, throwable -> Print.error("Error managing a bookmark: " + throwable.getMessage(), throwable))
        );
    }

    @Override
    public void isUrlBookmarked(String url, Consumer<Boolean> result) {
        if (repository == null) { return; }
        addDisposable(3, repository.getBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookmarks -> {
                    boolean isBookmarked = false;
                    for (PageData bookmark : bookmarks) {
                        if (bookmark.url.equals(url)) {
                            isBookmarked = true;
                            break;
                        }
                    }
                    result.accept(isBookmarked);
                }, throwable -> Print.error("Error checking if a url is bookmarked: " + throwable.getMessage(), throwable))
        );
    }


    private void addDisposable(int index, Disposable disposable) {
        dispose(index);
        if (disposables != null && disposables[index] != null) {
            disposables[index] = disposable;
        }
    }

    private void dispose() {
        if (disposables != null) {
            for (Disposable disposable : disposables) {
                if (disposable != null) {
                    disposable.dispose();
                }
            }
            disposables = null;
        }
    }

    private void dispose(int index) {
        if (disposables != null && disposables[index] != null) {
            disposables[index].dispose();
            disposables[index] = null;
        }
    }
}
