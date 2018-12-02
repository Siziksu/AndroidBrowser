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

    private static final String BOOKMARKS_KEY = "bookmarks";
    private static final String LAST_PAGE_VISITED_KEY = "last_page_visited";

    @Inject
    PreferencesService service;

    public PreferencesClient() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public Single<String> getLastPageVisited() {
        return Single.create(emitter -> {
            try {
                String string = service.useDefaultSharedPreferences().getValue(LAST_PAGE_VISITED_KEY, "");
                emitter.onSuccess(string);
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }

    @Override
    public Completable setLastPageVisited(String url) {
        return Completable.create(emitter -> {
            service.useDefaultSharedPreferences().applyValue(LAST_PAGE_VISITED_KEY, url);
            emitter.onComplete();
        });
    }

    @Override
    public Completable manageBookmark(PageClient bookmark) {
        return Completable.create(emitter -> {
            List<PageClient> bookmarks = getBookmarkList();
            if (!bookmarks.contains(bookmark)) {
                bookmark.titleToShow = bookmark.title;
                bookmarks.add(bookmark);
            } else {
                bookmarks.remove(bookmark);
            }
            String json = new Gson().toJson(bookmarks);
            service.useDefaultSharedPreferences().applyValue(BOOKMARKS_KEY, json);
            emitter.onComplete();
        });
    }

    @Override
    public Single<List<PageClient>> getBookmarks() {
        return Single.create(emitter -> {
            try {
                List<PageClient> bookmarks = getBookmarkList();
                emitter.onSuccess(bookmarks);
            } catch (Exception exception) {
                emitter.onError(exception);
            }
        });
    }

    @Override
    public Completable deleteBookmark(PageClient bookmark) {
        return Completable.create(emitter -> {
            List<PageClient> bookmarks = getBookmarkList();
            bookmarks.remove(bookmark);
            String json = new Gson().toJson(bookmarks);
            service.useDefaultSharedPreferences().applyValue(BOOKMARKS_KEY, json);
            emitter.onComplete();
        });
    }

    private List<PageClient> getBookmarkList() {
        String json = service.useDefaultSharedPreferences().getValue(BOOKMARKS_KEY, "[]");
        return new Gson().fromJson(json, new TypeToken<List<PageClient>>() {}.getType());
    }
}
