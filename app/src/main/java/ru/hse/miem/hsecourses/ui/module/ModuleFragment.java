package ru.hse.miem.hsecourses.ui.module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.stream.Collectors;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Module;
import ru.hse.miem.hsecourses.courses.Topic;
import ru.hse.miem.hsecourses.ui.MainActivity;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModuleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModuleFragment extends Fragment implements ModuleItemsAdapter.ItemClickListener {

    private CommunicateData listener;
    Module selectedModule;
    MaterialButton backButton;

    RecyclerView moduleItemsRecycle;
    ModuleItemsAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int s;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CommunicateData) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }


    public ModuleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModuleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModuleFragment newInstance(String param1, String param2) {
        ModuleFragment fragment = new ModuleFragment();
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

        ModuleFragment fragment = this;
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                NavHostFragment.findNavController(fragment).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b = getArguments();
        s = 0;
        if(b!=null)
            s = b.getInt("selected", 0);
        selectedModule = listener.getAllModules().get(s);

        View root = inflater.inflate(R.layout.fragment_module, container, false);

        moduleItemsRecycle = root.findViewById(R.id.recyclerViewTopic);

        ExtendedFloatingActionButton extendedFloatingActionButton
                = root.findViewById(R.id.floatingActionButton);
        if(!selectedModule.isEnded()){
            extendedFloatingActionButton.setText(R.string.mark_completed);
            extendedFloatingActionButton.setIcon(AppCompatResources.getDrawable(
                    root.getContext(), R.drawable.ic_baseline_check_circle_outline_24));
        } else {
            extendedFloatingActionButton.setText(getString(R.string.mark_as_unfulfilled));
            extendedFloatingActionButton.setIcon(AppCompatResources.getDrawable(
                    root.getContext(), R.drawable.ic_baseline_clear_24));
        }
        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedModule.isEnded()){
                    extendedFloatingActionButton.setText(getString(R.string.mark_as_unfulfilled));
                    selectedModule.setEnded(true);
                    listener.setModuleEnded(s);
                    extendedFloatingActionButton.setIcon(AppCompatResources.getDrawable(
                            root.getContext(), R.drawable.ic_baseline_clear_24));

                } else {
                    extendedFloatingActionButton.setText(R.string.mark_completed);
                    selectedModule.setEnded(false);
                    listener.setModuleNotEnded(s);
                    extendedFloatingActionButton.setIcon(AppCompatResources.getDrawable(
                            root.getContext(), R.drawable.ic_baseline_check_circle_outline_24));
                }


            }
        });

        MaterialToolbar topAppBar = root.findViewById(R.id.topAppBar);

        ((MainActivity)requireActivity()).setSupportActionBar(topAppBar);
        topAppBar.setNavigationIcon(AppCompatResources.getDrawable(
                root.getContext(), R.drawable.ic_baseline_arrow_back_ios_24));
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).popBackStack();
            }
        });


        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshAdapter();
    }

    private void refreshAdapter() {
        adapter = new ModuleItemsAdapter(requireContext(),
                listener.getAllTopics().stream().filter(t -> t.getModuleNumber()==
                        selectedModule.getModuleNumber()).collect(Collectors.toList()));
        moduleItemsRecycle.setAdapter(adapter);
        adapter.setClickListener(this);
    }


    @Override
    public void onItemClick(View view, Topic selected) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://courses.openedu.urfu.ru/"));
        startActivity(browserIntent);
    }
}