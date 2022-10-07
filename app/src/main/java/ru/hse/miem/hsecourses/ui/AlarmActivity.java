package ru.hse.miem.hsecourses.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ru.hse.miem.hsecourses.R;

public class AlarmActivity extends AppCompatActivity {

    private static AlarmActivity inst;
    ImageView imageView;
    TextView title;
    TextView description;

    TextView timeAndData;

    Button closeButton;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
        mediaPlayer.start();

    }

    public static AlarmActivity instance() {
        return inst;
    }
}