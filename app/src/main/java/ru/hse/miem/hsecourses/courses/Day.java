package ru.hse.miem.hsecourses.courses;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Day implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int dayId;

    private int dayNumber;

    private String dayName;

    private int weekNumber;

    @Ignore
    long tasksTimeCount;

    public Day(){
    }

    public Day(Day day){
        this.dayId = day.dayId;
        this.dayNumber = day.dayNumber;
        this.dayName = day.dayName;
        this.weekNumber = day.weekNumber;
        this.tasksTimeCount = day.tasksTimeCount;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

//    public long getTasksTimeCount() {
//        for(int i = 0; i< taskList.size(); i++){
//            tasksTimeCount = taskList.get(i).getEndTime().getTime()
//                    - taskList.get(i).getStartTime().getTime();
//            //tasksTimeCount
//        }
//        return tasksTimeCount;
//    }
}
