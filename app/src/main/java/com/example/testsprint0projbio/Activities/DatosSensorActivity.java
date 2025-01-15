package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;

public class DatosSensorActivity extends AppCompatActivity {
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


    }
}




