package ru.hse.miem.hsecourses.courses;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private Repository mRepository;

    private LiveData<List<Course>> mAllCourses;

    private LiveData<List<Module>> mAllModules;

    private LiveData<List<Day>> mAllDays;

    private LiveData<List<Topic>> mAllTopics;

    public CourseViewModel (Application application) {
        super(application);
        mRepository = new Repository(application);

        mAllCourses = mRepository.getAllCourses();

        mAllModules = mRepository.getAllModules();

        mAllDays = mRepository.getAllDays();

        mAllTopics = mRepository.getAllTopics();
    }

    public LiveData<List<Course>> getAllCourses() { return mAllCourses; }

    public LiveData<List<Module>> getAllModules() { return mAllModules; }

    public LiveData<List<Day>> getAllDays() { return mAllDays; }

    public LiveData<List<Topic>> getAllTopics() {
        return mAllTopics;
    }

    public void insert(Course courses) { mRepository.insert(courses); }

    public void insertModules(List<Module> modules) { mRepository.insertWeeks(modules); }

    public void insertDays(List<Day> days) { mRepository.insertDays(days); }

    public void insertTopics(List<Topic> days) { mRepository.insertTopics(days); }

    public void clear() { mRepository.clear(); }

    public void clearModules() { mRepository.clear(); }

    public void clearTopics() { mRepository.clear(); }
}
