package com.siziksu.browser.data;

import com.siziksu.browser.data.model.BookmarkData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface RepositoryContract {

    Single<String> getLastVisited();

    Completable manageBookmark(BookmarkData bookmark);

    Single<List<BookmarkData>> getBookmarks();

    Completable setUrlVisited(String url);

    Completable deleteBookmark(BookmarkData bookmark);
}
