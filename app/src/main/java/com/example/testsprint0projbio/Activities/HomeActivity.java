package com.example.testsprint0projbio.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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

        // LOGO
        ImageButton logoButton = findViewById(R.id.logoHOME);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioHOME);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaHOME);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DatosSensorActivity.class);
            startActivity(intent);
        });



        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaHOME);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.iconHomeHOME);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustessHOME);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AjustesActivity.class);
            startActivity(intent);
        });

        // Check and request POST_NOTIFICATIONS permission
        checkAndRequestNotificationPermission();

        // Set up "botonUbi" to go to UbicacionActivity
        ImageButton botonUbi = findViewById(R.id.iconoMapHOME); // Ensure this ID matches the one in your XML
        botonUbi.setOnClickListener(v -> {
            // Redirect to UbicacionActivity
            startActivity(new Intent(HomeActivity.this, UbicacionActivity.class));
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
