package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.utility.BiometricUtil;

public class PrincipalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);// Carga el layout que quieres mostrar

        //huella biométrica
        // Verificar soporte biométrico
        // Requerir autenticación biométrica antes de continuar
        if (BiometricUtil.isBiometricAvailable(this)) {
            BiometricUtil.attemptBiometricAuthWithBlock(this, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad si hay un error de autenticación
                    super.onAuthenticationError(errorCode, errString);
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    Toast.makeText(getApplicationContext(), "Authentication succeeded", Toast.LENGTH_SHORT).show();
                    super.onAuthenticationSucceeded(result);
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            });
        } else {
            Toast.makeText(this, "No Biometric Sensor available/registered", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si no hay autenticación disponible
        }

        Button login = findViewById(R.id.botonIniciar);
        login.setOnClickListener(v -> {
            // Iniciar sesión
            startActivity(new Intent(PrincipalActivity.this, LoginActivity.class));
            finish();
        });

    }
}