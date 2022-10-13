package ru.hse.miem.hsecourses.broadcasts;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ru.hse.miem.hsecourses.App;
import ru.hse.miem.hsecourses.BuildConfig;
import ru.hse.miem.hsecourses.R;
import ru.hse.miem.hsecourses.ui.AlarmActivity;
import ru.hse.miem.hsecourses.ui.MainActivity;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, BuildConfig.APPLICATION_ID)
                .setSmallIcon(R.drawable.ic_splash)
                .setContentTitle("Foxandroid Alarm Manager")
                .setContentText("Subscribe for android related content")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());


    }
}