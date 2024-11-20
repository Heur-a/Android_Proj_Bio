package com.example.testsprint0projbio.utility;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class BiometricUtil {

    public interface AuthCallback {
        void onSuccess();
        void onFailure();
    }

    public static boolean isBiometricAvailable(Context context) {
        BiometricManager biometricManager = BiometricManager.from(context);
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                == BiometricManager.BIOMETRIC_SUCCESS;
    }


    /**
     * Inicia la autenticación biométrica y bloquea el uso hasta que sea exitosa.
     *
     * @param context     Contexto actual
     */
    public static void attemptBiometricAuthWithBlock(Context context, BiometricPrompt.AuthenticationCallback callback) {
        Executor executor = ContextCompat.getMainExecutor(context);
        BiometricPrompt biometricPrompt = new BiometricPrompt(
                (AppCompatActivity) context,
                executor, callback);

        BiometricPrompt.PromptInfo promptInfo = getPromptInfo("Biometric Authentication",
                "Please authenticate to use the app",
                "Authentication is mandatory to access the application.", true);

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
}