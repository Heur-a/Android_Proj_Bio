package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;

public class TerminosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminos);


        // LOGO
        ImageButton logoButton = findViewById(R.id.logoSobreAPP);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(TerminosActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioSobreAPP);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(TerminosActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaSobreAPP);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(TerminosActivity.this, MapaActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.iconoMapSobreAPP);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(TerminosActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaSobreAPP);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(TerminosActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.IconHomeSobreAPP);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(TerminosActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustessSobreAPP);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(TerminosActivity.this, TerminosActivity.class);
            startActivity(intent);
        });
    }
}




