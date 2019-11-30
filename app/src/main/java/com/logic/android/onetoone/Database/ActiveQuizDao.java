package com.logic.android.onetoone.Database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ActiveQuizDao {


    @Query("Select * From quiz1")
    LiveData<List<ActiveQuizEntity>> getAllQuiz();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ActiveQuizEntity... entities);

    @Delete
    void delete(ActiveQuizEntity... entities);

    @Query("Delete from quiz1")
    public void deletetable();
}
