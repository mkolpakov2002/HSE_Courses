package ru.hse.miem.hsecourses.courses;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Module implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int moduleId;

    private int moduleNumber;

    private int courseNumber;

    private boolean isEnded = false;

    private boolean isUnlocked = false;

    private String moduleInformation;

    private String moduleName;

    @Ignore
    private List<Topic> topicList;

    public Module(){
        topicList = new ArrayList<>();
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleNumber(int moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    public int getModuleNumber() {
        return moduleNumber;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

//    public List<Day> getDayList() {
//        return dayList;
//    }
//
//    public void setDayList(List<Day> dayList) {
//        this.dayList = dayList;
//    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

//    public void updateDay(Day editedDay){
//        for(Day day: dayList){
//            if(day.getDayNumber() == editedDay.getDayNumber()){
//                dayList.remove(day);
//                dayList.add(editedDay.getDayNumber(), editedDay);
//            }
//        }
//    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    public void addTopic(Topic d){
        topicList.add(d);
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    public String getModuleInformation() {
        return moduleInformation;
    }

    public void setModuleInformation(String moduleInformation) {
        this.moduleInformation = moduleInformation;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
