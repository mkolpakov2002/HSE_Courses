package ru.hse.miem.hsecourses.courses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasksList();

    @Query("DELETE FROM Task")
    void truncateTheList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataIntoTaskList(Task task);

    @Query("DELETE FROM Task WHERE taskId = :taskId")
    void deleteTaskFromId(int taskId);

    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    Task selectDataFromAnId(int taskId);

//    @Query("UPDATE Task SET taskDescription = :taskDescription, startTime = :taskDate WHERE taskId = :taskId")
//    void updateAnExistingRow(int taskId, String taskTitle, String taskDescription , String taskDate, String taskTime,
//                             String taskEvent);

}