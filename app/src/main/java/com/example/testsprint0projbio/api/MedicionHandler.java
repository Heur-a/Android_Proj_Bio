package com.example.testsprint0projbio.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.testsprint0projbio.pojo.Medicion;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicionHandler {
    private static final String TAG = "MedicionHandler";
    private final ApiService apiService;

    public MedicionHandler(Context context) {
        // Obtén una instància del servei de l'API
        this.apiService = OzoneApiClient.getInstance(context).createService(MedicionService.class);
    }

    /**
     * Envia una mesura a l'API REST.
     *
     * @param medicion La mesura a enviar.
     * @param callback El callback per gestionar el resultat de l'operació.
     */
    public void sendMeasurement(Medicion medicion, MeasurementCallback callback) {
        // Crea una crida a l'API
        Call<ResponseBody> call = ((com.example.testsprint0projbio.api.MedicionService) apiService)
                .createMeasurement(medicion);

        // Gestiona la resposta asíncrona
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Measurement sent successfully: " + response.message());
                    callback.onSuccess();
                } else {
                    Log.e(TAG, "Failed to send measurement. Response code: " + response.code());
                    callback.onFailure(new Exception("Response code: " + response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG, "Error sending measurement: " + t.getMessage());
                callback.onFailure(t);
            }
        });
    }

    /**
     * Interfície per gestionar el resultat de l'enviament.
     */
    public interface MeasurementCallback {
        void onSuccess();

        void onFailure(Throwable t);
    }
}

