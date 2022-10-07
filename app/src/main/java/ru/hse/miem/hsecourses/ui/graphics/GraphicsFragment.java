package ru.hse.miem.hsecourses.ui.graphics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import ir.mahozad.android.PieChart;
import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.databinding.FragmentGraphicsBinding;

public class GraphicsFragment extends Fragment {

    private FragmentGraphicsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GraphicsViewModel graphicsViewModel =
                new ViewModelProvider(this).get(GraphicsViewModel.class);

        binding = FragmentGraphicsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        PieChart pieChartTime = binding.pieChartDays;
        //pieChartTime.setCenterLabel("");
//        pieChartTime.setGradientType(PieChart.GradientType.RADIAL);
//        pieChartTime.setLegendsIcon(PieChart.DefaultIcons.CIRCLE);
//        pieChartTime.setAnimationEnabled(true);
//        List<PieChart.Slice> resultTime = new ArrayList<>();
//        pieChartTime.setSlices(resultTime);


        PieChart pieChartDays = binding.pieChartDays;

        //graphicsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}