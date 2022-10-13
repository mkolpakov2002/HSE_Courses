package ru.hse.miem.hsecourses.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.CourseViewModel;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Week;
import ru.hse.miem.hsecourses.databinding.FragmentHomeBinding;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.OpenFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private CommunicateData listener;

    Course selectedForEducationCourse;
    List<Week> weeks;
    List<Day> days;

    HomeViewModel model;
    RecyclerView weekRecycle;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CommunicateData) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        model = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        loadCourseData();

        weekRecycle = binding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        weekRecycle.setLayoutManager(linearLayoutManager);

        return root;
    }

    private void loadCourseData() {

        model.getAllCourses().observe(requireActivity(), new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable final List<Course> words) {
                // Update the cached copy of the words in the adapter.
                if(words!=null)
                    for(Course c: words)
                        if(c.isSelected())
                            selectedForEducationCourse = c;

                if(weeks!=null)
                    selectedForEducationCourse.setWeekList(weeks);

                if(days!=null&&weeks!=null) {
                    for(Day d: days){
                        selectedForEducationCourse.getWeekList().get(d.getWeekNumber()).addDay(d);
                    }
                }
                if(selectedForEducationCourse!=null&&weeks!=null&&days!=null)
                    refreshAdapter();
            }
        });


        model.getAllWeeks().observe(requireActivity(), new Observer<List<Week>>() {
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
                if(selectedForEducationCourse!=null&&weeks!=null&&days!=null)
                    refreshAdapter();
            }
        });

        model.getAllDays().observe(requireActivity(), new Observer<List<Day>>() {
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
                if(selectedForEducationCourse!=null&&weeks!=null&&days!=null)
                    refreshAdapter();
            }
        });
    }

    private void refreshAdapter() {
        HomeAdapter adapter = new HomeAdapter(requireContext(), selectedForEducationCourse.getWeekList());

        weekRecycle.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}