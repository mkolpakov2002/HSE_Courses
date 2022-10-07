package ru.hse.miem.hsecourses.courses;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface DayDao {
    @Query("SELECT * FROM Day")
    List<Day> getAllDaysList();

    @Query("DELETE FROM Day")
    void truncateTheList();

    @Insert
    void insertDataIntoDaysList(Day day);

    @Query("DELETE FROM Day WHERE dayId = :dayId")
    void deleteDayFromId(int dayId);

    @Query("SELECT * FROM Day WHERE dayId = :dayId")
    Task selectDataFromAnId(int dayId);
}
