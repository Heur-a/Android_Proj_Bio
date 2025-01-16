package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.api.OzoneApiClient;

public class AjustesActivity extends AppCompatActivity {
    private static final String BASE_URL = OzoneApiClient.BASE_URL; // Replace this with your base URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editarperfil);

        // FlechaWeb
        ImageButton flechaWebButton = findViewById(R.id.flechaweb);
        flechaWebButton.setOnClickListener(v -> openWebPage(BASE_URL + "/"));

        // Flecha (Log-in)
        ImageButton flechaButton = findViewById(R.id.flecha);
        flechaButton.setOnClickListener(v -> openWebPage(BASE_URL + "/log-in.html"));

        // FlechaSobreNosotros
        ImageButton flechaSobreAppButton = findViewById(R.id.flecha2);
        flechaSobreAppButton.setOnClickListener(v -> openWebPage(BASE_URL + "/acerca_de.html"));

        // Privacidad
        ImageButton privacidad = findViewById(R.id.privacidadflecha);

        privacidad.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, PrivacyActivity.class);
            startActivity(intent);
        });


        // Términos
        ImageButton terminos = findViewById(R.id.privacidadflecha2);

        terminos.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, TerminosActivity.class);
            startActivity(intent);
        });

        // Sobre app
        ImageButton sobreapp = findViewById(R.id.flechaSobreAPP);

        sobreapp.setOnClickListener(v -> {
            Intent intent = new Intent(AjustesActivity.this, SobreAPPActivity.class);
            startActivity(intent);
        });

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

    // Helper method to open a web page
    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
