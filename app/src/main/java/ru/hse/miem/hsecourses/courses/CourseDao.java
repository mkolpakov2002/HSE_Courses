package ru.hse.miem.hsecourses.courses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Course... courses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Course> courses);


    // Удаление Person из бд
    @Delete
    void delete(Course person);

    // Получение всех Person из бд
    @Query("SELECT * FROM course")
    LiveData<List<Course>> getAllCourses();

    @Query("DELETE FROM course")
    public void clearTable();

}
