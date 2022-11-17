package ru.hse.miem.hsecourses.courses;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private CourseDao mCourseDao;
    private LiveData<List<Course>> mAllCourses;

    private ModuleDao mWeeksDao;
    private LiveData<List<Module>> mAllWeeks;

    private DayDao mDaysDao;
    private LiveData<List<Day>> mAllDays;

    private TopicDao mTopicDao;
    private LiveData<List<Topic>> mAllTopics;

    public Repository(Application application) {
        AppDataBase db = AppDataBase.getAppDb(application);
        mCourseDao = db.getCourseDao();
        mAllCourses = mCourseDao.getAllCourses();

        mWeeksDao = db.getWeeksDao();
        mAllWeeks = mWeeksDao.getAllWeeks();

        mDaysDao = db.getDaysDao();
        mAllDays = mDaysDao.getAllDays();

        mTopicDao = db.getTopicsDao();
        mAllTopics = mTopicDao.getAllTopicsList();
    }

    public LiveData<List<Topic>> getAllTopics() {
        return mAllTopics;
    }

    public LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    }

    public LiveData<List<Module>> getAllModules() {
        return mAllWeeks;
    }

    public LiveData<List<Day>> getAllDays() {
        return mAllDays;
    }

    public void insertTopics(List<Topic> topics) {
        new insertAsyncTaskTopic(mTopicDao).execute(topics);
    }

    public void insert (Course word) {
        new insertAsyncTaskCourse(mCourseDao).execute(word);
    }

    public void insertWeeks (List<Module> modules) {
        new insertAsyncTaskWeek(mWeeksDao).execute(modules);
    }

    public void insertDays (List<Day> days) {
        new insertAsyncTaskDay(mDaysDao).execute(days);
    }

    public void clear(){
        new clearAsyncTask(mCourseDao, mWeeksDao, mDaysDao, mTopicDao).execute();
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


    private static class insertAsyncTaskWeek extends AsyncTask<List<Module>, Void, Void> {

        private ModuleDao mAsyncTaskDao;

        @Deprecated
        insertAsyncTaskWeek(ModuleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Module>... params) {
            for(Module curr: params[0])
                mAsyncTaskDao.insertDataIntoWeeksList(curr);
            return null;
        }
    }

    private static class insertAsyncTaskTopic extends AsyncTask<List<Topic>, Void, Void> {

        private TopicDao mAsyncTaskDao;

        @Deprecated
        insertAsyncTaskTopic(TopicDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Topic>... params) {
            for(Topic curr: params[0])
                mAsyncTaskDao.insertDataIntoTopicList(curr);
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

        private ModuleDao moduleDao;

        private DayDao dayDao;

        private TopicDao topicDao;

        @Deprecated
        clearAsyncTask(CourseDao dao, ModuleDao dao2, DayDao dao3, TopicDao dao4) {
            courseDao = dao;
            moduleDao = dao2;
            dayDao = dao3;
            topicDao = dao4;
        }


        /**
         * @param voids
         * @deprecated
         */
        @Deprecated
        @Override
        protected Void doInBackground(Void... voids) {
            courseDao.clearTable();
            moduleDao.truncateTheList();
            dayDao.truncateTheList();
            topicDao.truncateTheList();
            return null;
        }
    }

}
