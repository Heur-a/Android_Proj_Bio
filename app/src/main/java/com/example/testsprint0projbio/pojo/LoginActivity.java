package com.example.testsprint0projbio.pojo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.MainActivity;
import com.example.testsprint0projbio.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);  // Carga el layout que quieres mostrar

        Button loginButton = findViewById(R.id.botonIniciar);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });
    }
}