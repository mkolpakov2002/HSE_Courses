package ru.hse.miem.hsecourses.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Module;
import ru.hse.miem.hsecourses.courses.Repository;

public class HomeViewModel extends AndroidViewModel  {

    private Repository mRepository;

    private LiveData<List<Module>> mAllModules;

    public HomeViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);

        mAllModules = mRepository.getAllModules();
    }


    public LiveData<List<Module>> getAllModules() { return mAllModules; }

    public void insert(Course courses) { mRepository.insert(courses); }

    public void insertModules(List<Module> modules) { mRepository.insertModules(modules); }

    public void clear() { mRepository.clear(); }

}
