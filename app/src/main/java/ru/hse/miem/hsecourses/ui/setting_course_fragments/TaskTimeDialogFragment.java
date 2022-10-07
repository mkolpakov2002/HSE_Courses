package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Date;

import nl.joery.timerangepicker.TimeRangePicker;
import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Task;

public class TaskTimeDialogFragment extends DialogFragment {

    final String LOG_TAG = "myLogs";
    private Context context;
    private OpenFragment listener2;

    TextView startTime;
    TextView endTime;

    TimeRangePicker timeRangePicker;
    LinearLayout startTimeLayout;
    LinearLayout endTimeLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.context = context;
            listener2 = (OpenFragment) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new MaterialAlertDialogBuilder(context);
        alertDialogBuilder.setTitle("Настройка времени занятия");
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_task, null);
        alertDialogBuilder.setView(v);

        if (getArguments() != null) {

            int dayNumber = getArguments().getInt("dayNumber");

            startTime = v.findViewById(R.id.start_time_text);

            endTime = v.findViewById(R.id.end_time_text);

            timeRangePicker = v.findViewById(R.id.picker);

            startTimeLayout = v.findViewById(R.id.start_task_layout);
            endTimeLayout = v.findViewById(R.id.stop_task_layout);


            startTime.setText(timeRangePicker.getStartTime().getHour()+":"+timeRangePicker.getStartTime().getMinute());

            endTime.setText(timeRangePicker.getEndTime().getHour()+":"+timeRangePicker.getEndTime().getMinute());

            timeRangePicker.setOnTimeChangeListener(new TimeRangePicker.OnTimeChangeListener() {
                @Override
                public void onStartTimeChange(@NonNull TimeRangePicker.Time time) {
                    startTime.setText(timeRangePicker.getStartTime().getHour()+":"+timeRangePicker.getStartTime().getMinute());
                }

                @Override
                public void onEndTimeChange(@NonNull TimeRangePicker.Time time) {
                    endTime.setText(timeRangePicker.getEndTime().getHour()+":"+timeRangePicker.getEndTime().getMinute());
                }

                @Override
                public void onDurationChange(@NonNull TimeRangePicker.TimeDuration timeDuration) {

                }
            });

            timeRangePicker.setOnDragChangeListener(new TimeRangePicker.OnDragChangeListener() {
                @Override
                public boolean onDragStart(@NonNull TimeRangePicker.Thumb thumb) {
                    if(thumb != TimeRangePicker.Thumb.BOTH) {
                        animate(thumb, true);
                    }
                    return  true;
                }

                @Override
                public void onDragStop(@NonNull TimeRangePicker.Thumb thumb) {
                    if(thumb != TimeRangePicker.Thumb.BOTH) {
                        animate(thumb, false);
                    }
                }
            });

            alertDialogBuilder.setPositiveButton(R.string.ok,  new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // on success
                    Task newTask = new Task();
                    Date startTime = timeRangePicker.getStartTime().getCalendar().getTime();
                    Date endTime = timeRangePicker.getEndTime().getCalendar().getTime();
                    newTask.setStartTime(startTime);
                    newTask.setEndTime(endTime);
                    newTask.setDayNumber(dayNumber);
                    Bundle b = new Bundle();
                    b.putSerializable("task", newTask);
                    getParentFragmentManager().setFragmentResult("requestKey", b);
                }
            });
            alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }

            });

        } else {
            //dismiss();
        }

        return alertDialogBuilder.create();
    }

    private void animate(TimeRangePicker.Thumb thumb, Boolean active) {
        LinearLayout activeView;
        LinearLayout inactiveView;
        int direction;
        float translationX;
        float alpha;

        if(thumb == TimeRangePicker.Thumb.START){
            activeView = startTimeLayout;
            inactiveView = endTimeLayout;
            direction = 1;
        } else {
            activeView = endTimeLayout;
            inactiveView = startTimeLayout;
            direction = -1;
        }

        if(active){
            translationX = (activeView.getMeasuredWidth() / 2f)*direction;
            alpha = 0f;
        } else {
            translationX = 0f;
            alpha = 1f;
        }


        activeView
                .animate()
                .translationX(translationX)
            .setDuration(300)
                .start();
        inactiveView
                .animate()
                .alpha(alpha)
            .setDuration(300)
                .start();
    }


}
