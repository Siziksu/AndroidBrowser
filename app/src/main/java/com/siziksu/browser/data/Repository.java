package com.siziksu.browser.data;

import com.siziksu.browser.App;
import com.siziksu.browser.data.client.PreferencesClientContract;
import com.siziksu.browser.data.mapper.BookmarkMapper;
import com.siziksu.browser.data.model.BookmarkData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class Repository implements RepositoryContract {

    @Inject
    PreferencesClientContract preferencesClient;

    public Repository() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public Single<String> getLastVisited() {
        return preferencesClient.getLastVisited();
    }

    @Override
    public Completable manageBookmark(BookmarkData bookmark) {
        return preferencesClient.manageBookmark(new BookmarkMapper().unMap(bookmark));
    }

    @Override
    public Single<List<BookmarkData>> getBookmarks() {
        return preferencesClient.getBookmarks().map(bookmarks -> new BookmarkMapper().map(bookmarks));
    }

    @Override
    public Completable setUrlVisited(String url) {
        return preferencesClient.setUrlVisited(url);
    }

    @Override
    public Completable deleteBookmark(BookmarkData bookmark) {
        return preferencesClient.deleteBookmark(new BookmarkMapper().unMap(bookmark));
    }
}
