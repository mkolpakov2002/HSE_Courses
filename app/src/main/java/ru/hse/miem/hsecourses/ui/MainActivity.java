package ru.hse.miem.hsecourses.ui;

import static ru.hse.miem.hsecourses.Constants.isTestLaunch;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.hse.miem.hsecourses.App;
import ru.hse.miem.hsecourses.BuildConfig;
import ru.hse.miem.hsecourses.Constants;
import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.broadcasts.AlarmBroadcastReceiver;
import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.CourseViewModel;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Task;
import ru.hse.miem.hsecourses.courses.Week;
import ru.hse.miem.hsecourses.databinding.ActivityMainBinding;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.OpenFragment;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.SetEnabledNextButton;


public class MainActivity extends AppCompatActivity implements
        SetEnabledNextButton,
        CommunicateData,
        OpenFragment {

    private ActivityMainBinding binding;

    SharedPreferences sPref;

    MaterialButton prevButton;

    MaterialButton nextButton;

    LinearProgressIndicator progressBar;

    Course selectedForEducationCourse;
    List<Course> courseList;

    NavController navController;

    int fragmentId;

    boolean isCourseEditingMode = true;

    ConstraintLayout navigationLayout;

    private Calendar calendar;

    private AlarmManager alarmManager;

    private PendingIntent pendingIntent;

    private CourseViewModel model;

    List<Week> weeks;

    List<Day> days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen.installSplashScreen(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createNotificationChannel();

        model = new ViewModelProvider(this).get(CourseViewModel.class);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(this.getPackageName(), MODE_PRIVATE);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.e("APP_LOG_TAG", "onDestinationChanged: " + destination.getLabel());
                fragmentId = destination.getId();
                //отслеживания фпагмента на главном экране
                if(fragmentId==R.id.navigation_home)
                    isCourseEditingMode = false;
                if(isCourseEditingMode){
                    setEnabledNextButton(!(fragmentId==R.id.courseSelectFragment &&
                            selectedForEducationCourse==null));

                    binding.navView.setVisibility(View.GONE);

                    changeButtonPrevNextVisibility(fragmentId!=R.id.firstPageFragment && isCourseEditingMode);

                    //progressBar.setProgress(currFragmentNumber);

                    changeButtonNextText(fragmentId!=R.id.lastPageFragment);


                }
            }
        });

        navigationLayout = binding.NavigationLayout;
        progressBar = binding.progressBar;
        nextButton = binding.buttonNext;
        prevButton = binding.buttonPrev;

        model.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable final List<Course> words) {
                // Update the cached copy of the words in the adapter.
                courseList = words;
            }
        });

        if (!prefs.getBoolean(Constants.courseIsSavedKey, false)) {

            isCourseEditingMode = true;

            if(isTestLaunch){
                setupTestCourses();
                //getAllDaysByWeekNumber(0);
            }

            navGraph.setStartDestination(R.id.firstPageFragment);

            progressBar.setMax(Constants.courseSettingPagesCount-1);

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNextFragment(null);
                }
            });

            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigatePrevFragment(null);
                }
            });

            selectedForEducationCourse = null;

            binding.navView.setVisibility(View.GONE);

        } else {
            loadCourseData();
            isCourseEditingMode = false;
            navGraph.setStartDestination(R.id.navigation_home);

            setupBottomMenu();

        }
        navController.setGraph(navGraph);
    }

    private void loadCourseData() {

        model.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable final List<Course> words) {
                // Update the cached copy of the words in the adapter.
                courseList = new ArrayList<>();
                if(words!=null)
                    courseList.addAll(words);
                for(Course c: courseList)
                    if(c.isSelected())
                        selectedForEducationCourse = c;

                    if(weeks!=null)
                        selectedForEducationCourse.setWeekList(weeks);

                    if(days!=null&&weeks!=null) {
                        for(Day d: days){
                            selectedForEducationCourse.getWeekList().get(d.getWeekNumber()).addDay(d);
                        }
                    }
            }
        });


        model.getAllWeeks().observe(this, new Observer<List<Week>>() {
            @Override
            public void onChanged(@Nullable final List<Week> words) {
                // Update the cached copy of the words in the adapter.
                weeks = new ArrayList<>();
                if(words!=null)
                    weeks.addAll(words);


                if(selectedForEducationCourse!=null)
                    selectedForEducationCourse.setWeekList(weeks);


                if(days!=null&&selectedForEducationCourse!=null) {
                    for(Day d: days){
                        selectedForEducationCourse.getWeekList().get(d.getWeekNumber()).addDay(d);
                    }
                }
            }
        });

        model.getAllDays().observe(this, new Observer<List<Day>>() {
            @Override
            public void onChanged(@Nullable final List<Day> words) {
                // Update the cached copy of the words in the adapter.
                days = new ArrayList<>();
                if(words!=null){
                    days.addAll(words);
                }

                if(selectedForEducationCourse!=null&&weeks!=null)
                    for(Day d: days){
                        selectedForEducationCourse.getWeekList().get(d.getWeekNumber()).addDay(d);
                    }
            }
        });
    }

    void setupTestCourses(){
        if(isTestLaunch){

            List<Course> courseList = new ArrayList<>();
            Course course1 = new Course();
            course1.setCourseName("Личная эффективность");
            Course course2 = new Course();
            course2.setCourseName("Искусство публичных выступлений");
            Course course3 = new Course();
            course3.setCourseName("Проекты цифровой информации");
            Course course4 = new Course();
            course4.setCourseName("Взаимодействие государства и бизнеса в условиях цифровой трансформации");
            courseList.add(course1);
            courseList.add(course2);
            courseList.add(course3);
            courseList.add(course4);

            model.clear();

            for(Course course: courseList)
                model.insert(course);
        }
    }

    void setupBottomMenu(){
        binding.cardView.setVisibility(View.GONE);
        binding.navView.setVisibility(View.VISIBLE);
        binding.navView.getMenu().clear();
        binding.navView.inflateMenu(R.menu.bottom_nav_menu);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    void saveChanges(){
        setupBottomMenu();

        sPref = getApplicationContext().getSharedPreferences(this.getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(Constants.courseIsSavedKey, true);
        ed.apply();

        model.insert(selectedForEducationCourse);

    }

    public void changeButtonPrevNextVisibility(boolean b) {
        if(b){
            binding.cardView.setVisibility(View.VISIBLE);
            prevButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.cardView.setVisibility(View.INVISIBLE);
//            prevButton.setVisibility(View.INVISIBLE);
//            nextButton.setVisibility(View.INVISIBLE);
//            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void changeButtonNextText(boolean b) {
        if(b){
            nextButton.setText(getString(R.string.next));
        } else {
            nextButton.setText(getString(R.string.finish));
        }
    }

    @Override
    public void setEnabledNextButton(boolean status) {
        nextButton.setEnabled(status);
    }

    @Override
    public void openNextFragment(Bundle b) {
        if(fragmentId == R.id.lastPageFragment){
            saveChanges();
            changeButtonPrevNextVisibility(false);
        }
        navigateNextFragment(b);
    }

    @Override
    public void setUpdatedCourse(Course updatedCourse) {
        this.selectedForEducationCourse = updatedCourse;
    }

    @Override
    public List<Course> availableCourses() {
        return courseList;
    }

    @Override
    public Course getCourse() {
        return selectedForEducationCourse;
    }


    void navigateNextFragment(Bundle bundle){
        if(bundle==null)
           bundle = new Bundle();
        switch (fragmentId){
            case (R.id.firstPageFragment):
                navController.navigate(R.id.courseSelectFragment, bundle);
                progressBar.setProgress(1, true);
                break;
            case (R.id.courseSelectFragment):
                navController.navigate(R.id.courseTargetFragment, bundle);
                progressBar.setProgress(2, true);
                break;
            case (R.id.courseTargetFragment):
                navController.navigate(R.id.courseDayFragment, bundle);
                progressBar.setProgress(3, true);
                break;
            case (R.id.courseDayFragment):
            case (R.id.taskTimeDialogFragment):
                if(selectedForEducationCourse.getHoursCount()>=selectedForEducationCourse.getMinCourseTime()){
                    navController.navigate(R.id.lastPageFragment, bundle);
                    progressBar.setProgress(4, true);
                } else {
                    bundle.putString("messageText", getString(R.string.warning_little_hours));
                    bundle.putBoolean("isWarningMode", true);
                    navController.navigate(R.id.textDialogFragment, bundle);
                }
                break;
            case (R.id.textDialogFragment):
                navController.navigate(R.id.lastPageFragment, bundle);
                progressBar.setProgress(4, true);
                break;
            default:
                navController.navigate(R.id.navigation_home, bundle);
                saveCourse();
                break;
        }
    }

    void navigatePrevFragment(Bundle bundle){
        if(bundle==null)
            bundle = new Bundle();
        switch (fragmentId){
            case (R.id.courseTargetFragment):
                navController.navigate(R.id.courseSelectFragment, bundle);
                progressBar.setProgress(1, true);
                break;
            case (R.id.courseDayFragment):
            case (R.id.taskTimeDialogFragment):
                navController.navigate(R.id.courseTargetFragment, bundle);
                progressBar.setProgress(2, true);
                break;
            case (R.id.lastPageFragment):
                navController.navigate(R.id.courseDayFragment, bundle);
                progressBar.setProgress(3, true);
                break;
            default:
                navController.navigate(R.id.firstPageFragment, bundle);
                progressBar.setProgress(0, true);
                break;
        }
    }

    @Override
    public List<Task> getTasksByWeekDayNumber(int dayNumber, int weekNumber) {
        //TODO
        return selectedForEducationCourse.getWeekList().get(weekNumber).getDayList().get(dayNumber).getTaskList();
    }

    @Override
    public List<Day> getAllDaysByWeekNumber(int weekNumber) {
        //TODO
        if(selectedForEducationCourse.getWeekList()== null || selectedForEducationCourse.getWeekList().isEmpty()){
            List<Day> newDayList = new ArrayList<>();

            Day day1 = new Day();
            day1.setDayName("Понедельник");
            day1.setDayNumber(0);
            newDayList.add(day1);

            Day day2 = new Day();
            day2.setDayName("Вторник");
            day2.setDayNumber(1);
            newDayList.add(day2);

            Day day3 = new Day();
            day3.setDayName("Среда");
            day3.setDayNumber(2);
            newDayList.add(day3);

            Day day4 = new Day();
            day4.setDayName("Четверг");
            day4.setDayNumber(3);
            newDayList.add(day4);

            Day day5 = new Day();
            day5.setDayName("Пятница");
            day5.setDayNumber(4);
            newDayList.add(day5);

            Day day6 = new Day();
            day6.setDayName("Суббота");
            day6.setDayNumber(5);
            newDayList.add(day6);

            Day day7 = new Day();
            day7.setDayName("Воскресенье");
            day7.setDayNumber(6);
            newDayList.add(day7);
            List<Week> weeks = new ArrayList<>();
            //TODO
            for(int i = 0; i < selectedForEducationCourse.getWeekCount(); i++){
                Week curr = new Week();
                curr.setWeekNumber(i);
                curr.setEnded(false);
                curr.setCourseNumber(selectedForEducationCourse.getCourseId());
                for(int j = 0; j < 7; j++){
                    newDayList.get(j).setWeekNumber(i);
                }
                curr.setDayList(newDayList);
                weeks.add(curr);
            }

            selectedForEducationCourse.setWeekList(weeks);
        }
        //TODO
        return selectedForEducationCourse.getWeekList().get(0).getDayList();
    }

    @Override
    public void saveCourse() {
        App app = (App) getApplication();
        selectedForEducationCourse.setSelected(true);
        model.insert(selectedForEducationCourse);
        model.insertWeeks(selectedForEducationCourse.getWeekList());
        for(int i = 0; i < selectedForEducationCourse.getWeekCount(); i++)
            model.insertDays(selectedForEducationCourse.getWeekList().get(i).getDayList());
    }

    @Override
    public void saveTaskToDay(int dayNumber, List<Task> taskList) {
        selectedForEducationCourse.getWeekList().get(0).getDayList().get(dayNumber).setTaskList(taskList);
    }

    @Override
    public void saveEditedDayInWeek(int weekNumber, Day day) {
        selectedForEducationCourse.getWeekList().get(weekNumber).updateDay(day);
    }

    @Override
    public void setSelectedForEducationCourse(int selectedForEducationCourseId) {
        for (Course course : availableCourses()) {
            if (course.getCourseId() == selectedForEducationCourseId) {
                selectedForEducationCourse = course;
                setEnabledNextButton(true);
            }
        }
    }

    void calculateSelectedCourseTime(){

        if(selectedForEducationCourse.getHoursCount()<selectedForEducationCourse.getMinCourseTime()){

        }

    }


    private void cancelAlarm() {

        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        if (alarmManager == null){

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);

        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();

    }


    private void createNotificationChannel() {

        CharSequence name = BuildConfig.APPLICATION_ID;
        String description = "HSE Courses Alarm Manager";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(BuildConfig.APPLICATION_ID,name,importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);


    }


}