package ru.hse.miem.hsecourses.courses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TopicDao {

    @Query("SELECT * FROM Topic")
    LiveData<List<Topic>> getAllTopicsList();

    @Query("DELETE FROM Topic")
    void truncateTheList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataIntoTopicList(Topic task);

    @Query("DELETE FROM Topic WHERE topicId = :topicId")
    void deleteTopicFromId(int topicId);

    @Query("SELECT * FROM Topic WHERE topicId = :topicId")
    Topic selectDataFromAnId(int topicId);

}
