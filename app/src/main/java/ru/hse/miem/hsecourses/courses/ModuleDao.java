package ru.hse.miem.hsecourses.courses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ModuleDao {
    @Query("SELECT * FROM Module")
    LiveData<List<Module>> getAllWeeks();

    @Query("DELETE FROM Module")
    void truncateTheList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataIntoWeeksList(Module module);

    @Query("DELETE FROM Module WHERE moduleId = :weekId")
    void deleteWeekFromId(int weekId);

    @Query("SELECT * FROM Module WHERE moduleId = :weekId")
    List<Module> selectDataById(int weekId);

    @Query("SELECT * FROM Module WHERE moduleNumber = :weekNumber")
    List<Module> selectDataByNumber(int weekNumber);
}
