package com.siziksu.browser.data;

import com.siziksu.browser.App;
import com.siziksu.browser.data.client.PreferencesClientContract;
import com.siziksu.browser.data.mapper.PageMapper;
import com.siziksu.browser.data.model.PageData;

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
    public Completable setPageVisited(String url) {
        return preferencesClient.setPageVisited(url);
    }

    @Override
    public Single<String> getLastPageVisited() {
        return preferencesClient.getLastVisited();
    }

    @Override
    public Completable manageBookmark(PageData bookmark) {
        return preferencesClient.manageBookmark(new PageMapper().unMap(bookmark));
    }

    @Override
    public Completable deleteBookmark(PageData bookmark) {
        return preferencesClient.deleteBookmark(new PageMapper().unMap(bookmark));
    }

    @Override
    public Single<List<PageData>> getBookmarks() {
        return preferencesClient.getBookmarks().map(bookmarks -> new PageMapper().map(bookmarks));
    }
}
