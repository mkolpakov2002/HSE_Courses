package ru.hse.miem.hsecourses;

import static ru.hse.miem.hsecourses.Constants.isTestLaunch;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import ru.hse.miem.hsecourses.courses.AppDataBase;
import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Task;

public class App extends Application {

    App instance;

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!

        instance = this;

    }

    public App getInstance() {
        return instance;
    }

}
