package com.siziksu.browser.data;

import com.siziksu.browser.App;
import com.siziksu.browser.data.client.PreferencesClientContract;
import com.siziksu.browser.data.mapper.BrowserActivityMapper;
import com.siziksu.browser.data.mapper.PageMapper;
import com.siziksu.browser.data.model.BrowserActivityData;
import com.siziksu.browser.data.model.PageData;
import com.siziksu.browser.data.persistence.BrowserDatabaseClient;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class Repository implements RepositoryContract {

    @Inject
    PreferencesClientContract preferencesClient;
    @Inject
    BrowserDatabaseClient browserDatabaseClient;

    public Repository() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public Completable setLastPageVisited(String url) {
        return preferencesClient.setLastPageVisited(url);
    }

    @Override
    public Single<String> getLastPageVisited() {
        return preferencesClient.getLastPageVisited();
    }

    @Override
    public Completable clearLastPageVisited() {
        return preferencesClient.setLastPageVisited("");
    }

    @Override
    public Completable insertBookmark(PageData page) {
        return browserDatabaseClient.insertBookmark(new PageMapper().unMap(page));
    }

    @Override
    public Completable deleteBookmark(PageData page) {
        return browserDatabaseClient.deleteBookmark(new PageMapper().unMap(page));
    }

    @Override
    public Single<List<PageData>> getBookmarks() {
        return browserDatabaseClient.getBookmarks().map(bookmarks -> new PageMapper().map(bookmarks));
    }

    @Override
    public Completable insertHistoryItem(BrowserActivityData activity) {
        return browserDatabaseClient.insertHistoryItem(new BrowserActivityMapper().unMap(activity));
    }

    @Override
    public Completable deleteHistoryItem(BrowserActivityData activity) {
        return browserDatabaseClient.deleteHistoryItem(new BrowserActivityMapper().unMap(activity));
    }

    @Override
    public Single<List<BrowserActivityData>> getHistory() {
        return browserDatabaseClient.getHistory().map(history -> new BrowserActivityMapper().map(history));
    }
}
