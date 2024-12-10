package com.example.testsprint0projbio.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.utility.StepCounterManager;

public class GraficaActivity extends AppCompatActivity {

    private StepCounterManager stepCounterManager;
    private TextView stepsTextView;
    private TextView distanceTextView;
    private TextView goalTextView;
    private EditText goalInputEditText;
    private Button updateGoalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafica);  // Asegúrate de que este layout tenga los TextViews correspondientes

        // Obtener las referencias de los TextViews y EditText desde el layout
        stepsTextView = findViewById(R.id.stepsTextView);
        distanceTextView = findViewById(R.id.distanceTextView);
        goalTextView = findViewById(R.id.goalTextView);
        goalInputEditText = findViewById(R.id.goalInputEditText);
        updateGoalButton = findViewById(R.id.updateGoalButton);

        // Crear el StepCounterManager y pasarle las referencias
        stepCounterManager = new StepCounterManager(this, stepsTextView, distanceTextView, goalTextView);

        // Registrar el sensor cuando la actividad se cree
        stepCounterManager.registerSensorListener();

        // Configurar el botón para actualizar el objetivo de pasos
        updateGoalButton.setOnClickListener(v -> {
            stepCounterManager.updateTargetFromInput(goalInputEditText);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desregistrar el sensor cuando la actividad se destruya
        stepCounterManager.unregisterSensorListener();
    }
}
