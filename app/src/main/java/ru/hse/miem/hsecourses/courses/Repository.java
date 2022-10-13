package ru.hse.miem.hsecourses.courses;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private CourseDao mCourseDao;
    private LiveData<List<Course>> mAllCourses;

    private WeekDao mWeeksDao;
    private LiveData<List<Week>> mAllWeeks;

    private DayDao mDaysDao;
    private LiveData<List<Day>> mAllDays;

    public Repository(Application application) {
        AppDataBase db = AppDataBase.getAppDb(application);
        mCourseDao = db.getCourseDao();
        mAllCourses = mCourseDao.getAllCourses();

        mWeeksDao = db.getWeeksDao();
        mAllWeeks = mWeeksDao.getAllWeeks();

        mDaysDao = db.getDaysDao();
        mAllDays = mDaysDao.getAllDays();
    }

    public LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }

    public LiveData<List<Week>> getAllWeeks() {
        return mAllWeeks;
    }

    public LiveData<List<Day>> getAllDays() {
        return mAllDays;
    }

    public void insert (Course word) {
        new insertAsyncTaskCourse(mCourseDao).execute(word);
    }

    public void insertWeeks (List<Week> weeks) {
        new insertAsyncTaskWeek(mWeeksDao).execute(weeks);
    }

    public void insertDays (List<Day> days) {
        new insertAsyncTaskDay(mDaysDao).execute(days);
    }

    public void clear(){
        new clearAsyncTask(mCourseDao, mWeeksDao, mDaysDao).execute();
    }

    private static class insertAsyncTaskCourse extends AsyncTask<Course, Void, Void> {

        private CourseDao mAsyncTaskDao;

        @Deprecated
        insertAsyncTaskCourse(CourseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }


    private static class insertAsyncTaskWeek extends AsyncTask<List<Week>, Void, Void> {

        private WeekDao mAsyncTaskDao;

        @Deprecated
        insertAsyncTaskWeek(WeekDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Week>... params) {
            for(Week curr: params[0])
                mAsyncTaskDao.insertDataIntoWeeksList(curr);
            return null;
        }
    }

    private static class insertAsyncTaskDay extends AsyncTask<List<Day>, Void, Void> {

        private DayDao mAsyncTaskDao;

        @Deprecated
        insertAsyncTaskDay(DayDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Day>... params) {
            for(Day curr: params[0])
                mAsyncTaskDao.insertDataIntoDaysList(curr);
            return null;
        }
    }

    private static class clearAsyncTask extends AsyncTask<Void, Void, Void> {

        private CourseDao courseDao;

        private WeekDao weekDao;

        private DayDao dayDao;

        @Deprecated
        clearAsyncTask(CourseDao dao, WeekDao dao2, DayDao dao3) {
            courseDao = dao;
            weekDao = dao2;
            dayDao = dao3;
        }


        /**
         * @param voids
         * @deprecated
         */
        @Deprecated
        @Override
        protected Void doInBackground(Void... voids) {
            courseDao.clearTable();
            weekDao.truncateTheList();
            dayDao.truncateTheList();
            return null;
        }
    }

}
