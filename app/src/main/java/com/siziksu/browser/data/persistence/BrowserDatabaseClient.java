package com.siziksu.browser.data.persistence;

import com.siziksu.browser.data.persistence.model.Bookmark;
import com.siziksu.browser.data.persistence.model.History;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface BrowserDatabaseClient {

    Completable insertBookmark(Bookmark bookmark);

    Completable deleteBookmark(Bookmark bookmark);

    Single<List<Bookmark>> getBookmarks();

    Completable insertHistoryItem(History item);

    Completable deleteHistoryItem(History item);

    Single<List<History>> getHistory();
}
