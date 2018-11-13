package com.siziksu.browser.data;

import com.siziksu.browser.data.model.PageData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RepositoryContract {

    Single<String> getLastPageVisited();

    Completable manageBookmark(PageData bookmark);

    Single<List<PageData>> getBookmarks();

    Completable setPageVisited(String url);

    Completable deleteBookmark(PageData bookmark);
}
