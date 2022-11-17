package ru.hse.miem.hsecourses.courses;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class Course implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String courseName;

    private String courseTarget;

    private int hoursCount;

    private int endedHoursCount;

    private long minCourseTime;

    @Ignore
    private List<Module> moduleList;

    @Ignore
    private Date creationTime;

    boolean isSelected;


    public Course(int courseId, String courseName, String courseTarget, int hoursCount,
                  int endedHoursCount, List<Module> moduleList, Date creationTime, int moduleCount, boolean isSelected){
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseTarget = courseTarget;
        this.hoursCount = hoursCount;
        this.endedHoursCount = endedHoursCount;
        this.moduleList = moduleList;
        this.creationTime = creationTime;
        minCourseTime = 1000000;
        this.isSelected = isSelected;
    }

    public Course(){
        courseName = "Default course";
        courseTarget = "";
        hoursCount = 100;
        endedHoursCount = 0;
        moduleList = new ArrayList<>();
        creationTime = Calendar.getInstance().getTime();
        minCourseTime = 1000000;
        isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseTarget() {
        return courseTarget;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseTarget(String courseTarget) {
        this.courseTarget = courseTarget;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getHoursCount() {
        return hoursCount;
    }

    public void setHoursCount(int hoursCount) {
        this.hoursCount = hoursCount;
    }

    public int getEndedHoursCount() {
        return endedHoursCount;
    }

    public void setEndedHoursCount(int endedHoursCount) {
        this.endedHoursCount = endedHoursCount;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        //this.moduleList.clear();
        this.moduleList=(moduleList);
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getMinCourseTime() {
        return minCourseTime;
    }

    public void setMinCourseTime(long minCourseTime) {
        this.minCourseTime = minCourseTime;
    }

    public int getModuleCount() {
        return moduleList.size();
    }

}
