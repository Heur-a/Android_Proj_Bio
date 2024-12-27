package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.net.Uri; // Necesario para trabajar con URLs
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;

public class WebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.WEB);

        // LOGO
        ImageButton logoButton = findViewById(R.id.logoWEB);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioWEB);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaWEB);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebActivity.this, MapaActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.iconoMapWEB);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaWEB);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.IconHomeWEB);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustessWEB);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebActivity.this, WebActivity.class);
            startActivity(intent);
        });

        // Instagram Button (Nuevo botón para abrir Instagram)
        Button BotonWeb = findViewById(R.id.buttonWEB);

        BotonWeb.setOnClickListener(v -> {
            // Crear un Intent para abrir la URL
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/mmanueeela"));
            startActivity(intent); // Iniciar el navegador o la app que maneje esta URL
        });
    }
}
