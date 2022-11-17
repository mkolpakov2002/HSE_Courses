package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.CourseViewModel;
import ru.hse.miem.hsecourses.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectCourseFragment extends Fragment implements SelectCourseAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SelectCourseAdapter selectCourseAdapter;
    RecyclerView courseSelectRecyclerView;

    private Context context;

    private CommunicateData listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            this.context = context;
            listener = (CommunicateData) context;
        }
    }


    public SelectCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectCourseFragment newInstance(String param1, String param2) {
        SelectCourseFragment fragment = new SelectCourseFragment();
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
        View view = inflater.inflate(R.layout.fragment_course_select, container, false);
        courseSelectRecyclerView = view.findViewById(R.id.courseSelectRecycleView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        courseSelectRecyclerView.setLayoutManager(mLayoutManager);

        selectCourseAdapter = new SelectCourseAdapter(view.getContext(), listener.availableCourses(), listener.getCourse());
        selectCourseAdapter.setClickListener(this);
        courseSelectRecyclerView.setAdapter(selectCourseAdapter);

        return view;
    }

    @Override
    public void onItemClick(View view, int selectedId) {
        listener.setSelectedForEducationCourse(selectedId);

    }
}