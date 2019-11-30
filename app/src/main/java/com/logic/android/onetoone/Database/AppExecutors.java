package com.logic.android.onetoone.Database;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK=new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;


    public AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance(){
        if (sInstance==null){
            synchronized (LOCK){
                sInstance=new AppExecutors(Executors.newSingleThreadExecutor()
                        ,Executors.newFixedThreadPool(3)
                        ,new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor getDiskIO() { return diskIO;}
    public Executor getMainThread() { return mainThread ;}
    public Executor getNetworkIO() { return networkIO ;}

    public static class MainThreadExecutor implements Executor{
        Handler mainThreadHandler =new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
