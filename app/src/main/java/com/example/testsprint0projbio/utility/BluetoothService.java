package com.example.testsprint0projbio.utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.testsprint0projbio.pojo.TramaIBeacon;

import java.util.List;
import java.util.Objects;

public class BluetoothService {
    private final Context context;
    private BluetoothLeScanner bluetoothLeScanner;
    private ScanCallback scanCallback;

    public BluetoothService(Context context) {
        this.context = context;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            this.bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        }
    }

    public void startScanning(ScanCallback callback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
            }
            return;
        }

        this.scanCallback = callback;
        bluetoothLeScanner.startScan(scanCallback);
    }

    @SuppressLint("MissingPermission")
    public void stopScanning() {
        if (scanCallback != null && bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(scanCallback);
            scanCallback = null;
        }
    }
}

