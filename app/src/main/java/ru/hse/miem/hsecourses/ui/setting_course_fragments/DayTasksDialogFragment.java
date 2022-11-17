package ru.hse.miem.hsecourses.ui.setting_course_fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Task;

public class DayTasksDialogFragment extends DialogFragment implements DayTasksAdapter.onDeleteClickedListener {
    final String LOG_TAG = "myLogs";
    private Context context;
    private CommunicateData listener;
    List<Task> dayTaskList;
    TextView textViewIsAnyReminder;
    int selectedDayNumber;
    MaterialButton addNewTask;
    RecyclerView recyclerViewTasks;

    DayTasksAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.context = context;
            listener = (CommunicateData) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new MaterialAlertDialogBuilder(context);
        alertDialogBuilder.setTitle("Настройка времени");
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_day_tasks, null);
        alertDialogBuilder.setView(v);

        if (getArguments() != null) {

            getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                    Task newTask = (Task) bundle.getSerializable("task");
                    dayTaskList.add(0, newTask);
                    adapter.notifyItemInserted(0);
                    onRefresh();
                }
            });

            selectedDayNumber = getArguments().getInt("dayNumber");
            dayTaskList = listener.getTasksByDayNumber(selectedDayNumber);
            textViewIsAnyReminder = v.findViewById(R.id.textView);

            addNewTask = v.findViewById(R.id.buttonAddTimeReminder);
            addNewTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle b = new Bundle();
                    b.putInt("dayNumber", selectedDayNumber);
                    Navigation.findNavController(requireParentFragment().requireView()).navigate(R.id.taskTimeDialogFragment, b);
                }
            });

            recyclerViewTasks = v.findViewById(R.id.tasksList);
            recyclerViewTasks.setLayoutManager(new LinearLayoutManager(context));
            adapter = new DayTasksAdapter(v.getContext(), dayTaskList);
            adapter.setOnDeleteClickedListener(this);
            recyclerViewTasks.setAdapter(adapter);
            onRefresh();
            alertDialogBuilder.setPositiveButton(R.string.ok,  new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // on success
                    listener.saveTaskToDay(selectedDayNumber, dayTaskList);
                    Bundle b = new Bundle();
                    b.putInt("dayNumber", selectedDayNumber);
                    getParentFragmentManager().setFragmentResult("requestKeyDialogClosed", b);
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
            dismiss();
        }

        return alertDialogBuilder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    void onRefresh(){
        if(dayTaskList.size()>0){
            textViewIsAnyReminder.setVisibility(View.GONE);
        } else {
            textViewIsAnyReminder.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
    }

    @Override
    public void deleteClicked() {
        onRefresh();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }
}