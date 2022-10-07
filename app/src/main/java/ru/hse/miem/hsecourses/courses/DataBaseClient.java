package ru.hse.miem.hsecourses.courses;

import android.content.Context;

import androidx.room.Room;

public class DataBaseClient {
    private Context mCtx;
    private static DataBaseClient mInstance;

    //our app database object
    private AppDataBase appDatabase;

    private DataBaseClient(Context mCtx) {
        this.mCtx = mCtx;
        appDatabase = Room.databaseBuilder(mCtx, AppDataBase.class, "hse_courses.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    public static synchronized DataBaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DataBaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDataBase getAppDatabase() {
        return appDatabase;
    }
}
