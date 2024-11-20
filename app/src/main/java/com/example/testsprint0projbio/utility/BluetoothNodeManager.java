package com.example.testsprint0projbio.utility;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.testsprint0projbio.pojo.TramaIBeacon;

import java.util.List;
import java.util.Objects;

public class BluetoothNodeManager {
    private static final String ETIQUETA_LOG = ">>>>"; ///< Etiqueta per a logs

    private final Context context; ///< Context de l'aplicació o activitat
    private final BluetoothLeScanner scanner; ///< Escàner Bluetooth LE
    private ScanCallback scanCallback; ///< Callback per a manejar els resultats d'escaneig
    private boolean isScanning = false; ///< Estat del procés d'escaneig

    // Constructor
    public BluetoothNodeManager(Context context) {
        this.context = context;
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            this.scanner = adapter.getBluetoothLeScanner();
        } else {
            this.scanner = null;
        }
    }

    /**
     * @brief Inicia l'escaneig de dispositius Bluetooth LE.
     */
    @RequiresApi(api = Build.VERSION_CODES.S)
    public void startScanning(ScanCallback callback) {
        if (scanner == null) {
            Log.e(ETIQUETA_LOG, "startScanning(): Bluetooth no disponible.");
            Toast.makeText(context, "Bluetooth no está disponible.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(ETIQUETA_LOG, "startScanning(): Configurando callback escaneo.");

        this.scanCallback = callback;

        try {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_SCAN) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                Log.e(ETIQUETA_LOG, "startScanning(): Permisos de Bluetooth no concedidos.");
                Toast.makeText(context, "Permisos de Bluetooth no concedidos.", Toast.LENGTH_SHORT).show();
                return;
            }

            scanner.startScan(this.scanCallback);
            isScanning = true;
            Log.d(ETIQUETA_LOG, "startScanning(): Escaneo iniciado.");
            Toast.makeText(context, "Escaneo iniciado.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(ETIQUETA_LOG, "startScanning(): Error iniciando escaneo:  " + e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void startScanning() {
        startScanning(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                processScanResult(result);
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, "startScanning(): Resultados en lote encontrados.");
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.e(ETIQUETA_LOG, "startScanning(): Error en escaneo, Código de error: " + errorCode);
                Toast.makeText(context, "Error en escaneo. Código de error: " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * @brief Deté l'escaneig de dispositius Bluetooth LE.
     */
    @SuppressLint("MissingPermission")
    public void stopScanning() {
        if (scanner != null && isScanning) {
            scanner.stopScan(scanCallback);
            scanCallback = null;
            isScanning = false;
            Log.d(ETIQUETA_LOG, "stopScanning(): Escaneo detenido");
        }
    }

    /**
     * @param result El resultat de l'escaneig.
     * @brief Processa els resultats de l'escaneig.
     */
    @SuppressLint("MissingPermission")
    private void processScanResult(ScanResult result) {
        BluetoothDevice device = result.getDevice();
        byte[] scanBytes = Objects.requireNonNull(result.getScanRecord()).getBytes();

        Log.d(ETIQUETA_LOG, "Dispositiu detectat:");
        Log.d(ETIQUETA_LOG, "Nom: " + device.getName());
        Log.d(ETIQUETA_LOG, "Adreça: " + device.getAddress());
        Log.d(ETIQUETA_LOG, "RSSI: " + result.getRssi());

        // Processar dades del sensor
        TramaIBeacon iBeaconData = new TramaIBeacon(scanBytes);
        Log.d(ETIQUETA_LOG, "UUID: " + Utilidades.bytesToString(iBeaconData.getUUID()));
        Log.d(ETIQUETA_LOG, "Major: " + Utilidades.bytesToInt(iBeaconData.getMajor()));
        Log.d(ETIQUETA_LOG, "Minor: " + Utilidades.bytesToInt(iBeaconData.getMinor()));
    }

    /**
     * @param uuidString El UUID del dispositiu que es vol buscar.
     * @brief Cerca un dispositiu amb un UUID específic.
     */
    public void searchSpecificDevice(String uuidString, DeviceScanCallback callback) {
        Log.d(ETIQUETA_LOG, "searchSpecificDevice(): Iniciant cerca per UUID: " + uuidString);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            startScanning(new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    byte[] scanBytes = Objects.requireNonNull(result.getScanRecord()).getBytes();
                    TramaIBeacon iBeaconData = new TramaIBeacon(scanBytes);

                    String detectedUUID = Utilidades.bytesToString(iBeaconData.getUUID());
                    if (uuidString.equals(detectedUUID)) {
                        stopScanning();
                        Log.d(ETIQUETA_LOG, "Dispositiu trobat amb UUID: " + detectedUUID);

                        // Notificar mitjançant el callback
                        if (callback != null) {
                            callback.onDeviceFound(iBeaconData);
                        }
                    }
                }

                @Override
                public void onScanFailed(int errorCode) {
                    super.onScanFailed(errorCode);
                    Log.e(ETIQUETA_LOG, "searchSpecificDevice(): Error en escaneig. Codi d'error: " + errorCode);

                    // Notificar error mitjançant el callback
                    if (callback != null) {
                        callback.onScanFailed("Error en escaneig. Codi d'error: " + errorCode);
                    }
                }
            });
        } else {
            Log.e(ETIQUETA_LOG, "searchSpecificDevice(): Versió d'Android no compatible.");
            if (callback != null) {
                callback.onScanFailed("Versió d'Android no compatible.");
            }
        }
    }


    public interface DeviceScanCallback {
        void onDeviceFound(TramaIBeacon device);

        void onScanFailed(String errorMessage);
    }

}

