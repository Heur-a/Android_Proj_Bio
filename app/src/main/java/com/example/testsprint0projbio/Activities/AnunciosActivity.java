package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;

public class AnunciosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anuncios); // Usa el diseño anuncios.xml

        // LOGO
        ImageButton logoButton = findViewById(R.id.logoAnuncios);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioUbicacion);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaUbicacion);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, MapaActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.iconoMapAnuncios);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaUbicacion);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.IconHomeAnuncios);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustessUbicacion);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, AjustesActivity.class);
            startActivity(intent);
        });

    }
}
