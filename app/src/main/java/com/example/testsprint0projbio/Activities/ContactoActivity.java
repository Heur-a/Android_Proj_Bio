package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;

public class ContactoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto);

        Spinner spinner = findViewById(R.id.spinner);
        String[] asuntosArray = getResources().getStringArray(R.array.asuntos_array);

        // Configurar adaptador personalizado para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, asuntosArray) {
            @Override
            public boolean isEnabled(int position) {
                // Deshabilitar la primera opción
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                // Cambiar el color de la primera opción deshabilitada
                if (position == 0) {
                    ((TextView) view).setTextColor(Color.GRAY);
                } else {
                    ((TextView) view).setTextColor(Color.WHITE);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Manejar selección de opciones
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String selectedAsunto = parent.getItemAtPosition(position).toString();
                    Toast.makeText(ContactoActivity.this, "Seleccionaste: " + selectedAsunto, Toast.LENGTH_SHORT).show();
                }
                ((TextView) view).setTextColor(Color.BLACK);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // GRAFICAS
        ImageButton graficaButton = findViewById(R.id.iconGraficaContacto);

        graficaButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContactoActivity.this, DatosSensorActivity.class);
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
        Button enviarButton = findViewById(R.id.botonIniciar2);
        enviarButton.setOnClickListener(v -> {
            String nombre = ((EditText) findViewById(R.id.editTextNombre)).getText().toString();
            String apellido = ((EditText) findViewById(R.id.editTextApellido)).getText().toString();
            String correo = ((EditText) findViewById(R.id.editTextTextEmailAddress)).getText().toString();
            String telefono = ((EditText) findViewById(R.id.editTextPhone)).getText().toString();
            String asunto = spinner.getSelectedItem().toString();

            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || spinner.getSelectedItemPosition() == 0) {
                Toast.makeText(ContactoActivity.this, "Por favor, completa todos los campos obligatorios.", Toast.LENGTH_SHORT).show();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(ContactoActivity.this, "Por favor, introduce un correo válido.", Toast.LENGTH_SHORT).show();
            } else if (!telefono.isEmpty() && !telefono.matches("\\d{9,15}")) {
                Toast.makeText(ContactoActivity.this, "Por favor, introduce un número de teléfono válido.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ContactoActivity.this, "Formulario enviado con éxito.", Toast.LENGTH_SHORT).show();
                // Aquí puedes procesar los datos del formulario
            }
        });

    }

}




