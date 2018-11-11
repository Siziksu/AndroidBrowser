package com.siziksu.browser.domain.main;

import android.util.Log;

import com.siziksu.browser.App;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.data.RepositoryContract;
import com.siziksu.browser.data.model.BookmarkData;
import com.siziksu.browser.domain.mapper.BookmarkMapper;
import com.siziksu.browser.domain.model.BookmarkDomain;

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
    public void setUrlVisited(String url) {
        if (repository == null) { return; }
        addDisposable(0, repository.setUrlVisited(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    @Override
    public void getLastVisited(Consumer<String> result) {
        if (repository == null) { return; }
        addDisposable(1, repository.getLastVisited()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result::accept,
                        throwable -> Log.e(Constants.TAG, throwable.getMessage(), throwable)
                )
        );
    }

    @Override
    public void manageBookmark(BookmarkDomain bookmark) {
        if (repository == null) { return; }
        addDisposable(2, repository.manageBookmark(new BookmarkMapper().unMap(bookmark))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    @Override
    public void checkIfItIsBookmarked(String url, Consumer<Boolean> result) {
        if (repository == null) { return; }
        addDisposable(3, repository.getBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookmarks -> {
                    boolean isBookmarked = false;
                    for (BookmarkData bookmark : bookmarks) {
                        if (bookmark.url.equals(url)) {
                            isBookmarked = true;
                            break;
                        }
                    }
                    result.accept(isBookmarked);
                })
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
