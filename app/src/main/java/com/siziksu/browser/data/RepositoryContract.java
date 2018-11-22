package com.siziksu.browser.data;

import com.siziksu.browser.data.model.PageData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RepositoryContract {

    Completable setPageVisited(String url);

    Single<String> getLastPageVisited();

    void clearLastPageVisited();

    Completable manageBookmark(PageData bookmark);

    Completable deleteBookmark(PageData bookmark);

    Single<List<PageData>> getBookmarks();
}
