package com.example.testsprint0projbio.utility;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.testsprint0projbio.Activities.DatosMentiraActivity;
import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.api.MedicionService;
import com.example.testsprint0projbio.api.OzoneApiClient;
import com.example.testsprint0projbio.pojo.Medicion;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasurementService extends Service {

    private static final long INTERVAL = 15000; // 15 segons
    private static final String NOTIFICATION_CHANNEL_ID = "54321";
    private static final int NOTIFICATION_ID = 34;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Random random = new Random();
    private boolean isRunning = false;

    private OzoneApiClient apiClient;
    private MedicionService medicionService;

    @Override
    public void onCreate() {
        super.onCreate();
        apiClient = OzoneApiClient.getInstance(this);
        medicionService = apiClient.createService(MedicionService.class);

        // Crea el canal de notificacions
        createNotificationChannel();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String uuid = intent.getStringExtra("UUID");

        Intent notificationIntent = new Intent(this, DatosMentiraActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        // Crea una notificació
        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Ozone Manager Sensor")
                .setContentText("Ozone está recabando medidas del sensor en segundo plano")
                .setSmallIcon(R.drawable.logotipo_modooscuro)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();

        // Inicia el servei en primer pla
        startForeground(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
                        | ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
                        | ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        );

        if (uuid != null) {
            isRunning = true;
            startSendingMeasurements(uuid);
        }
        return START_STICKY;
    }

    /**
     * Crea un canal de notificacions per al servei
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Ozone Sensor Service";
            String channelDescription = "Gestió del servei de sensors d'ozó";
            int importance = android.app.NotificationManager.IMPORTANCE_LOW;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);

            android.app.NotificationManager notificationManager = getSystemService(android.app.NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // No binding per aquest servei
    }

    private void startSendingMeasurements(String uuid) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!isRunning) return;

                // Genera valors aleatoris entre 0 i 2
                float randomValue = random.nextFloat() * 2;
                float randomLocX = random.nextFloat() * 2;
                float randomLocY = random.nextFloat() * 2;
                long gasId = 1; // Identificador d'exemple, es pot canviar

                // Crea l'objecte de mesura
                Medicion measurement = new Medicion(randomValue, randomLocX, randomLocY, gasId, uuid);

                // Envia la mesura a l'API
                medicionService.createMeasurement(measurement).enqueue(new Callback<okhttp3.ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<okhttp3.ResponseBody> call, @NonNull Response<okhttp3.ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG,"Mesura enviada correctament: " + measurement);
                        } else {
                            Log.d(TAG,"No s'ha pogut enviar la mesura: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<okhttp3.ResponseBody> call, @NonNull Throwable t) {
                        Log.d(TAG,"Error enviant la mesura: " + t.getMessage());
                    }
                });

                // Programa l'execució següent
                handler.postDelayed(this, INTERVAL);
            }
        });
    }
}
