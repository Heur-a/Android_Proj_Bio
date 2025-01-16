package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;

public class SobreAPPActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sobreapp);


        // LOGO
        ImageButton logoButton = findViewById(R.id.logoWEB);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(SobreAPPActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioWEB);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(SobreAPPActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaWEB);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(SobreAPPActivity.this, DatosSensorActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.iconoMapWEB);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(SobreAPPActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaWEB);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(SobreAPPActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.IconHomeWEB);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(SobreAPPActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustessWEB);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(SobreAPPActivity.this, SobreAPPActivity.class);
            startActivity(intent);
        });
    }
}




