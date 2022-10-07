package ru.hse.miem.hsecourses.courses;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private CourseDao mCourseDao;
    private LiveData<List<Course>> mAllCourses;

    Repository(Application application) {
        AppDataBase db = AppDataBase.getAppDb(application);
        mCourseDao = db.getCourseDao();
        mAllCourses = mCourseDao.getAllCourses();
    }

    LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }

    public void insert (Course word) {
        new insertAsyncTask(mCourseDao).execute(word);
    }

    public void clear(){
        new clearAsyncTask(mCourseDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao mAsyncTaskDao;

        insertAsyncTask(CourseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }

    private static class clearAsyncTask extends AsyncTask<Void, Void, Void> {

        private CourseDao mAsyncTaskDao;

        clearAsyncTask(CourseDao dao) {
            mAsyncTaskDao = dao;
        }


        /**
         * @param voids
         * @deprecated
         */
        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.clearTable();
            return null;
        }
    }

}
