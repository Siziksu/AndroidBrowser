package com.siziksu.browser.data.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siziksu.browser.App;
import com.siziksu.browser.data.client.model.PageClient;
import com.siziksu.browser.data.client.service.PreferencesService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class PreferencesClient implements PreferencesClientContract {

    private static final String BOOKMARKS = "bookmarks";
    private static final String LAST_PAGE_VISITED = "last_page_visited";

    @Inject
    PreferencesService service;

    public PreferencesClient() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public Single<String> getLastVisited() {
        return Single.create(e -> {
            try {
                String string = service.useDefaultSharedPreferences().getValue(LAST_PAGE_VISITED, "");
                e.onSuccess(string);
            } catch (Exception exception) {
                e.onError(exception);
            }
        });
    }

    @Override
    public Completable manageBookmark(PageClient bookmark) {
        return Completable.create(e -> {
            List<PageClient> bookmarks = getBookmarkList();
            if (!bookmarks.contains(bookmark)) {
                bookmark.titleToShow = bookmark.title;
                bookmarks.add(bookmark);
            } else {
                bookmarks.remove(bookmark);
            }
            String json = new Gson().toJson(bookmarks);
            service.useDefaultSharedPreferences().applyValue(BOOKMARKS, json);
            e.onComplete();
        });
    }

    @Override
    public Single<List<PageClient>> getBookmarks() {
        return Single.create(e -> {
            try {
                List<PageClient> bookmarks = getBookmarkList();
                e.onSuccess(bookmarks);
            } catch (Exception exception) {
                e.onError(exception);
            }
        });
    }

    @Override
    public Completable setPageVisited(String url) {
        return Completable.create(e -> {
            service.useDefaultSharedPreferences().applyValue(LAST_PAGE_VISITED, url);
            e.onComplete();
        });
    }

    @Override
    public Completable deleteBookmark(PageClient bookmark) {
        return Completable.create(e -> {
            List<PageClient> bookmarks = getBookmarkList();
            bookmarks.remove(bookmark);
            String json = new Gson().toJson(bookmarks);
            service.useDefaultSharedPreferences().applyValue(BOOKMARKS, json);
            e.onComplete();
        });
    }

    private List<PageClient> getBookmarkList() {
        String json = service.useDefaultSharedPreferences().getValue(BOOKMARKS, "[]");
        return new Gson().fromJson(json, new TypeToken<List<PageClient>>() {}.getType());
    }
}
