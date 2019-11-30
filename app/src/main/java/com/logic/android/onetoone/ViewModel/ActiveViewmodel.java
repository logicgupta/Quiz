package com.logic.android.onetoone.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.logic.android.onetoone.Database.ActiveQuizDao;
import com.logic.android.onetoone.Database.ActiveQuizDatabase;
import com.logic.android.onetoone.Database.ActiveQuizEntity;

import java.util.List;

public class ActiveViewmodel extends AndroidViewModel {

    private String TAG=this.getClass().getSimpleName();
    private ActiveQuizDao quizDao;
    private ActiveQuizDatabase quizDatabase;
    LiveData<List<ActiveQuizEntity>> livedata;

    public ActiveViewmodel( Application application) {
        super(application);
        quizDatabase=ActiveQuizDatabase.getInstance(application);
        quizDao=quizDatabase.activeQuizDao();
        livedata=quizDao.getAllQuiz();
    }

    public  void insert(ActiveQuizEntity quizEntity){
        new InsertAsynctask(quizDao).execute(quizEntity);
    }

    public  LiveData<List<ActiveQuizEntity>> getAllQuizes(){
        return livedata;
    }

    public void deleteTables(){
        quizDao.deletetable();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.e(TAG,"ViewModel Destroyed");
    }
    private class  InsertAsynctask extends AsyncTask<ActiveQuizEntity,Void,Void> {
        ActiveQuizDao mquizDao;
        public InsertAsynctask(ActiveQuizDao mquizDao) {
            this.mquizDao=mquizDao;
        }

        @Override
        protected Void doInBackground(ActiveQuizEntity... entities) {
            mquizDao.insert(entities[0]);
            return null;
        }
    }
}
