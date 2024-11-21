package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;

public class ContactoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto);

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGrafica2);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, GraficaActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.iconoMap2);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, MapaActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconAgenda);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.iconHome);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconagenda2);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, AjustesActivity.class);
            startActivity(intent);
        });
    }


}




