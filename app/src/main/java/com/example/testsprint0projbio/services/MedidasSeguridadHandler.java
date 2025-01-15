package com.example.testsprint0projbio.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.testsprint0projbio.Activities.HomeActivity;
import com.example.testsprint0projbio.R;

public class MedidasSeguridadHandler {

    private static final String TAG = "MedidasSeguridadHandler";

    // Valores de límites
    private static final int LIMITE_PRECAUCION = 20;
    private static final int LIMITE_PELIGRO = 40;

    // Constantes de tiempo
    private static final long INTERVALO_PRECAUCION = 10 * 60 * 1000; // 10 minutos
    private static final long INTERVALO_PELIGRO = 5 * 60 * 1000; // 5 minutos

    private final Context context;
    private final NotificationManager notificationManager;
    private long ultimaNotificacionPrecaucion;
    private long ultimaNotificacionPeligro;

    public static final String NOTIFICACION_CHANNEL_ID = "ALERTA_SEGURIDAD";
    public static final String NOTIFICACION_CHANNEL_NAME = "Alertas de Seguridad";

    public MedidasSeguridadHandler(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.ultimaNotificacionPrecaucion = 0;
        this.ultimaNotificacionPeligro = 0;

        crearCanalNotificacion();
    }

    public void evaluarMedida(int valorMedida) {
        long ahora = System.currentTimeMillis();

        if (valorMedida >= LIMITE_PELIGRO) {
            if (ahora - ultimaNotificacionPeligro >= INTERVALO_PELIGRO) {
                enviarNotificacion("PELIGRO", "EL SENSOR HA DETECTADO NIVELES TÓXICOS DE OZONO" +
                        "\n\rSALGA DE LA ZONA INMEDIATAMENTE", 2);
                ultimaNotificacionPeligro = ahora;
            }
        } else if (valorMedida >= LIMITE_PRECAUCION) {
            if (ahora - ultimaNotificacionPrecaucion >= INTERVALO_PRECAUCION) {
                enviarNotificacion("Precaución", "El nivel del sensor indica un posible riesgo.", 1);
                ultimaNotificacionPrecaucion = ahora;
            }
        }
    }

    private void enviarNotificacion(String titulo, String mensaje, int idNotificacion) {
        Log.d(TAG, "Enviando notificación: " + titulo);

        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(context, NOTIFICACION_CHANNEL_ID)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setSmallIcon(R.drawable.logotipo_modooscuro)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(idNotificacion, notification);
    }

    private void crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICACION_CHANNEL_ID,
                    NOTIFICACION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notificaciones de alerta de seguridad.");
            notificationManager.createNotificationChannel(channel);
        }
    }
}
