package com.siziksu.browser.data.client;

import com.siziksu.browser.data.client.model.PageClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface PreferencesClientContract {

    Single<String> getLastPageVisited();

    Completable setPageVisited(String url);

    void clearLastPageVisited();

    Completable manageBookmark(PageClient bookmark);

    Single<List<PageClient>> getBookmarks();

    Completable deleteBookmark(PageClient bookmark);
}
