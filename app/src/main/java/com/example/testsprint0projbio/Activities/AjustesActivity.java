package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;

public class AjustesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editarperfil);

        // LOGO
        ImageButton logoButton = findViewById(R.id.logoAjustes);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioAjustes);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaAjustes);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, MapaActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.mapaAjustes);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaAjustes);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.iconHomeAjustes);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustessAjustes);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, AjustesActivity.class);
            startActivity(intent);
        });
    }
}




