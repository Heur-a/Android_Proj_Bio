package com.example.testsprint0projbio.api;

import static com.example.testsprint0projbio.MainActivity.ETIQUETA_LOG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.testsprint0projbio.pojo.Medicion;
import com.example.testsprint0projbio.pojo.TramaIBeacon;
import com.example.testsprint0projbio.utility.Utilidades;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Worker for making REST requests to the measurements API.
 */
public class PeticionarioRESTWorker extends Worker {

    // Key for the HTTP method (GET, POST, etc.)
    public static final String KEY_METHOD = "KEY_METHOD";

    // Key for the URL destination
    public static final String KEY_URL = "KEY_URL";

    // Key for the request body (e.g., the JSON payload)
    public static final String KEY_BODY = "KEY_BODY";

    // Key for the response code of the HTTP request
    public static final String KEY_RESPONSE_CODE = "KEY_RESPONSE_CODE";

    // Key for the response body (e.g., the response JSON)
    public static final String KEY_RESPONSE_BODY = "KEY_RESPONSE_BODY";
    public static final String URL = "http://192.168.146.90:80/mediciones";

    private final Context context;

    /**
     * Constructor for the Worker.
     *
     * @param context The application context
     * @param params Parameters for the Worker
     */
    public PeticionarioRESTWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    /**
     * Executes the Worker task, which is making a REST request.
     *
     * @return Result of the execution (success or failure)
     */
    @NonNull
    @Override
    public Result doWork() {
        // Retrieve dynamic values passed at runtime through Data
        String method = getInputData().getString(KEY_METHOD); // HTTP method
        String urlDestination = getInputData().getString(KEY_URL); // API URL
        String requestBody = getInputData().getString(KEY_BODY); // Request body (payload)

        int responseCode;
        String responseBody = "";

        try {
            // Create HTTP connection and send request
            HttpURLConnection connection = getHttpURLConnection(urlDestination, method, requestBody);

            // Get response code and body
            responseCode = connection.getResponseCode();
            StringBuilder responseAccumulator = new StringBuilder();
            try {
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    responseAccumulator.append(line);
                }
                responseBody = responseAccumulator.toString();
                connection.disconnect();
            } catch (IOException ex) {
                Log.d("PeticionarioRESTWorker", "No response body.");
            }

            // Show a Toast depending on the response code
            if (responseCode == 201) { // Successful creation
                showToast("Measurement sent successfully!");
            } else if (responseCode == 400) { // Bad request
                showToast("Error: Invalid measurement data.");
            } else {
                showToast("Unexpected response: " + responseCode);
            }

        } catch (Exception e) {
            Log.d("PeticionarioRESTWorker", "An exception occurred: " + e.getMessage());
            showToast("Failed to send measurement: " + e.getMessage());
            return Result.failure();
        }

        // Returning the response code and body as output data
        Data outputData = new Data.Builder()
                .putInt(KEY_RESPONSE_CODE, responseCode)
                .putString(KEY_RESPONSE_BODY, responseBody)
                .build();

        return Result.success(outputData);
    }

    /**
     * Configures the HTTP connection for the REST request.
     *
     * @param urlDestination Destination URL
     * @param method HTTP method (GET, POST, etc.)
     * @param requestBody Request body (for POST or PUT methods)
     * @return Configured HttpURLConnection
     * @throws IOException If there is a connection issue
     */
    private static @NonNull HttpURLConnection getHttpURLConnection(String urlDestination, String method, String requestBody) throws IOException {
        URL url = new URL(urlDestination);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestMethod(method);
        connection.setDoInput(true);

        // If the method is not GET, add the request body
        if (!"GET".equals(method) && requestBody != null) {
            connection.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(requestBody);
            dos.flush();
            dos.close();
        }
        return connection;
    }

    /**
     * Shows a Toast message in the UI thread.
     *
     * @param message The message to show in the Toast
     */
    private void showToast(final String message) {
        // Ensuring the Toast is run on the UI thread
        new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }

    public static void POST(TramaIBeacon tib, Context context) {
        if(tib == null) {
            Toast.makeText(context, "No hay datos disponibles", Toast.LENGTH_SHORT).show();
            return;

        }
        Medicion medicion = new Medicion( Utilidades.bytesToIntOK(tib.getMajor()), "Zona Industrial", "CO2");
        Log.d(ETIQUETA_LOG, " Medicion: " + medicion.toString());
        String json = medicion.toJson();
        Log.d(ETIQUETA_LOG, " JSON: " + json);
        Data inputData = new Data.Builder()
                .putString(PeticionarioRESTWorker.KEY_METHOD, "POST")
                .putString(PeticionarioRESTWorker.KEY_URL, URL)
                .putString(PeticionarioRESTWorker.KEY_BODY, json)
                .build();
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(PeticionarioRESTWorker.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);
    }
}
