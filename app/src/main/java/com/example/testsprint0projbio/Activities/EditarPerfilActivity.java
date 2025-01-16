package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;

public class EditarPerfilActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editarperfil);

        // Privacidad
        ImageButton privacidad = findViewById(R.id.privacidadflecha);

        privacidad.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, PrivacyActivity.class);
            startActivity(intent);
        });

        // Términos
        ImageButton terminos = findViewById(R.id.privacidadflecha2);

        terminos.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, TerminosActivity.class);
            startActivity(intent);
        });

        // Sobre Nosotros
        ImageButton sobrenosotros = findViewById(R.id.flecha2);

        sobrenosotros.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, SobreAPPActivity.class);
            startActivity(intent);
        });

        // Sobre app
        ImageButton sobreapp = findViewById(R.id.flechaSobreAPP);

        sobreapp.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, SobreAPPActivity.class);
            startActivity(intent);
        });

        // Web
        ImageButton web = findViewById(R.id.flechaweb);

        web.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, WebActivity.class);
            startActivity(intent);
        });


        // LOGO
        ImageButton logoButton = findViewById(R.id.logoEditarPerfil);

        logoButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // ANUNCIOS
        ImageButton anunciosButton = findViewById(R.id.iconanuncioEditarPerfil);

        anunciosButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, AnunciosActivity.class);
            startActivity(intent);
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaEditarPerfil);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, DatosSensorActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.mapaEditarPerfil);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconagendaEditarPerfil);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.iconHomeEditarPerfil);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconAjustessEditarPerfil);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilActivity.this, EditarPerfilActivity.class);
            startActivity(intent);
        });
    }
}




