package com.example.testsprint0projbio.Activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.testsprint0projbio.R;

public class LocationActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private TextView distanciaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Inicializa el TextView
        distanciaTextView = findViewById(R.id.distanciaSensorMovil);

        // Inicializar Bluetooth
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            startScan();
        } else {
            Log.e("BLE", "Bluetooth no está habilitado.");
        }
    }

    private void startScan() {
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
        bluetoothLeScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);

                // Obtener el RSSI
                int rssi = result.getRssi();
                if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                // Filtrar valores de RSSI entre 0 y -65
                if (rssi > 0 || rssi < -65) {
                    Log.i("BLE", "RSSI fuera de rango: " + rssi);
                    return; // Ignorar este resultado
                }

                String deviceName = result.getDevice().getName();
                double distance = calculateDistance(rssi, -59); // Ajusta el valor de referencia

                Log.i("BLE", "Dispositivo: " + deviceName + ", RSSI: " + rssi + ", Distancia: " + distance + "m");
                // Mostrar la distancia en el TextView
                runOnUiThread(() -> distanciaTextView.setText("Distancia: " + String.format("%.2f", distance) + " m"));
            }
        });
    }

    // Método robusto y preciso para calcular la distancia
    private double calculateDistance(int rssi, int txPower) {
        if (txPower == 0) {
            return -1.0; // No se puede calcular la distancia
        }
        // Calcular la distancia basada en RSSI y Tx Power calibrado
        double distance;
        double ratio = (double) rssi / txPower;
        if (ratio < 1.0) {
            distance = Math.pow(ratio, 10);
        } else {
            double environmentalFactor = 2.0; // Ajustable según el entorno
            distance = Math.pow(10, (txPower - rssi) / (10 * environmentalFactor));
        }
        // Limitar la distancia a dos decimales
        return Math.round(distance * 100.0) / 100.0;
    }
}
