package com.example.testsprint0projbio.Activities;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.MainActivity;
import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.api.MedicionService;
import com.example.testsprint0projbio.api.OzoneApiClient;
import com.example.testsprint0projbio.pojo.DatosDiarios;
import com.example.testsprint0projbio.pojo.Medicion;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.Nullable;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosSensorActivity extends AppCompatActivity {

    private MedicionService medicionService;
    private DatosDiarios datosDiarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_sensor);

        // LOGO
        ImageButton logoButton = findViewById(R.id.logoGrafica);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(DatosSensorActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioGrafica);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(DatosSensorActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaGrafica);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(DatosSensorActivity.this, DatosSensorActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.iconoMapGrafica);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(DatosSensorActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaGrafica);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(DatosSensorActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.IconHomeGrafica);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(DatosSensorActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustessGrafica);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(DatosSensorActivity.this, AjustesActivity.class);
            startActivity(intent);
        });

        medicionService = OzoneApiClient.getInstance(this).createService(MedicionService.class);

        TextView media = findViewById(R.id.mediaDiariaDatos);
        TextView suma = findViewById(R.id.sumaDiaria);

        getDatosDiarios(null, new DatosDiariosCallback() {
            @Override
            public void onSuccess(DatosDiarios datosDiarios) {
                Toast.makeText(DatosSensorActivity.this, "Media diaria: " + datosDiarios.getMedia(), Toast.LENGTH_SHORT).show();
                Log.d("DatosDiarios", "Datos Diarios" + datosDiarios);

                media.setText(String.valueOf(Math.round(datosDiarios.getMedia())) + " ppm");
                suma.setText(String.valueOf(Math.round(datosDiarios.getSuma())) + " ppm");


            }

            @Override
            public void onError(Throwable t) {
                Log.e("DatosDiarios", "Error obteiendo datos", t);
            }
        });


    }

    public void getDatosDiarios(@Nullable String fecha, DatosDiariosCallback callback) {
        if (fecha == null || fecha.isEmpty() || !fecha.matches("\\d{4}-\\d{2}-\\d{2}") ) {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fecha = sdf.format(new Date());
        }

        @Nullable String finalFecha = fecha;
        medicionService.getDailyMeasurements(fecha).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    // Obté el JSON com a String
                    String jsonResponse = response.body().string();

                    // Deserialitza el JSON
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Medicion>>() {}.getType();
                    List<Medicion> measurements = gson.fromJson(jsonResponse, listType);

                    double suma = measurements.stream().mapToDouble(Medicion::getValue).sum();
                    DatosDiarios datosDiarios = new DatosDiarios(finalFecha, suma / measurements.size(), measurements.size());

                    // Retorna el resultat a través del callback
                    callback.onSuccess(datosDiarios);

                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    // Defineix una interfície pel callback
    public interface DatosDiariosCallback {
        void onSuccess(DatosDiarios datosDiarios);
        void onError(Throwable t);
    }

}




