package ru.hse.miem.hsecourses.courses;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Course.class, Task.class, Day.class, Module.class, Topic.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    private static final String DB_NAME = "application_db";

    private static AppDataBase INSTANCE;

    //Simple singleton - is better to use dagger to inject db.
    public static AppDataBase getAppDb(Context context) {
        synchronized (AppDataBase.class){
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDataBase.class, DB_NAME)
                        .fallbackToDestructiveMigration() //temporary
                        .build();
            }
        }

        return INSTANCE;
    }

    public abstract CourseDao getCourseDao();

    public abstract ModuleDao getWeeksDao();

    public abstract DayDao getDaysDao();

    public abstract TopicDao getTopicsDao();
}
