package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


        // data to populate the RecyclerView with
        List<Day> daysList;
        daysList = listener2.getAllDays();

        adapter = new CourseDayAdapter(view.getContext(), daysList);

        getParentFragmentManager().setFragmentResultListener("requestKeyDialogClosed", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                int dayNumber = bundle.getInt("dayNumber", -1);
                if(dayNumber<=6&&dayNumber>=0)
                    adapter.updateDay(dayNumber);
            }
        });

        getParentFragmentManager().setFragmentResultListener("textWarningDialog", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                if(bundle.getBoolean("isPositive", false))
                    listener.openNextFragment(null);
            }
        });

        adapter.setClickListener(new CourseDayAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle b = new Bundle();
                b.putInt("dayNumber", position);
                Navigation.findNavController(view).navigate(R.id.courseTimeReminderDialogFragment, b);
                dayNumber = position;
            }
        });

        recyclerViewDays.setAdapter(adapter);

        return view;
    }

}