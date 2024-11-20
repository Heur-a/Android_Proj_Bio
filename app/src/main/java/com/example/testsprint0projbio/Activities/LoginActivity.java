package com.example.testsprint0projbio.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.MainActivity;
import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.api.AuthService;
import com.example.testsprint0projbio.api.CookieManager;
import com.example.testsprint0projbio.api.OzoneApiClient;
import com.example.testsprint0projbio.pojo.UserLogin;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public String email;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);  // Carga el layout que quieres mostrar

        TextView emailInput = findViewById(R.id.correoInput);
        TextView passwordInput = findViewById(R.id.contrasenyaInput);

        Button loginButton = findViewById(R.id.botonIniciar);
        loginButton.setOnClickListener(v -> {

            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();

            // Verificar si email y password son correctos
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // trim email
            email = email.trim();

            // Enviar email y password al servidor
            OzoneApiClient.getInstance(this).createService(AuthService.class).login(new UserLogin(email, password))
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                try {
                                    // Obtener el cuerpo de la respuesta como texto
                                    String responseBody = response.body().string();

                                    // Verificar el contenido de la respuesta
                                    if (responseBody.contains("OK")) {
                                        // Emmagatzemar la cookie de sessió
                                        String sessionCookie = response.headers().get("Set-Cookie");
                                        if (sessionCookie != null) {
                                            CookieManager.saveSessionCookie(sessionCookie,getApplicationContext()); // Guardar a SharedPreferences
                                            Log.d(TAG, "onResponse: Session Cookie " + sessionCookie);
                                        }

                                        Toast.makeText(LoginActivity.this,"Sesión iniciada correctamente",Toast.LENGTH_SHORT).show();
                                        // Cambiar de actividad si el login es exitoso
                                        Intent intent = new Intent(LoginActivity.this, EscanearQrActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Log.e("Login", "Respuesta inesperada: " + responseBody);
                                        Toast.makeText(LoginActivity.this, "Error de login", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.e("Login", "Error al procesar la respuesta", e);
                                    Toast.makeText(LoginActivity.this, "Error de login", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Error de login
                                Log.e("Login", "Error: " + response.message());
                                Toast.makeText(LoginActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            Log.e("Login", "Error: " + t.getMessage());
                        }
                    });
        });
    }

}
