package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.api.AuthService;
import com.example.testsprint0projbio.api.LocalStorageManager;
import com.example.testsprint0projbio.api.MedicionService;
import com.example.testsprint0projbio.api.NodeService;
import com.example.testsprint0projbio.api.OzoneApiClient;
import com.example.testsprint0projbio.pojo.Node;
import com.example.testsprint0projbio.pojo.NodeResponse;
import com.example.testsprint0projbio.pojo.UserLogin;
import com.example.testsprint0projbio.utility.MeasurementService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosMentiraActivity extends AppCompatActivity {

    public static final String UUID = "UUID_ADMIN123456";
    OzoneApiClient apiClient;
    MedicionService medicionService;
    AuthService authService;
    NodeService nodeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_mentira);

        apiClient = OzoneApiClient.getInstance(this);
        medicionService = apiClient.createService(MedicionService.class);
        authService = apiClient.createService(AuthService.class);
        nodeService = apiClient.createService(NodeService.class);

        //Login con usuario admin
        authService.login(new UserLogin("admin@admin.com","Admin2025") ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(DatosMentiraActivity.this, "Login Bien", Toast.LENGTH_SHORT).show();
                    checkNode();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DatosMentiraActivity.this, "Login Mal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Atura el servei
        Intent intent = new Intent(this, MeasurementService.class);
        stopService(intent);
    }

    private void checkNode () {
        OzoneApiClient.getInstance(this)
                .createService(NodeService.class)
                .getNodeById()
                .enqueue(new Callback<NodeResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<NodeResponse> call, @NonNull Response<NodeResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Obté la UUID des de la resposta
                            String uuid = response.body().getUuid();

                            // Crea un objecte Node amb la UUID
                            Node node = new Node(uuid);

                            // Guarda el Node a l'emmagatzematge local
                            LocalStorageManager localStorageManager = new LocalStorageManager(getApplicationContext());
                            localStorageManager.saveNode(node);

                            Intent intent = new Intent(getApplicationContext(), MeasurementService.class);
                            intent.putExtra("UUID", uuid);
                            ContextCompat.startForegroundService(getApplicationContext(), intent);


                        }

                        //No associated Node
                        else if (response.code() == 401 || response.code() == 400) {

                        createNode();

                        } else {
                            Log.e("API_ERROR", "Error code: " + response.code());
                            Toast.makeText(DatosMentiraActivity.this, "No se pueden obtener datos del nodo", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NodeResponse> call, @NonNull Throwable t) {
                        Log.e("API_ERROR", "Request failed", t);
                    }
                });
    }

    private void createNode () {
        nodeService.createNode(new Node(UUID)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Dispositivo añadido", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MeasurementService.class);
                    intent.putExtra("UUID", UUID);
                    ContextCompat.startForegroundService(getApplicationContext(), intent);

                } else if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Dispositivo no añadido, error servidor", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Dispositivo no añadido, error servidor", Toast.LENGTH_LONG).show();

            }
        });
    }

}
