package ru.hse.miem.hsecourses.courses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int taskId;

    @ColumnInfo(name = "startTime")
    Date startTime;
    @ColumnInfo(name = "endTime")
    Date endTime;
    @ColumnInfo(name = "taskDescription")
    String taskDescription;
    @ColumnInfo(name = "isComplete")
    boolean isComplete;
    @ColumnInfo(name = "dayNumber")
    int dayNumber;

    public Task() {

    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getInterval() {
        return startTime.getHours()+
                ":"+
                startTime.getMinutes()+
                " - "+
                endTime.getHours()+
                ":"+
                endTime.getMinutes();
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getDayNumber() {
        return dayNumber;
    }
}
