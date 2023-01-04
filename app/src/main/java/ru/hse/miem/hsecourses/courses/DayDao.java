package ru.hse.miem.hsecourses.courses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DayDao {
    @Query("SELECT * FROM Day")
    LiveData<List<Day>> getAllDays();

    @Query("DELETE FROM Day")
    void truncateTheList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataIntoDaysList(Day day);

    @Query("DELETE FROM Day WHERE dayId = :dayId")
    void deleteDayFromId(int dayId);

    @Query("SELECT * FROM Day WHERE dayId = :dayId")
    List<Day> selectDataFromAnId(int dayId);
}
