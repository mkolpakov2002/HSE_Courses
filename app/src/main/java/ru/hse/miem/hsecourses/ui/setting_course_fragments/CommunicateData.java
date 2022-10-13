package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import java.util.List;

import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Task;

public interface CommunicateData {
    List<Task> getTasksByWeekDayNumber(int dayNumber, int weekNumber);
    List<Day> getAllDaysByWeekNumber(int weekNumber);
    void saveCourse();
    void saveTaskToDay(int dayNumber, List<Task> tasks);
    void saveEditedDayInWeek(int weekNumber, Day day);
    Course getCourse();
    void setUpdatedCourse(Course course);
    List<Course> availableCourses();
    void setSelectedForEducationCourse(int selectedForEducationCourseId);

}
