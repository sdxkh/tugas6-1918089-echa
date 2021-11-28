package com.example.tugas6;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class NotifHandler extends Worker {
    public NotifHandler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        showNotif();
        return null;
    }
    public static void periodicWorkRequest() {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(NotifHandler.class, 5, TimeUnit.SECONDS)
                .setInitialDelay(2, TimeUnit.SECONDS)
                .setConstraints(setCons())
                .addTag("periodic")
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork("periodic", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);

    }

    //notifikasi
    public void showNotif(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(getApplicationContext(), "12")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Works")
                .setContentText("Remaining deadline in 2 days...")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1, notificationCompat.build());
    }

    public static Constraints setCons(){
        Constraints constraints = new Constraints.Builder()
                .build();
        return constraints;
    }
}
