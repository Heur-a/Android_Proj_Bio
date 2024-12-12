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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.testsprint0projbio.R;

public class LocationFragment extends Fragment {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private TextView distanciaTextView;
    private LocationFragmentListener listener;  // Listener para enviar la distancia

    // Definir la interfaz para la comunicación con la actividad
    public interface LocationFragmentListener {
        void onLocationDataChanged(String newLocation);  // Método para recibir los datos
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LocationFragmentListener) {
            listener = (LocationFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LocationFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grafica, container, false);

        distanciaTextView = view.findViewById(R.id.distanciaSensorTextView);

        BluetoothManager bluetoothManager = (BluetoothManager) requireContext().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            startScan();
        } else {
            Log.e("BLE", "Bluetooth no está habilitado.");
        }

        return view;
    }

    private void startScan() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        bluetoothLeScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);

                // Obtener el RSSI
                int rssi = result.getRssi();
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
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

                // Enviar la distancia calculada a la actividad a través del listener
                if (listener != null) {
                    listener.onLocationDataChanged(String.format("%.2f", distance) + " m");
                }

                // Mostrar la distancia en el TextView
                distanciaTextView.setText(String.format("%.2f", distance) + " m");
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
