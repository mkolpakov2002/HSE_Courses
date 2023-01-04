package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import static com.patrykandpatryk.vico.core.entry.EntryListExtensionsKt.entryModelOf;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Task;
import ru.hse.miem.hsecourses.graphics.GraphValue;
import ru.hse.miem.hsecourses.graphics.PopupBarChart;
import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LastPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LastPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    PopupBarChart graph;

    TextView textViewHoursCount;

    //ChartProgressBar equalDaysChartView;

    //ChartEntryModel chartEntryModel;

    public LastPageFragment() {
        // Required empty public constructor
    }

    CommunicateData listener;

    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            this.context = context;
            listener = (CommunicateData) context;
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LastPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LastPageFragment newInstance(String param1, String param2) {
        LastPageFragment fragment = new LastPageFragment();
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

        View view = inflater.inflate(R.layout.fragment_last_page, container, false);
        graph = view.findViewById(R.id.customBarchart);
        textViewHoursCount = view.findViewById(R.id.textViewHoursCount);

        showGraphicTaskCount();

        return view;
    }

    void showGraphicTaskCount(){
        List<GraphValue> values = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        List<Day> dayList = listener.getAllDays();
        List<Task> taskList = listener.getAllTasks();
        double[] daysHoursCount = new double[dayList.size()];
        double totalCount = 0.0;
        for(int i = 0; i<taskList.size(); i++){
            totalCount += (taskList.get(i).getTaskTimeCount()/(1000*60*60d));
            daysHoursCount[i] = (taskList.get(i).getTaskTimeCount()/(1000*60*60d));
            GraphValue curr = new GraphValue(i, i, (int) (daysHoursCount[i])*100/24,
                    dayOfWeek.getValue()-1==dayList.get(i).getDayNumber(),
                    false);
            values.add(curr);
        }

        DecimalFormat decimalFormat = new DecimalFormat( "#.#" );
        String result = decimalFormat.format(totalCount);
        graph.setGraphValues(values);
        textViewHoursCount.setText(result + " ч");
    }
}