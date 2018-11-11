package com.siziksu.browser.data.client;

import com.siziksu.browser.data.client.model.BookmarkClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface PreferencesClientContract {

    Single<String> getLastVisited();

    Completable manageBookmark(BookmarkClient bookmark);

    Single<List<BookmarkClient>> getBookmarks();

    Completable setUrlVisited(String url);

    Completable deleteBookmark(BookmarkClient bookmark);
}
