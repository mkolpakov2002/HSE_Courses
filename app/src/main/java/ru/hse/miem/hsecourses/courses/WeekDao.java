package ru.hse.miem.hsecourses.courses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WeekDao {
    @Query("SELECT * FROM Week")
    LiveData<List<Week>> getAllWeeks();

    @Query("DELETE FROM Week")
    void truncateTheList();

    @Insert
    void insertDataIntoWeeksList(Week week);

    @Query("DELETE FROM Week WHERE weekId = :weekId")
    void deleteWeekFromId(int weekId);

    @Query("SELECT * FROM Week WHERE weekId = :weekId")
    List<Week> selectDataById(int weekId);

    @Query("SELECT * FROM Week WHERE weekNumber = :weekNumber")
    List<Week> selectDataByNumber(int weekNumber);
}
