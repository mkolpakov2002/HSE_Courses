package ru.hse.miem.hsecourses.courses;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private Repository mRepository;

    private LiveData<List<Course>> mAllWords;

    public CourseViewModel (Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllWords = mRepository.getAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() { return mAllWords; }

    public void insert(Course courses) { mRepository.insert(courses); }

    public void clear() { mRepository.clear(); }
}
