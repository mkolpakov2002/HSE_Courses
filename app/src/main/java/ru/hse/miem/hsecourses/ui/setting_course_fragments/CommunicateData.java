package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import java.util.List;

import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Task;

public interface CommunicateData {
    List<Task> getTasksByDayNumber(int dayNumber);
    List<Day> getAllDays();
    void saveDays(List<String> newReminders);
    void saveTaskToDay(int dayNumber, List<Task> tasks);
    void saveEditedDay(Day day);
    Course getCourse();
    void setUpdatedCourse(Course course);
    List<Course> availableCourses();
    void setSelectedForEducationCourse(int selectedForEducationCourseId);

}