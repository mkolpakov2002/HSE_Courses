package ru.hse.miem.hsecourses.ui.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.ui.setting_course_fragments.CommunicateData;

public class SettingsFragment extends PreferenceFragmentCompat {

    private CommunicateData listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CommunicateData) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        // Регистрируем этот OnSharedPreferenceChangeListener
        SharedPreferences prefs = requireActivity().
                getApplicationContext().getSharedPreferences(requireActivity().getPackageName(), MODE_PRIVATE);
        prefs.registerOnSharedPreferenceChangeListener(this::onSharedPreferenceChanged);
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        // TODO Проверять общие настройки, ключевые параметры и изменять UI
        // или поведение приложения, если потребуется.
        listener.onRefresh();
    }
}