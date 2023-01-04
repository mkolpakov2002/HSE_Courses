package ru.hse.miem.hsecourses.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import ru.hse.miem.hsecourses.BuildConfig;
import ru.hse.miem.hsecourses.Constants;
import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.broadcasts.AlarmBroadcastReceiver;
import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Module;
import ru.hse.miem.hsecourses.courses.Task;
import ru.hse.miem.hsecourses.courses.Topic;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.OpenFragment;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.SetEnabledNextButton;


public class MainActivity extends AppCompatActivity implements
        SetEnabledNextButton,
        CommunicateData,
        OpenFragment {

    boolean isPermissionNotifyAsked = false;

    MaterialButton prevButton;

    MaterialButton nextButton;

    LinearProgressIndicator progressBar;

    NavController navController;

    int fragmentId;

    boolean isCourseEditingMode = true;

    ConstraintLayout navigationLayout;

    private Calendar calendar;

    private AlarmManager alarmManager;

    private PendingIntent pendingIntent;

    SharedPreferences prefs;

    BottomNavigationView navView;

    MaterialCardView cardViewPrevNextButton;

    ImageView spaceBackground;

    //Data section

    private MainViewModel model;
    private List<Module> moduleList;
    private List<Topic> topicList;
    private List<Day> dayList;
    private Course selectedForEducationCourse;
    private List<Course> courseList;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen.installSplashScreen(this);

        setContentView(R.layout.activity_main);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        prefs = getApplicationContext().getSharedPreferences(this.getPackageName(), MODE_PRIVATE);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        navigationLayout = findViewById(R.id.NavigationLayout);
        progressBar = findViewById(R.id.progressBar);
        nextButton = findViewById(R.id.button_next);
        prevButton = findViewById(R.id.button_prev);
        navView = findViewById(R.id.nav_view);
        cardViewPrevNextButton = findViewById(R.id.cardView);
        spaceBackground = findViewById(R.id.space_background);

        setUpDataBase();

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

                    navView.setVisibility(View.GONE);

                    changeButtonPrevNextVisibility(fragmentId!=R.id.firstPageFragment &&
                            fragmentId!=R.id.permissionFragment && isCourseEditingMode);

                    changeButtonNextText(fragmentId!=R.id.lastPageFragment);
                }
            }
        });

        onRefresh();
    }

    void setUpHomeDestination(boolean isHome){
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);
        if(isHome){
            navView.setVisibility(View.VISIBLE);
            navGraph.setStartDestination(R.id.navigation_home);
        } else {
            navView.setVisibility(View.GONE);
            navGraph.setStartDestination(R.id.firstPageFragment);
        }
        navController.setGraph(navGraph);
    }

    public void onRefresh(){
        prefs = getApplicationContext().getSharedPreferences(this.getPackageName(), MODE_PRIVATE);
        if (!prefs.getBoolean(Constants.courseIsSavedKey, false)) {

            isCourseEditingMode = true;

            setUpHomeDestination(false);

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

            dayList = new ArrayList<>();

            spaceBackground.setVisibility(View.GONE);

        } else {
            isCourseEditingMode = false;

            setUpHomeDestination(true);

            setupBottomMenu();

            switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                case Configuration.UI_MODE_NIGHT_YES -> spaceBackground.setVisibility(View.VISIBLE);
                case Configuration.UI_MODE_NIGHT_NO -> spaceBackground.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isPermissionNotifyAsked)
            askNotificationPermission();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPrefs();
    }

    boolean checkboxPreference;
    String listPreference;
    String adapterPreference;

    private void getPrefs() {
        // Get the xml/preferences.xml preferences
        checkboxPreference = prefs.getBoolean("alarmMode", true);
        listPreference = prefs.getString("alarm_time", "default");
        adapterPreference = prefs.getString("adapter",
                "planet");

        Log.e("jngndjnbdkn", listPreference);
        Log.e("jngndjnbdkn", adapterPreference);
    }


    void setupBottomMenu(){
        cardViewPrevNextButton.setVisibility(View.GONE);
        navView.setVisibility(View.VISIBLE);
        navView.getMenu().clear();
        navView.inflateMenu(R.menu.bottom_nav_menu);
        NavigationUI.setupWithNavController(navView, navController);
    }


    void exitCourseSetting(){
        SharedPreferences.Editor ed = prefs.edit();
        ed.putBoolean(Constants.courseIsSavedKey, true);
        ed.apply();

        onRefresh();

        selectedForEducationCourse.setSelected(true);
        saveAll();
    }

    void saveAll(){
        model.insert(selectedForEducationCourse);
        model.insertModules(moduleList);
        model.insertTopics(topicList);
        model.insertDays(dayList);
        model.insertTasks(taskList);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putSerializable("selectedForEducationCourse", selectedForEducationCourse);
        savedInstanceState.putSerializable("moduleList", (Serializable) moduleList);
        savedInstanceState.putSerializable("topicList", (Serializable) topicList);
        savedInstanceState.putSerializable("dayList", (Serializable) dayList);
        savedInstanceState.putSerializable("taskList", (Serializable) taskList);

        // etc.

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        selectedForEducationCourse = (Course) savedInstanceState.getSerializable("selectedForEducationCourse");
        moduleList = (List<Module>) savedInstanceState.getSerializable("moduleList");
        topicList = (List<Topic>) savedInstanceState.getSerializable("topicList");
        dayList = (List<Day>) savedInstanceState.getSerializable("dayList");
        taskList = (List<Task>) savedInstanceState.getSerializable("taskList");
    }

    @Override
    public void onPause(){
        super.onPause();
        saveAll();
    }

    public void changeButtonPrevNextVisibility(boolean b) {
        if(b){
            cardViewPrevNextButton.setVisibility(View.VISIBLE);
            prevButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            cardViewPrevNextButton.setVisibility(View.GONE);
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
            exitCourseSetting();
            changeButtonPrevNextVisibility(false);
        }
        navigateNextFragment(b);
    }

    @Override
    public void setUpdatedCourse(Course updatedCourse) {
        this.selectedForEducationCourse = updatedCourse;
    }

    void navigateNextFragment(Bundle bundle){
        if(bundle==null)
           bundle = new Bundle();
        switch (fragmentId){
            case (R.id.firstPageFragment):
                if(!NotificationManagerCompat.from(this).areNotificationsEnabled()){
                    navigatePermissionPage();
                } else {
                    navController.navigate(R.id.courseSelectFragment, bundle);
                    progressBar.setProgress(1, true);
                }
                break;
            case (R.id.permissionFragment):
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
                break;
        }
    }

    void navigatePrevFragment(Bundle bundle){
        if(bundle==null)
            bundle = new Bundle();
        switch (fragmentId) {
            case (R.id.courseTargetFragment) -> {
                navController.navigate(R.id.courseSelectFragment, bundle);
                progressBar.setProgress(1, true);
            }
            case (R.id.courseDayFragment), (R.id.taskTimeDialogFragment) -> {
                navController.navigate(R.id.courseTargetFragment, bundle);
                progressBar.setProgress(2, true);
            }
            case (R.id.lastPageFragment) -> {
                navController.navigate(R.id.courseDayFragment, bundle);
                progressBar.setProgress(3, true);
            }
            default -> {
                navController.navigate(R.id.firstPageFragment, bundle);
                progressBar.setProgress(0, true);
            }
        }
    }

    @Override
    public void setDay(int dayNumber, Day day){
        dayList.remove(dayNumber);
        dayList.add(day);
        dayList.sort(Comparator.comparing(Day::getDayNumber));
    }

    @Override
    public List<Course> availableCourses() {
        return courseList;
    }

    @Override
    public Course getCourse() {
        return selectedForEducationCourse;
    }

    @Override
    public List<Day> getAllDays() {
        return dayList;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskList;
    }

    @Override
    public List<Task> getAllTasksByDay(int dayNumber) {
        ArrayList<Task> taskArrayList = new ArrayList<>(taskList);
        taskArrayList.removeIf(t -> t.getDayNumber()!=dayNumber);
        return taskArrayList;
    }

    @Override
    public void setUpdatedTasksByDay(List<Task> taskList, int dayNumber) {
        this.taskList.removeIf(t -> t.getDayNumber()==dayNumber);
        this.taskList.addAll(taskList);
        remakeHoursCount();
    }

    @Override
    public List<Module> getAllModules() {
        return moduleList;
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicList;
    }

    @Override
    public void setSelectedForEducationCourse(int selectedForEducationCourseId) {
        for (Course course : courseList) {
            if (course.getCourseId() == selectedForEducationCourseId) {
                selectedForEducationCourse = course;
                setEnabledNextButton(true);
            }
        }
    }

    @Override
    public String getAdapterMode() {
        prefs = getApplicationContext().getSharedPreferences(this.getPackageName(), MODE_PRIVATE);
        return prefs.getString("adapterMode", Constants.adapterHomeGame);
    }

    void remakeHoursCount(){
        int count = 0;
        for(Task t: taskList){
            count += t.getTaskTimeCount()/(1000*60*60d);
        }
        selectedForEducationCourse.setHoursCount(count);
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

    Integer tick = 0;
    ArrayList<Integer> RC_ARRAY = new ArrayList<>();

    private void setWeekAlarm(Calendar c) {

        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        //when alarm store set the request assign to tick variable
        tick = (int) System.currentTimeMillis();
        //Add all the alarm Request into RC_ARRAY for just cancel the alarm
        RC_ARRAY.add(tick);

        //Notification Broadcast intent
        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        PendingIntent appIntent = PendingIntent.getBroadcast(this, tick, intent, PendingIntent.FLAG_ONE_SHOT);

        //alarm fire next day if this condition is not statisfied
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        //set alarm
        manager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000 * 60 * 10080,
                appIntent);
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    Bundle b = new Bundle();
                    b.putString("messageText", getString(R.string.notify_error));
                    navController.navigate(R.id.textDialogFragment, b);
                }
            });

    private synchronized void askNotificationPermission() {
        isPermissionNotifyAsked = true;
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
                createNotificationChannel();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                navigatePermissionPage();
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            createNotificationChannel();
        } else {
            Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
        }
    }


    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(BuildConfig.APPLICATION_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void setUpDataBase(){

        boolean isFirstLaunch = prefs.getBoolean("isFirstLaunch", true);

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

                if(selectedForEducationCourse == null && isFirstLaunch){
                    model.clear();
                    //TODO: offline test implementation
                    parseCourses();
                }
            }
        });

        model.getAllDays().observe(this, new Observer<List<Day>>() {
            @Override
            public void onChanged(@Nullable final List<Day> words) {
                // Update the cached copy of the words in the adapter.
                dayList = new ArrayList<>();
                if (words != null) {
                    if(words.size()==0)
                        parseDays();
                    else {
                        dayList.addAll(words);
                    }
                }
//                if(words != null){
//                    dayList.addAll(words);
//                } else if(isFirstLaunch) {
//                    parseDays();
//                }

            }
        });

        model.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> words) {
                // Update the cached copy of the words in the adapter.
                taskList = new ArrayList<>();
                if(words != null){
                    taskList.addAll(words);
                }
            }
        });

        model.getAllModules().observe(this, new Observer<List<Module>>() {
            @Override
            public void onChanged(@Nullable final List<Module> words) {
                // Update the cached copy of the words in the adapter.
                moduleList = new ArrayList<>();
                if(words != null){
                    moduleList.addAll(words);
                }
            }
        });

        model.getAllTopics().observe(this, new Observer<List<Topic>>() {
            @Override
            public void onChanged(@Nullable final List<Topic> words) {
                // Update the cached copy of the words in the adapter.
                topicList = new ArrayList<>();
                if(words != null){
                    topicList.addAll(words);
                }
            }
        });

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isFirstLaunch",false);
        editor.apply();
    }

    @Override
    public MainViewModel getModel(){
        if(model == null)
            model = new ViewModelProvider(this).get(MainViewModel.class);
        return model;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        setUpDataBase();
    }

    private void parseDays(){
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
        dayList.clear();
        dayList.addAll(newDayList);

        model.insertDays(dayList);
    }

    private void parseCourses(){

        List<Course> courseListNew = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseName("Личная эффективность");
        Course course2 = new Course();
        course2.setCourseName("Искусство публичных выступлений");
        Course course3 = new Course();
        course3.setCourseName("Проекты цифровой информации");
        Course course4 = new Course();
        course4.setCourseName("Взаимодействие государства и бизнеса в условиях цифровой трансформации");


        Module begin = new Module();
        begin.setModuleName("Введение в курс");
        begin.setUnlocked(true);
        begin.setModuleNumber(0);

        Module m1 = new Module();
        m1.setModuleName("Модуль 1. Цифровая трансформация: глобальный контекст, основные понятия и тенденции");
        m1.setCourseNumber(0);
        begin.setModuleNumber(1);


        Module m3 = new Module();
        m3.setModuleName("Модуль 2. Цифровые экосистемы и бизнес-модели. Архитектура предприятия");
        m3.setCourseNumber(0);
        begin.setModuleNumber(2);

        Module m4 = new Module();
        m4.setModuleName("Модуль 3. Цифровые технологии. Управление компанией на основе данных");
        m4.setCourseNumber(0);
        begin.setModuleNumber(3);

        Module m5 = new Module();
        m5.setModuleName("Модуль 4. Стратегии цифровой трансформации компании");
        m5.setCourseNumber(0);
        begin.setModuleNumber(4);

        moduleList = new ArrayList<>();

        moduleList.add(begin);
        moduleList.add(m1);
        moduleList.add(m3);
        moduleList.add(m4);
        moduleList.add(m5);

        courseListNew.add(course1);
        courseListNew.add(course2);
        courseListNew.add(course3);
        courseListNew.add(course4);

        courseList = courseListNew;

        model.clear();

        //Topics

        Topic t1 = new Topic();
        t1.setTopicName("Что такое цифр. тр.");
        t1.setModuleNumber(m1.getModuleNumber());
        t1.setItemType(0);

        Topic t2 = new Topic();
        t2.setTopicName("Учебные задания 1.1");
        t2.setModuleNumber(m1.getModuleNumber());
        t2.setItemType(1);

        Topic t3 = new Topic();
        t3.setTopicName("Контрольные задания 1.1");
        t3.setModuleNumber(m1.getModuleNumber());
        t3.setItemType(2);

        Topic t4 = new Topic();
        t4.setTopicName("Процессы цифровой трансформации");
        t4.setModuleNumber(m1.getModuleNumber());
        t4.setItemType(0);

        List<Topic> topicList = new ArrayList<>();
        topicList.add(t1);
        topicList.add(t2);
        topicList.add(t3);
        topicList.add(t4);

        model.insertTopics(topicList);


        for(Course course: courseList)
            model.insert(course);
    }

    public void setModuleEnded(int pos){
        for(int i = 0; i<moduleList.size(); i++){
            if(i<=pos){
                moduleList.get(i).setEnded(true);
            } else {
                moduleList.get(i).setEnded(false);
                moduleList.get(i).setUnlocked(i == pos + 1);
            }
        }
    }

    public void setModuleNotEnded(int pos){
        for(int i = 0; i<moduleList.size(); i++){
            if(i<pos){
                moduleList.get(i).setEnded(true);
            } else {
                moduleList.get(i).setEnded(false);
                moduleList.get(i).setUnlocked(i == pos);
            }
        }
    }

    void navigatePermissionPage(){
        // TODO: display an educational UI explaining to the user the features that will be enabled
        //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
        //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
        //       If the user selects "No thanks," allow the user to continue without notifications.
        Bundle b = new Bundle();
        b.putString("messageTitleText", getString(R.string.notify_title));
        b.putString("messageDescriptionText", getString(R.string.notify_text));
        b.putInt("messageIcon", R.drawable.ic_notify_permission);
        navController.navigate(R.id.permissionFragment, b);
    }

}