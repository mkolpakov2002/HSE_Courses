package ru.hse.miem.hsecourses.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.OpenFragment;

public class TextDialogFragment extends DialogFragment {

    final String LOG_TAG = "myLogs";
    private CommunicateData listener;
    private OpenFragment listener2;
    private Context context;

    TextView messageTextView;

    private boolean isWarningMode;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.context = context;
            listener = (CommunicateData) context;
            listener2 = (OpenFragment) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new MaterialAlertDialogBuilder(context);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_text_info, null);
        alertDialogBuilder.setView(v);

        messageTextView = v.findViewById(R.id.dialogMessage);

        if (getArguments() != null) {

            isWarningMode = getArguments().getBoolean("isWarningMode", false);

            messageTextView = v.findViewById(R.id.dialogMessage);

            messageTextView.setText(getArguments().getString("messageText", getString(R.string.error_msg)));

            alertDialogBuilder.setPositiveButton("Продолжить",  new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // on success
                    if(isWarningMode){
                        Bundle b = new Bundle();
                        b.putBoolean("isPositive", true);
                        getParentFragmentManager().setFragmentResult("textWarningDialog", b);
                    }
                }
            });


            if(isWarningMode){
                ImageView warningIcon = v.findViewById(R.id.imageViewWarning);
                warningIcon.setVisibility(View.VISIBLE);

                alertDialogBuilder.setNegativeButton("Отмена",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // on cancel
                        Bundle b = new Bundle();
                        b.putBoolean("isPositive", false);
                        getParentFragmentManager().setFragmentResult("textWarningDialog", b);
                    }
                });
            }


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

}
