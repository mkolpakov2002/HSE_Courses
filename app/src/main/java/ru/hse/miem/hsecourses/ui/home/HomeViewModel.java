package ru.hse.miem.hsecourses.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Module;
import ru.hse.miem.hsecourses.courses.Repository;
import ru.hse.miem.hsecourses.courses.Topic;

public class HomeViewModel extends AndroidViewModel {

    private Repository mRepository;

    private LiveData<List<Course>> mAllCourses;

    private LiveData<List<Module>> mAllWeeks;

    private LiveData<List<Day>> mAllDays;

    private LiveData<List<Topic>> mAllTopics;

    public HomeViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);

        mAllCourses = mRepository.getAllCourses();

        mAllWeeks = mRepository.getAllModules();

        mAllDays = mRepository.getAllDays();

        mAllTopics = mRepository.getAllTopics();
    }

    public LiveData<List<Course>> getAllCourses() { return mAllCourses; }

    public LiveData<List<Module>> getAllModules() { return mAllWeeks; }

    public LiveData<List<Day>> getAllDays() { return mAllDays; }

    public LiveData<List<Topic>> getAllTopics() { return mAllTopics; }

    public void insert(Course courses) { mRepository.insert(courses); }

    public void insertWeeks(List<Module> modules) { mRepository.insertWeeks(modules); }

    public void insertDays(List<Day> days) { mRepository.insertDays(days); }

    public void clear() { mRepository.clear(); }
}