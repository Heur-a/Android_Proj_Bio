package com.example.testsprint0projbio.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.MainActivity;
import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.pojo.TramaIBeacon;
import com.example.testsprint0projbio.utility.BluetoothNodeManager;
import com.example.testsprint0projbio.utility.QRCodeService;
import com.example.testsprint0projbio.utility.Utilidades;

import java.io.Console;
import java.util.Arrays;

public class EscanearQr extends AppCompatActivity {

    private QRCodeService qrCodeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enlazar_sensor);  // Carga el layout que quieres mostrar

        //Boton iniciar escaner qr
        Button iniciarEscaner = findViewById(R.id.enlazarSensor);
        iniciarEscaner.setOnClickListener(v -> {
            qrCodeService = new QRCodeService(EscanearQr.this);
            // Inicia el escaner QR
            qrCodeService.startQRCodeScanner();

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result from the QR code scanner
        String qrCodeContent = qrCodeService.handleActivityResult(requestCode, resultCode, data);

        if (qrCodeContent != null) {
            // Display the scanned content
            Toast.makeText(this, "QR Code Content: " + qrCodeContent, Toast.LENGTH_LONG).show();
            try {
                Log.d(TAG, "onActivityResult: qrCode isn't null ");
                String uuid = getQruuid(qrCodeContent);
                Log.d(TAG, "onActivityResult: qrCode is valid");
                // Call BluetoothManager scan for this uuid
                BluetoothNodeManager bluetoothManager = new BluetoothNodeManager(this);
                bluetoothManager.searchSpecificDevice(uuid, new BluetoothNodeManager.DeviceScanCallback() {
                    @Override
                    public void onDeviceFound(TramaIBeacon device) {
                        Log.d(TAG, "onDeviceFound: Scan success");
                        Toast.makeText( getApplicationContext(), "Dispositivo encontrado " + Utilidades.bytesToString(device.getUUID()), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onDeviceFound: Device found" + device);
                        //TODO: Enlazar sensor
                        Log.d(TAG, "onDeviceFound: initiate Intent");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Log.d(TAG, "onDeviceFound: Stop Scan");
                        bluetoothManager.stopScanning();
                        Log.d(TAG, "onDeviceFound: Start Activity");
                        startActivity(intent);
                    }

                    @Override
                    public void onScanFailed(String errorMessage) {
                        Log.d(TAG, "onScanFailed: Scan Failure");
                    }
                });


            } catch (IllegalArgumentException e) {
                // Handle the exception
                Toast.makeText(this, "Invalid QR Code format.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // Handle other exceptions
                Toast.makeText(this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // Notify the user that no QR code was scanned
            Toast.makeText(this, "No QR Code scanned.", Toast.LENGTH_SHORT).show();
        }
    }



    private String getQruuid (String qrCodeContent) {
        if(qrCodeContent.length() != 16){
            throw new IllegalArgumentException("El contenido QR debe tener una longitud de 16 caracteres");
        }
        return qrCodeContent;
    }



}
