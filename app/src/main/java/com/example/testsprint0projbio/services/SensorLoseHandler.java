package com.example.testsprint0projbio.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.CountDownTimer;
import androidx.core.app.NotificationCompat;

/**
 * Class responsible for handling sensor node connection loss and restoration events.
 * This class provides mechanisms to notify users via notifications when a sensor node is lost or found.
 */
public class SensorLoseHandler {

    /** Tag for logging purposes. */
    private final String TAG = "SensorLoseHandler";

    /** Context of the application. */
    private final Context context;

    /** Notification manager to handle notifications. */
    private final NotificationManager notificationManager;

    /** Notification channel ID for sending notifications. */
    private final String NOTIFICATION_CHANNEL_ID;

    /** Threshold time in milliseconds to detect node loss. */
    private static final long LOST_TIME_THRESHOLD = 1000 * 30; // 30 seconds

    /** Countdown timer to monitor node connection status. */
    private CountDownTimer countDownTimer;

    /** Flag indicating whether the sensor node is lost. */
    private boolean isNodeLost = false;

    /**
     * Constructor for SensorLoseHandler.
     *
     * @param context The application context.
     * @param NOTIFICATION_CHANNEL_ID The notification channel ID to send notifications.
     */
    public SensorLoseHandler(Context context, String NOTIFICATION_CHANNEL_ID) {
        this.context = context;
        this.NOTIFICATION_CHANNEL_ID = NOTIFICATION_CHANNEL_ID;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Start monitoring the sensor node connection.
        startTimer();
    }

    /**
     * Starts the countdown timer to monitor node connection status.
     */
    private void startTimer() {
        countDownTimer = new CountDownTimer(LOST_TIME_THRESHOLD, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // No action needed on each tick.
            }

            @Override
            public void onFinish() {
                // Handle node loss when the timer finishes.
                handleNodeLost();
            }
        };
        countDownTimer.start();
    }

    /**
     * Resets the countdown timer, restarting the monitoring process.
     */
    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.start();
        }
    }

    /**
     * Handles the scenario when the sensor node is detected as lost.
     */
    private void handleNodeLost() {
        isNodeLost = true;
        sendLostNodeNotification();
        // Add additional logic to handle lost node, e.g., attempting reconnection.
    }

    /**
     * Handles the scenario when the sensor node is found again.
     */
    public void nodeFound() {
        if (isNodeLost) {
            isNodeLost = false;
            resetTimer();
            sendFoundNodeNotification();
        }
    }

    /**
     * Launches a notification to inform the user about the sensor node status.
     *
     * @param title The title of the notification.
     * @param message The message body of the notification.
     */
    private void launchNotification(String title, String message) {
        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }

    /**
     * Sends a notification to inform the user that the sensor node is lost.
     */
    private void sendLostNodeNotification() {
        launchNotification("Node Lost", "The sensor node connection is lost. Searching...");
    }

    /**
     * Sends a notification to inform the user that the sensor node has been found.
     */
    private void sendFoundNodeNotification() {
        launchNotification("Node Found", "The sensor node connection has been restored.");
    }
}
