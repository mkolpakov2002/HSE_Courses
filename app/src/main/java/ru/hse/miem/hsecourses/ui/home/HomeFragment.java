package ru.hse.miem.hsecourses.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import ru.hse.miem.hsecourses.Constants;
import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.courses.Module;
import ru.hse.miem.hsecourses.ui.MainActivity;
import ru.hse.miem.hsecourses.ui.MainViewModel;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData;

public class HomeFragment extends Fragment implements HomeAdapter.ItemClickListener {


    private CommunicateData listener;
    List<Module> moduleList;

    MainViewModel model;
    RecyclerView weekRecycle;
    HomeAdapter adapter;

    MaterialCardView progressCard;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CommunicateData) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


        weekRecycle = root.findViewById(R.id.recyclerView);

        progressCard = root.findViewById(R.id.progress_circular);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());

        if(listener.getAdapterMode()!=Constants.adapterHomeSimple){
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
        }

        weekRecycle.setLayoutManager(linearLayoutManager);

        loadCourseData();

        weekRecycle.post(new Runnable() {
            @Override
            public void run() {
                weekRecycle.smoothScrollToPosition(0);
            }
        });


//        MaterialToolbar topAppBar = root.findViewById(R.id.topAppBar);
//
//        topAppBar.inflateMenu(R.menu.top_app_bar);
//
//        ((MainActivity)requireActivity()).setSupportActionBar(topAppBar);
//
//        topAppBar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
//
//            /**
//             * This method will be invoked when a menu item is clicked if the item itself did
//             * not already handle the event.
//             *
//             * @param item {@link MenuItem} that was clicked
//             * @return <code>true</code> if the event was handled, <code>false</code> otherwise.
//             */
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.settings_menu) {
//                    Navigation.findNavController(root).navigate(R.id.navigation_settings);
//                }
//                return false;
//            }
//        });


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshAdapter();
    }

    private void loadCourseData() {
        if(listener==null)
            listener = (CommunicateData) requireContext();
        LiveData<List<Module>> liveData = listener.getModel().getAllModules();

        liveData.observe(requireActivity(), new Observer<List<Module>>() {
            @Override
            public void onChanged(@Nullable List<Module> value) {
                moduleList = value;
                refreshAdapter();
            }
        });
    }

    private void refreshAdapter() {

        if(moduleList == null || moduleList.isEmpty()){
            progressCard.setVisibility(View.VISIBLE);
        } else {
            progressCard.setVisibility(View.GONE);
            adapter = new HomeAdapter(requireContext(), moduleList,
                    listener.getAdapterMode());

            weekRecycle.setAdapter(adapter);

            adapter.setClickListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int selected) {
        if(selected>0 && !moduleList.get(selected).isUnlocked() && listener.getAdapterMode().equalsIgnoreCase(Constants.adapterHomeSimple)){
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
            builder.setMessage("Этот модуль пока впереди. Пройдите предыдущий, чтобы открыть!")
                    .setCancelable(true)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Bundle b = new Bundle();
            b.putSerializable("selected", selected);
            NavHostFragment.findNavController(this).navigate(R.id.navigation_module, b);
        }
    }

}