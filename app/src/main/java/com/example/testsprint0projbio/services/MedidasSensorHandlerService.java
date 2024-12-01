package com.example.testsprint0projbio.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.testsprint0projbio.api.LocalStorageManager;
import com.example.testsprint0projbio.api.MedicionService;
import com.example.testsprint0projbio.api.NodeService;
import com.example.testsprint0projbio.api.OzoneApiClient;
import com.example.testsprint0projbio.pojo.Medicion;
import com.example.testsprint0projbio.pojo.Node;
import com.example.testsprint0projbio.pojo.NodeResponse;
import com.example.testsprint0projbio.pojo.TramaIBeacon;
import com.example.testsprint0projbio.utility.BluetoothNodeManager;
import com.example.testsprint0projbio.utility.Utilidades;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A background service that scans for Bluetooth LE devices, retrieves node info if needed,
 * and sends data to a REST server if a matching UUID is found.
 */
@RequiresApi(api = Build.VERSION_CODES.S)
public class MedidasSensorHandlerService extends Service {

    private static final String TAG = "BluetoothScanService"; ///< Log tag
    private static final long SCAN_PERIOD = 10000; ///< Scan period in milliseconds
    private MedicionService medicionService;

    private BluetoothNodeManager bluetoothNodeManager; ///< Manages Bluetooth scanning
    private Handler handler; ///< Handler for timing tasks
    private LocalStorageManager localStorageManager; ///< Manages app's shared preferences
    private NodeService nodeService; ///< API service for node operations

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created.");

        // Inicialitza el BluetoothNodeManager i LocalStorageManager
        bluetoothNodeManager = new BluetoothNodeManager(this);
        localStorageManager = new LocalStorageManager(this);

        // Obtén una instància del client de l'API
        OzoneApiClient ozoneApiClient = OzoneApiClient.getInstance(this);

        // Inicialitza NodeService i MedicionHandler usant OzoneApiClient
        nodeService = ozoneApiClient.createService(NodeService.class);
        medicionService = ozoneApiClient.createService(MedicionService.class);

        // Inicialitza Handler (si és necessari)
        handler = new Handler();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started.");
        checkOrFetchNode();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bluetoothNodeManager.stopScanning();
        Log.d(TAG, "Service destroyed. Scanning stopped.");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // This service does not support binding
    }

    /**
     * Checks if a node is stored locally. If not, fetches the node from the API.
     */
    private void checkOrFetchNode() {
        Node storedNode = localStorageManager.getNode();
        if (storedNode == null) {
            Log.d(TAG, "No stored node found. Fetching from API...");
            fetchNodeFromApi();
        } else {
            Log.d(TAG, "Stored node found: " + storedNode.getUuid());
            startScanning();
        }
    }

    /**
     * Fetches the node from the API and saves it locally if successful.
     */
    private void fetchNodeFromApi() {
        nodeService.getNodeById().enqueue(new Callback<NodeResponse>() {
            @Override
            public void onResponse(@NonNull Call<NodeResponse> call, @NonNull Response<NodeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String uuid = response.body().getUuid();
                    Node fetchedNode = new Node(uuid);
                    localStorageManager.saveNode(fetchedNode);
                    Log.d(TAG, "Node fetched and saved: " + fetchedNode.getUuid());
                    startScanning();
                } else {
                    Log.e(TAG, "Failed to fetch node. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NodeResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching node: " + t.getMessage());
            }
        });
    }

    /**
     * Starts scanning for Bluetooth LE devices.
     */
    private void startScanning() {
        handler.postDelayed(() -> {
            bluetoothNodeManager.stopScanning();
            Log.d(TAG, "Scan period finished. Restarting scan.");
            startScanning();
        }, SCAN_PERIOD);

        bluetoothNodeManager.startScanning(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                processScanResult(result);
            }

            @Override
            public void onBatchScanResults(java.util.List<ScanResult> results) {
                super.onBatchScanResults(results);
                for (ScanResult result : results) {
                    processScanResult(result);
                }
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.e(TAG, "Bluetooth scan failed. Error code: " + errorCode);
            }
        });
    }

    /**
     * Processes a Bluetooth LE scan result.
     *
     * @param result The scan result to process.
     */
    private void processScanResult(ScanResult result) {
        try {
            byte[] scanBytes = Objects.requireNonNull(result.getScanRecord()).getBytes();
            TramaIBeacon iBeaconData = new TramaIBeacon(scanBytes);

            // Extract UUID, Major, and Minor from the iBeacon message
            String detectedUUID = Utilidades.bytesToString(iBeaconData.getUUID());
            int major = Utilidades.bytesToInt(iBeaconData.getMajor());

            Log.d(TAG, "Device detected: UUID=" + detectedUUID + ", Major=" + major);

            // Get the stored UUID from SharedPreferences
            String storedUUID = localStorageManager.getNode() != null ? localStorageManager.getNode().getUuid() : null;

            if (storedUUID != null && storedUUID.equals(detectedUUID)) {
                Log.d(TAG, "Matching UUID found: " + detectedUUID);
                //Get location
                Location location = getCurrentLocation();
                //Create new Medicion
                Medicion medicion = new Medicion(location, major,1, detectedUUID);

                // Send data to REST server
                sendDataToServer(medicion);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing scan result: " + e.getMessage());
        }
    }

    /**
     * Sends the UUID and Major values to the REST server.
     *
     * @param medicion  The measurement to send.
     */
    private void sendDataToServer(Medicion medicion) {
        // Placeholder for REST API call
        Log.d(TAG, "Sending data to server: UUID=" + medicion.getUuid() + ", Major=" + medicion.getValue());

        //Send the measurement to the server
        medicionService.createMeasurement(medicion).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Measurement sent successfully.");
                } else {
                    Log.e(TAG, "Failed to send measurement. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG, "Error sending measurement: " + t.getMessage());
            }
        });
    }

    @SuppressLint("MissingPermission") // Assegura't de gestionar permisos abans
    private Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            Log.w(TAG, "LocationManager is null.");
            return null;
        }
    }
}

