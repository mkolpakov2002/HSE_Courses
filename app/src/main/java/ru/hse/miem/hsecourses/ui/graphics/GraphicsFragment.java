package ru.hse.miem.hsecourses.ui.graphics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ir.mahozad.android.PieChart;
import ru.hse.miem.hsecourses.courses.Course;
import ru.hse.miem.hsecourses.courses.Day;
import ru.hse.miem.hsecourses.databinding.FragmentGraphicsBinding;
import ru.hse.miem.hsecourses.graphics.GraphValue;
import ru.hse.miem.hsecourses.graphics.PopupBarChart;
import ru.hse.miem.hsecourses.ui.MainActivity;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData;

public class GraphicsFragment extends Fragment {

    private FragmentGraphicsBinding binding;

    PopupBarChart graph;

    TextView textViewHoursCount;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GraphicsViewModel graphicsViewModel =
                new ViewModelProvider(this).get(GraphicsViewModel.class);

        binding = FragmentGraphicsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        PieChart pieChartTime = binding.pieChartWeeks;
        //pieChartTime.setCenterLabel("");
//        pieChartTime.setGradientType(PieChart.GradientType.RADIAL);
//        pieChartTime.setLegendsIcon(PieChart.DefaultIcons.CIRCLE);
//        pieChartTime.setAnimationEnabled(true);
//        List<PieChart.Slice> resultTime = new ArrayList<>();
//        pieChartTime.setSlices(resultTime);




        Course course = listener.getCourse();

        List<GraphValue> values = new ArrayList<>();

        LocalDate today = LocalDate.now();

        DayOfWeek dayOfWeek = today.getDayOfWeek();

        //TODO
        List<Day> dayList = listener.getAllDays();
        double[] daysHoursCount = new double[dayList.size()];
        double totalCount = 0.0;
        for(int i = 0; i<dayList.size(); i++){
            totalCount += (dayList.get(i).getTasksTimeCount()/(1000*60*60d));
            daysHoursCount[i] = (dayList.get(i).getTasksTimeCount()/(1000*60*60d));
            GraphValue curr = new GraphValue(i, i, (int) (daysHoursCount[i])*100/24,
                    dayOfWeek.getValue()-1==dayList.get(i).getDayNumber(),
                    false);
            values.add(curr);
        }


        graph = binding.customBarchart;

        graph.setGraphValues(values);
        //graph.setTooltipBg(R.color.transparent);


        DecimalFormat decimalFormat = new DecimalFormat( "#.#" );
        String result = decimalFormat.format(totalCount);

        textViewHoursCount = binding.textViewHoursCount;
        textViewHoursCount.setText(result + " ч");



        //graphicsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}