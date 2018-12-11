package com.siziksu.browser.data;

import com.siziksu.browser.data.model.BrowserActivityData;
import com.siziksu.browser.data.model.PageData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RepositoryContract {

    Completable setLastPageVisited(String url);

    Single<String> getLastPageVisited();

    Completable clearLastPageVisited();

    Completable insertBookmark(PageData page);

    Completable deleteBookmark(PageData page);

    Single<List<PageData>> getBookmarks();

    Completable insertHistoryItem(BrowserActivityData activity);

    Completable deleteHistoryItem(BrowserActivityData activity);

    Single<List<BrowserActivityData>> getHistory();
}
