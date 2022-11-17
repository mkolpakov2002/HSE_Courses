package ru.hse.miem.hsecourses.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.OpenFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PermissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PermissionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String messageDescription;
    String messageTitle;
    int messageIcon;

    MaterialButton openSettingsButton;
    MaterialButton cancelButton;

    private OpenFragment listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OpenFragment) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    public PermissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PermissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PermissionFragment newInstance(String param1, String param2) {
        PermissionFragment fragment = new PermissionFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle b = getArguments();
        if(b!=null){
            messageTitle = b.getString("messageTitleText", ":(");
            messageDescription = b.getString("messageDescriptionText", "Hmmm... Nothing there");
            messageIcon = b.getInt("messageIcon", R.drawable.earth);
        } else {
            messageTitle = ":(";
            messageDescription = "Hmmm... Nothing there";
            messageIcon = R.drawable.earth;
        }
        View v = inflater.inflate(R.layout.fragment_permission, container, false);
        openSettingsButton = v.findViewById(R.id.button8);
        cancelButton = v.findViewById(R.id.button7);
        openSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInstalledAppDetailsActivity(requireActivity());
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openNextFragment(null);
            }
        });

        TextView messageTitleTextView = v.findViewById(R.id.textView6);
        messageTitleTextView.setText(messageTitle);
        TextView messageDescriptionTextView = v.findViewById(R.id.textView9);
        messageDescriptionTextView.setText(messageDescription);
        ImageView messageImageView = v.findViewById(R.id.imageView5);
        messageImageView.setImageDrawable(AppCompatResources.getDrawable(v.getContext(), messageIcon));
        return v;
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(NotificationManagerCompat.from(requireContext()).areNotificationsEnabled())
            listener.openNextFragment(null);
    }
}