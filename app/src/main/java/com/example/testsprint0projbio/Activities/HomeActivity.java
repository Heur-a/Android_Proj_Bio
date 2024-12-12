package com.example.testsprint0projbio.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.services.MedidasSensorHandlerService;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 123; // You can define a custom request code if needed

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Check and request POST_NOTIFICATIONS permission
        checkAndRequestNotificationPermission();

        // Set up "botonGrafica" to go to GraficaActivity
        ImageButton botonGrafica = findViewById(R.id.botonGrafica); // Ensure this ID matches the one in your XML
        botonGrafica.setOnClickListener(v -> {
            // Redirect to GraficaActivity
            startActivity(new Intent(HomeActivity.this, GraficaActivity.class));
        });

    }

    // ActivityResultLauncher to handle the result of permission request
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    if (isGranted) {
                        // Permission granted, start the service
                        startMeasurementService();
                    } else {
                        // Permission denied, show a Toast
                        Toast.makeText(HomeActivity.this, "Permission denied. Measurement service cannot be started.", Toast.LENGTH_LONG).show();
                    }
                }
            });

    /**
     * This method checks if the POST_NOTIFICATIONS permission is granted,
     * and requests it if not granted.
     */
    private void checkAndRequestNotificationPermission() {
        // For Android 13+ (API 33) to check POST_NOTIFICATIONS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // If permission is not granted, request it
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            } else {
                // Permission granted, start the service
                startMeasurementService();
            }
        } else {
            // For devices below Android 13, permission is not required, start the service
            startMeasurementService();
        }
    }

    /**
     * Starts the foreground service for measurements.
     */
    private void startMeasurementService() {
        // Start the foreground service
        Intent serviceIntent = new Intent(this, MedidasSensorHandlerService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }
}
