package ru.hse.miem.hsecourses.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Module;
import ru.hse.miem.hsecourses.courses.Repository;
import ru.hse.miem.hsecourses.courses.Task;
import ru.hse.miem.hsecourses.courses.Topic;

public class MainViewModel extends AndroidViewModel {

    private Repository mRepository;

    private LiveData<List<Course>> mAllCourses;

    private LiveData<List<Module>> mAllModules;

    private LiveData<List<Day>> mAllDays;

    private LiveData<List<Topic>> mAllTopics;

    private LiveData<List<Task>> mAllTasks;

    public MainViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);

        mAllCourses = mRepository.getAllCourses();

        mAllModules = mRepository.getAllModules();

        mAllDays = mRepository.getAllDays();

        mAllTopics = mRepository.getAllTopics();

        mAllTasks = mRepository.getAllTasks();
    }

    public LiveData<List<Course>> getAllCourses() { return mAllCourses; }

    public LiveData<List<Module>> getAllModules() { return mAllModules; }

    public LiveData<List<Day>> getAllDays() { return mAllDays; }

    public LiveData<List<Topic>> getAllTopics() { return mAllTopics; }

    public LiveData<List<Task>> getAllTasks() { return mAllTasks; }

    public void insert(Course courses) { mRepository.insert(courses); }

    public void insertModules(List<Module> modules) { mRepository.insertModules(modules); }

    public void insertDays(List<Day> days) { mRepository.insertDays(days); }

    public void insertTasks(List<Task> tasks) { mRepository.insertTasks(tasks); }

    public void insertTopics(List<Topic> topics) { mRepository.insertTopics(topics); }

    public void clear() { mRepository.clear(); }
}