package com.logic.android.onetoone.Database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ActiveQuizEntity.class},version =1,exportSchema = false)
@TypeConverters(DataConvertor.class)
public abstract  class  ActiveQuizDatabase extends RoomDatabase {

    private static ActiveQuizDatabase Instance;
    public abstract ActiveQuizDao activeQuizDao();

    public static ActiveQuizDatabase getInstance(Context context){
        if (Instance==null){
            synchronized (ActiveQuizDatabase.class){
                if (Instance==null){
                    Instance= Room.databaseBuilder(context.getApplicationContext(),
                            ActiveQuizDatabase.class,"active-quiz").allowMainThreadQueries().build();
                }
            }
        }
        return Instance;
    }

    public static void destroyInstance(){
        Instance=null;
    }
}
