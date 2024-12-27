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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubicacion);

        // LOGO
        ImageButton logoButton = findViewById(R.id.logoGrafica);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioUbicacion);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaUbicacion);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, MapaActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.mapaUbicacion);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(UbicacionActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaUbicacion);

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
        ImageButton perfilButton = findViewById(R.id.iconAjustessUbicacion);

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

            if (rssi > 0 || rssi < -65) {
                Log.i("BLE", "RSSI fuera de rango: " + rssi);
                return;
            }


            double distance = calculateDistance(rssi, -59);
            String distanceText = String.format("%.2f m", distance);
            distanciaSensorTextView.setText(distanceText + "m");
            Log.i("BLE", "Distancia: " + distanceText);
        }
    };

    private double calculateDistance(int rssi, int txPower) {
        if (txPower == 0) return -1.0;
        double ratio = (double) rssi / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double environmentalFactor = 2.0;
            return Math.pow(10, (txPower - rssi) / (10 * environmentalFactor));
        }
    }


}

