package ru.hse.miem.hsecourses.courses;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Week implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int weekId;

    @Ignore
    private List<Day> dayList;

    private int weekNumber;

    private int courseNumber;

    private boolean isEnded;

    public Week(){
        dayList = new ArrayList<>();
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public void updateDay(Day editedDay){
        for(Day day: dayList){
            if(day.getDayNumber() == editedDay.getDayNumber()){
                dayList.remove(day);
                dayList.add(editedDay.getDayNumber(), editedDay);
            }
        }
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    public void addDay(Day d){
        dayList.add(d);
    }
}
