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
        ImageButton logoButton = findViewById(R.id.logoEditarPerfil);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioEditarPerfil);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaEditarPerfil);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, DatosSensorActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.mapaEditarPerfil);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaEditarPerfil);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.iconHomeEditarPerfil);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustessEditarPerfil);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, AjustesActivity.class);
            startActivity(intent);
        });
    }
}




