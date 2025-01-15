package com.example.testsprint0projbio.Activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.utility.StepCounterManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.List;

public class UbicacionActivity extends AppCompatActivity {

    private StepCounterManager stepCounterManager;
    private TextView stepsTextView;
    private TextView distanceTextView;
    private TextView goalTextView;
    private TextView distanciaSensorTextView;
    private EditText goalInputEditText;
    private Button updateGoalButton;
    private LinearLayout popupLayout;
    private Button updateGoalPopupButton;
    private Button cancelPopupButton;
    private PieChart pieChart;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;

    private MapView mapView;
    Drawable icon;
    Button button4;
    Button button3;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubicacion);

        Configuration.getInstance().setUserAgentValue(getPackageName());

        // Configuración del mapa
        mapView = findViewById(R.id.mapView2);
        mapView.setMultiTouchControls(true);

        // Establecer una ubicación inicial
        GeoPoint startPoint = new GeoPoint(38.996076129249644, -0.1656746914044447);
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(startPoint);

        // Crear un marcador
        // Obtener la ubicación del usuario y agregar un marcador
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        GeoPoint userLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                        org.osmdroid.views.overlay.Marker userMarker = new org.osmdroid.views.overlay.Marker(mapView);
                        userMarker.setPosition(userLocation);
                        userMarker.setTitle("Tu ubicación");
                        mapView.getOverlays().add(userMarker);
                        mapView.getController().setCenter(userLocation);
                    }
                });

        button4 = findViewById(R.id.button4);
        button3 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button2);


        // LOGO
        ImageButton logoButton = findViewById(R.id.logoGrafica);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioWEB);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaWEB);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, DatosSensorActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.mapaUbicacion);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaWEB);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.iconHomeUbicacion);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustessWEB);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, AjustesActivity.class);
            startActivity(intent);
        });

        // Inicializar vistas y componentes
        stepsTextView = findViewById(R.id.stepsTextView);
        distanceTextView = findViewById(R.id.distanceTextView);
        goalTextView = findViewById(R.id.goalTextView);
        distanciaSensorTextView = findViewById(R.id.distanciaSensorTextView);
        goalInputEditText = findViewById(R.id.goalInputEditText);
        updateGoalButton = findViewById(R.id.updateGoalButton);
        popupLayout = findViewById(R.id.popupLayout);
        updateGoalPopupButton = findViewById(R.id.updateGoalPopupButton);
        cancelPopupButton = findViewById(R.id.cancelPopupButton);
        pieChart = findViewById(R.id.pieChart);

        stepCounterManager = new StepCounterManager(this, stepsTextView, distanceTextView, goalTextView);
        stepCounterManager.registerSensorListener();

        updateGoalButton.setOnClickListener(v -> popupLayout.setVisibility(View.VISIBLE));

        updateGoalPopupButton.setOnClickListener(v -> {
            stepCounterManager.updateTargetFromInput(goalInputEditText);
            popupLayout.setVisibility(View.GONE);
            updatePieChart();
        });

        cancelPopupButton.setOnClickListener(v -> popupLayout.setVisibility(View.GONE));

        updatePieChart();
        initializeBluetooth();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stepCounterManager.unregisterSensorListener();
        if (bluetoothLeScanner != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            bluetoothLeScanner.stopScan(scanCallback);
        }
    }

    private void updatePieChart() {
        int totalSteps = stepCounterManager.getTotalSteps();
        int goal = stepCounterManager.getGoal();

        if (goal == 0) {
            goalTextView.setText("Set a goal first");
            return;
        }

        if (totalSteps >= goal) {
            goalTextView.setText("Objetivo Conseguido");
            totalSteps = goal;
        } else {
            goalTextView.setText(goal + " pasos");
        }

        float progress = totalSteps;
        float remaining = Math.max(goal - progress, 0);

        PieDataSet dataSet = new PieDataSet(List.of(
                new PieEntry(progress, "Steps"),
                new PieEntry(remaining, "Remaining")
        ), "");

        // Define una lista de colores personalizados
        ArrayList<Integer> customColors = new ArrayList<>();
        customColors.add(Color.parseColor("#C0B69F")); // Beige oscuro
        customColors.add(Color.parseColor("#888888")); // Gris oscuro

// Asigna los colores al conjunto de datos
        dataSet.setColors(customColors);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void initializeBluetooth() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager != null ? bluetoothManager.getAdapter() : null;

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            startScan();
        } else {
            Log.e("BLE", "Bluetooth no está habilitado.");
        }
    }

    private void startScan() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        bluetoothLeScanner.startScan(scanCallback);
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            int rssi = result.getRssi();
            if (ActivityCompat.checkSelfPermission(UbicacionActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            if (rssi > 0 || rssi < -100) {
                Log.i("BLE", "RSSI fuera de rango: " + rssi);
                return;
            }

            int proximityCategory = getProximityCategory(rssi);
            String proximityText = "";
            switch (proximityCategory) {
                case 0:
                    proximityText = "Cerca";
                    button4.setBackgroundColor(Color.parseColor("#87A08B"));
                    button3.setBackgroundColor(Color.parseColor("#87A08B"));
                    button2.setBackgroundColor(Color.parseColor("#87A08B"));
                    break;
                case 1:
                    proximityText = "Media distancia";
                    button4.setBackgroundColor(Color.parseColor("#c9b892"));
                    button3.setBackgroundColor(Color.parseColor("#87A08B"));
                    button2.setBackgroundColor(Color.parseColor("#87A08B"));
                    break;
                case 2:
                    proximityText = "Lejos";
                    button4.setBackgroundColor(Color.parseColor("#c9b892"));
                    button3.setBackgroundColor(Color.parseColor("#c9b892"));
                    button2.setBackgroundColor(Color.parseColor("#87A08B"));
                    break;
            }

            distanciaSensorTextView.setText(proximityText);
            Log.i("BLE", "Proximidad: " + proximityText + ", RSSI: " + rssi);
        }
    };

    /**
     * Clasifica el RSSI en categorías de proximidad.
     * @param rssi Valor de la señal RSSI.
     * @return 0 si está cerca, 1 si está a media distancia, 2 si está lejos.
     */
    private int getProximityCategory(int rssi) {
        if (rssi >= -45) {
            return 0; // Cerca
        } else if (rssi >= -75) {
            return 1; // Media distancia
        } else {
            return 2; // Lejos
        }
    }
}

