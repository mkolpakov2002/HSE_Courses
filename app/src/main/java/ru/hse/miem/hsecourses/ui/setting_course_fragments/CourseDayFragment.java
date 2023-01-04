package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Day;

public class CourseDayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerViewDays;
    CourseDayAdapter adapter;
    int dayNumber;

    private CommunicateData listener2;
    private OpenFragment listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OpenFragment) context;
            listener2 = (CommunicateData) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    public CourseDayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_day, container, false);
        recyclerViewDays = view.findViewById(R.id.recyclerViewDays);
        recyclerViewDays.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new CourseDayAdapter(view.getContext(),
                listener2.getAllDays(),
                listener2.getAllTasks());

        getParentFragmentManager().setFragmentResultListener("requestKeyDialogClosed", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                int dayNumber = bundle.getInt("dayNumber", -1);
                Log.e("Course Day Fragment result", String.valueOf(dayNumber));
                if(dayNumber<=6&&dayNumber>=0)
                    adapter.updateData(listener2.getAllDays(), listener2.getAllTasks());
            }
        });

        getParentFragmentManager().setFragmentResultListener("textWarningDialog", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                if(bundle.getBoolean("isPositive", false))
                    listener.openNextFragment(null);
            }
        });

        adapter.setClickListener((view1, position) -> {
            Bundle b = new Bundle();
            b.putInt("dayNumber", position);
            Navigation.findNavController(view1).navigate(R.id.courseTimeReminderDialogFragment, b);
            dayNumber = position;
        });

        recyclerViewDays.setAdapter(adapter);
        return view;
    }

    public void onResume() {
        super.onResume();
        Log.e("onResume", "onResume called");
        adapter.updateData(listener2.getAllDays(), listener2.getAllTasks());
    }

}