package com.example.testsprint0projbio.utility;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class BiometricUtil {

    /**
     * Verifica si la autenticación biométrica está disponible y es utilizable.
     *
     * @param context Contexto actual
     * @return Verdadero si está disponible, falso de lo contrario
     */
    public static boolean isBiometricAvailable(Context context) {
        BiometricManager biometricManager = BiometricManager.from(context);
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                == BiometricManager.BIOMETRIC_SUCCESS;
    }

    /**
     * Intenta iniciar la autenticación biométrica.
     *
     * @param context Contexto actual
     */
    public static void attemptBiometricAuth(Context context) {
        Executor executor = ContextCompat.getMainExecutor(context);
        BiometricPrompt.AuthenticationCallback callback = getAuthenticationCallback(context);
        BiometricPrompt biometricPrompt = new BiometricPrompt((AppCompatActivity) context, executor, callback);

        BiometricPrompt.PromptInfo promptInfo = getPromptInfo("Biometric Authentication",
                "Please login to get into the app",
                "This app is using Biometric Authentication to recognize user", true);

        biometricPrompt.authenticate(promptInfo);
    }

    /**
     * Crea una instancia de PromptInfo para la autenticación biométrica.
     *
     * @param title                     Título del prompt
     * @param subtitle                  Subtítulo del prompt
     * @param description               Descripción del prompt
     * @param isDeviceCredentialAllowed Si se permite la credencial del dispositivo
     * @return Instancia de PromptInfo
     */
    private static BiometricPrompt.PromptInfo getPromptInfo(String title, String subtitle, String description, boolean isDeviceCredentialAllowed) {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setDescription(description)
                .setDeviceCredentialAllowed(isDeviceCredentialAllowed)
                .build();
    }

    /**
     * Obtiene el callback de autenticación para manejar los eventos de autenticación.
     *
     * @param context Contexto actual
     * @return Instancia de AuthenticationCallback
     */
    private static BiometricPrompt.AuthenticationCallback getAuthenticationCallback(Context context) {
        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                Toast.makeText(context, "Authentication error", Toast.LENGTH_SHORT).show();
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                Toast.makeText(context, "Authentication successful", Toast.LENGTH_SHORT).show();
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
                super.onAuthenticationFailed();
            }
  };
}
}