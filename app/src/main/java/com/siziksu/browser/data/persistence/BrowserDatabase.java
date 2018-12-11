package com.siziksu.browser.data.persistence;

import com.siziksu.browser.App;
import com.siziksu.browser.data.persistence.model.Bookmark;
import com.siziksu.browser.data.persistence.model.BookmarkDao;
import com.siziksu.browser.data.persistence.model.History;
import com.siziksu.browser.data.persistence.model.HistoryDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class BrowserDatabase implements BrowserDatabaseClient {

    @Inject
    BookmarkDao bookmarkDao;
    @Inject
    HistoryDao historyDao;

    public BrowserDatabase() {
        App.get().getApplicationComponent().inject(this);
    }

    @Override
    public Completable insertBookmark(Bookmark bookmark) {
        return Completable.create(emitter -> {
            List<Bookmark> bookmarks = bookmarkDao.getBookmarks();
            if (!bookmarks.contains(bookmark)) {
                bookmark.titleToShow = bookmark.title;
                bookmarkDao.insertBookmark(bookmark);
            } else {
                bookmarkDao.deleteBookmark(bookmark);
            }
            emitter.onComplete();
        });
    }

    @Override
    public Completable deleteBookmark(Bookmark bookmark) {
        return Completable.create(emitter -> {
            bookmarkDao.deleteBookmark(bookmark);
            emitter.onComplete();
        });
    }

    @Override
    public Single<List<Bookmark>> getBookmarks() {
        return Single.create(emitter -> {
            List<Bookmark> bookmarks = bookmarkDao.getBookmarks();
            if (bookmarks != null) {
                emitter.onSuccess(bookmarks);
            }
            if (!emitter.isDisposed()) {
                emitter.onError(new Exception("No elements found"));
            }
        });
    }

    @Override
    public Completable insertHistoryItem(History item) {
        return Completable.create(emitter -> {
            historyDao.deleteOldItems();
            historyDao.insertHistoryItem(item);
            emitter.onComplete();
        });
    }

    @Override
    public Completable deleteHistoryItem(History item) {
        return Completable.create(emitter -> {
            historyDao.deleteHistoryItem(item);
            emitter.onComplete();
        });
    }

    @Override
    public Single<List<History>> getHistory() {
        return Single.create(emitter -> {
            List<History> history = historyDao.getHistory();
            if (history != null) {
                emitter.onSuccess(history);
            }
            if (!emitter.isDisposed()) {
                emitter.onError(new Exception("No elements found"));
            }
        });
    }
}
