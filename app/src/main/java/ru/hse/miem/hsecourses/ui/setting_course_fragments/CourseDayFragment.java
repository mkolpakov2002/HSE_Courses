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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseDayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerViewDays;
    CourseDayAdapter adapter;
    int dayNumber;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseDayFragment newInstance(String param1, String param2) {
        CourseDayFragment fragment = new CourseDayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                //Toast.makeText(requireContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewDays.setAdapter(adapter);

        return view;
    }

}