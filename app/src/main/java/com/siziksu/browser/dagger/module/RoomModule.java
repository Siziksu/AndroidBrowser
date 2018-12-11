package com.siziksu.browser.dagger.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.siziksu.browser.data.persistence.BrowserDatabase;
import com.siziksu.browser.data.persistence.BrowserDatabaseClient;
import com.siziksu.browser.data.persistence.database.BrowserRoomDatabase;
import com.siziksu.browser.data.persistence.model.BookmarkDao;
import com.siziksu.browser.data.persistence.model.HistoryDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class RoomModule {

    public static final int VERSION = 1;
    public static final String CURRENT_DATABASE_NAME = "browser_data.db";

    @Singleton
    @Provides
    BrowserRoomDatabase providesRoomDatabase(Context context) {
        return Room.databaseBuilder(context, BrowserRoomDatabase.class, CURRENT_DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    BookmarkDao providesBookmarkDao(BrowserRoomDatabase browserRoomDatabase) {
        return browserRoomDatabase.getBookmarkDao();
    }

    @Singleton
    @Provides
    HistoryDao providesHistoryDao(BrowserRoomDatabase browserRoomDatabase) {
        return browserRoomDatabase.getHistoryDao();
    }

    @Singleton
    @Provides
    BrowserDatabaseClient providesBrowserDatabaseClient() {
        return new BrowserDatabase();
    }
}
