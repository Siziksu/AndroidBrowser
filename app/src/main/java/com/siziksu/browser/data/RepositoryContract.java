package com.siziksu.browser.data;

import com.siziksu.browser.data.model.PageData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RepositoryContract {

    Completable setLastPageVisited(String url);

    Single<String> getLastPageVisited();

    Completable clearLastPageVisited();

    Completable manageBookmark(PageData bookmark);

    Completable deleteBookmark(PageData bookmark);

    Single<List<PageData>> getBookmarks();
}
