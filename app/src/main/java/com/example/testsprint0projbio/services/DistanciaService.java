package com.example.testsprint0projbio.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.utility.StepCounterManager;

public class DistanciaService extends Service {

    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private StepCounterManager stepCounterManager;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("StepData", Context.MODE_PRIVATE);

        // Inicializa StepCounterManager
        stepCounterManager = new StepCounterManager(this, null, null, null);
        stepCounterManager.registerSensorListener();

        // Configura el canal de notificaciones
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Muestra la notificación para mantener el servicio en primer plano
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Tracking Steps and Distance")
                .setContentText("The app is tracking your steps in the background.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stepCounterManager.unregisterSensorListener();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        NotificationChannel serviceChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
        }
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}
