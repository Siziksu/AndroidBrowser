package com.siziksu.browser.domain.bookmarks;

import android.util.Log;

import com.siziksu.browser.App;
import com.siziksu.browser.common.Constants;
import com.siziksu.browser.common.function.Action;
import com.siziksu.browser.common.function.Consumer;
import com.siziksu.browser.data.RepositoryContract;
import com.siziksu.browser.domain.mapper.BookmarkMapper;
import com.siziksu.browser.domain.model.BookmarkDomain;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class BookmarksDomain implements BookmarksDomainContract {

    @Inject
    RepositoryContract repository;

    private Disposable[] disposables;

    public BookmarksDomain() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public void register() {
        disposables = new Disposable[2];
    }

    @Override
    public void unregister() {
        dispose();
    }

    @Override
    public void getBookmarks(Consumer<List<BookmarkDomain>> result) {
        if (repository == null) { return; }
        addDisposable(0, repository.getBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookmarks -> result.accept(new BookmarkMapper().map(bookmarks)))
        );
    }

    @Override
    public void deleteBookmark(BookmarkDomain bookmark, Action action) {
        if (repository == null) { return; }
        addDisposable(1, repository.deleteBookmark(new BookmarkMapper().unMap(bookmark))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action::execute)
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
