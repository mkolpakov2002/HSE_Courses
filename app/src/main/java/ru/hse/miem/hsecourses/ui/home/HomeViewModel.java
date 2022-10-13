package ru.hse.miem.hsecourses.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Repository;
import ru.hse.miem.hsecourses.courses.Week;

public class HomeViewModel extends AndroidViewModel {

    private Repository mRepository;

    private LiveData<List<Course>> mAllCourses;

    private LiveData<List<Week>> mAllWeeks;

    private LiveData<List<Day>> mAllDays;

    public HomeViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);

        mAllCourses = mRepository.getAllCourses();

        mAllWeeks = mRepository.getAllWeeks();

        mAllDays = mRepository.getAllDays();
    }

    public LiveData<List<Course>> getAllCourses() { return mAllCourses; }

    public LiveData<List<Week>> getAllWeeks() { return mAllWeeks; }

    public LiveData<List<Day>> getAllDays() { return mAllDays; }

    public void insert(Course courses) { mRepository.insert(courses); }

    public void insertWeeks(List<Week> weeks) { mRepository.insertWeeks(weeks); }

    public void insertDays(List<Day> days) { mRepository.insertDays(days); }

    public void clear() { mRepository.clear(); }
}