package com.example.testsprint0projbio;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class PeticionarioRESTWorker extends Worker {

    public static final String KEY_METHOD = "KEY_METHOD";
    public static final String KEY_URL = "KEY_URL";
    public static final String KEY_BODY = "KEY_BODY";
    public static final String KEY_RESPONSE_CODE = "KEY_RESPONSE_CODE";
    public static final String KEY_RESPONSE_BODY = "KEY_RESPONSE_BODY";

    public PeticionarioRESTWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        String elMetodo = getInputData().getString(KEY_METHOD);
        String urlDestino = getInputData().getString(KEY_URL);
        String elCuerpo = getInputData().getString(KEY_BODY);

        int codigoRespuesta;
        String cuerpoRespuesta = "";

        try
        {
            HttpURLConnection connection = getHttpURLConnection(urlDestino, elMetodo, elCuerpo);

            codigoRespuesta = connection.getResponseCode();
            StringBuilder acumulador = new StringBuilder();
            try
            {
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                while ((linea = br.readLine()) != null) {
                    acumulador.append(linea);
                }
                cuerpoRespuesta = acumulador.toString();
                connection.disconnect();
            }
            catch (IOException ex)
            {
                Log.d("PeticionarioRESTWorker", "No hi ha cos en la resposta.");
            }
        }
        catch (Exception e)
        {
            Log.d("PeticionarioRESTWorker", "Ocurrio alguna excepcion: " + e.getMessage());
            return Result.failure();
        }

        // Retornem el codi de resposta i el cos de la resposta
        Data outputData = new Data.Builder()
                .putInt(KEY_RESPONSE_CODE, codigoRespuesta)
                .putString(KEY_RESPONSE_BODY, cuerpoRespuesta)
                .build();

        return Result.success(outputData);
    }

    private static @NonNull HttpURLConnection getHttpURLConnection(String urlDestino, String elMetodo, String elCuerpo) throws IOException {
        URL url = new URL(urlDestino);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json; charset-utf-8");
        connection.setRequestMethod(elMetodo);
        connection.setDoInput(true);

        if (!"GET".equals(elMetodo) && elCuerpo != null) {
            connection.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(elCuerpo);
            dos.flush();
            dos.close();
        }
        return connection;
    }
}

