package com.siziksu.browser.data.persistence.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("select * from History")
    List<History> getHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistoryItem(History item);

    @Delete
    void deleteHistoryItem(History item);

    @Query("delete from History where url not in (select url from History order by date desc limit 120)")
    void deleteOldItems();
}
