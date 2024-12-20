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
        ImageButton graficaButton = findViewById(R.id.iconGraficaContacto);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, MapaActivity.class);
            startActivity(intent);
        });

        // MAPA
        ImageButton mapaButton = findViewById(R.id.iconoMapContacto);

        mapaButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, UbicacionActivity.class);
            startActivity(intent);
        });

        // CONTACTO
        ImageButton contactoButton = findViewById(R.id.iconAgendaContacto);

        contactoButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, ContactoActivity.class);
            startActivity(intent);
        });

        // Home
        ImageButton homeButton = findViewById(R.id.iconHomeContacto);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // PERFIL
        ImageButton perfilButton = findViewById(R.id.iconagenda2Contacto);

        perfilButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, AjustesActivity.class);
            startActivity(intent);
        });
    }


}




