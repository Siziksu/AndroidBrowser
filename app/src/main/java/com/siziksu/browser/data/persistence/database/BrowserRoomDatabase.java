package com.siziksu.browser.data.persistence.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.siziksu.browser.dagger.module.RoomModule;
import com.siziksu.browser.data.persistence.model.Bookmark;
import com.siziksu.browser.data.persistence.model.BookmarkDao;
import com.siziksu.browser.data.persistence.model.History;
import com.siziksu.browser.data.persistence.model.HistoryDao;

@Database(entities = {Bookmark.class, History.class}, version = RoomModule.VERSION)
public abstract class BrowserRoomDatabase extends RoomDatabase {

    public abstract BookmarkDao getBookmarkDao();

    public abstract HistoryDao getHistoryDao();
}
