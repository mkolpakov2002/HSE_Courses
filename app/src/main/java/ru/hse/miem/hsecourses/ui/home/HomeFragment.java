package ru.hse.miem.hsecourses.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.hse.miem.hsecourses.Constants;
import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.courses.Module;
import ru.hse.miem.hsecourses.courses.Topic;
import ru.hse.miem.hsecourses.databinding.FragmentHomeBinding;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData;

public class HomeFragment extends Fragment implements HomeAdapter.ItemClickListener {

    private FragmentHomeBinding binding;

    private CommunicateData listener;

    Course selectedForEducationCourse;
    List<Module> modules;
    List<Topic> topics;
    List<Day> days;

    HomeViewModel model;
    RecyclerView weekRecycle;
    HomeAdapter adapter;

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

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        model = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        loadCourseData();

        weekRecycle = binding.recyclerView;

        if(listener.getAdapterMode()!=Constants.adapterHomeSimple){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            weekRecycle.setLayoutManager(linearLayoutManager);
        }

        return root;
    }

    private void loadCourseData() {

        model.getAllCourses().observe(requireActivity(), new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable final List<Course> words) {
                // Update the cached copy of the words in the adapter.
                if(words==null || words.isEmpty())
                    selectedForEducationCourse = new Course();

                if(words!=null)
                    for(Course c: words)
                        if(c.isSelected())
                            selectedForEducationCourse = c;

                if(modules !=null && words!=null)
                    selectedForEducationCourse.setModuleList(modules);

                if(topics!=null&& modules !=null && words!=null) {
                    for(Topic d: topics){
                        selectedForEducationCourse.getModuleList().get(d.getModuleNumber()).addTopic(d);
                    }
                }
                if(selectedForEducationCourse!=null&& modules !=null&&topics!=null && words!=null)
                    refreshAdapter();
            }
        });


        model.getAllModules().observe(requireActivity(), new Observer<List<Module>>() {
            @Override
            public void onChanged(@Nullable final List<Module> words) {
                // Update the cached copy of the words in the adapter.
                modules = new ArrayList<>();
                if(words!=null)
                    modules.addAll(words);


                if(selectedForEducationCourse!=null && words!=null)
                    selectedForEducationCourse.setModuleList(modules);


                if(topics!=null&&selectedForEducationCourse!=null && words!=null) {
                    for(Topic d: topics){
                        selectedForEducationCourse.getModuleList().get(d.getModuleNumber()).addTopic(d);
                    }
                }
                if(selectedForEducationCourse!=null&& modules !=null&&topics!=null && words!=null)
                    refreshAdapter();
            }
        });

        model.getAllTopics().observe(requireActivity(), new Observer<List<Topic>>() {
            @Override
            public void onChanged(@Nullable final List<Topic> words) {
                // Update the cached copy of the words in the adapter.
                topics = new ArrayList<>();
                if(words!=null){
                    topics.addAll(words);
                }

                if(selectedForEducationCourse!=null&& modules !=null && words!=null)
                    for(Topic d: topics){
                        selectedForEducationCourse.getModuleList().get(d.getModuleNumber()).addTopic(d);
                    }
                if(selectedForEducationCourse!=null&& modules !=null&&days!=null && words!=null)
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
            }
        });
    }

    private void refreshAdapter() {
        adapter = new HomeAdapter(requireContext(), selectedForEducationCourse.getModuleList(),
                listener.getAdapterMode());

        weekRecycle.setAdapter(adapter);

        adapter.setClickListener(this);

        weekRecycle.post(new Runnable() {
            @Override
            public void run() {
                weekRecycle.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(View view, Module selected) {
        Bundle b = new Bundle();
        b.putSerializable("selected", selected);
        NavHostFragment.findNavController(this).navigate(R.id.moduleFragment, b);
    }

}