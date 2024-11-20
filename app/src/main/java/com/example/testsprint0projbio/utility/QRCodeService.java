package com.example.testsprint0projbio.utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public  class QRCodeService {
    private final Activity activity;

    public QRCodeService(Activity activity) {
        this.activity = activity;
    }


    public void startQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Escanea un codigo QR");
        integrator.setCameraId(0); // Usa la càmera del darrere
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    public String handleActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            return result.getContents(); // Retorna el contingut del codi QR
        }
        return null; // Si no s'ha escanejat res
    }
}

