package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import java.util.List;

import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Module;
import ru.hse.miem.hsecourses.courses.Task;
import ru.hse.miem.hsecourses.courses.Topic;
import ru.hse.miem.hsecourses.ui.MainViewModel;

public interface CommunicateData {
    void setUpdatedCourse(Course course);
    void setSelectedForEducationCourse(int selectedForEducationCourseId);
    String getAdapterMode();
    void setDay(int dayNumber, Day day);
    void onRefresh();
    List<Course> availableCourses();
    List<Day> getAllDays();
    Course getCourse();
    List<Task> getAllTasks();
    List<Task> getAllTasksByDay(int dayNumber);
    void setUpdatedTasksByDay(List<Task> taskList, int dayNumber);
    List<Module> getAllModules();
    List<Topic> getAllTopics();
    MainViewModel getModel();
    void setModuleEnded(int pos);
    void setModuleNotEnded(int pos);
}