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
        ImageButton logoButton = findViewById(R.id.logo5);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncio);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGrafica);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.iconoMap);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, MapaActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagenda);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.IconHome);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustess);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnunciosActivity.this, AjustesActivity.class);
            startActivity(intent);
        });

    }
}
