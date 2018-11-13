package com.siziksu.browser.data.client;

import com.siziksu.browser.data.client.model.PageClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface PreferencesClientContract {

    Single<String> getLastVisited();

    Completable manageBookmark(PageClient bookmark);

    Single<List<PageClient>> getBookmarks();

    Completable setPageVisited(String url);

    Completable deleteBookmark(PageClient bookmark);
}
