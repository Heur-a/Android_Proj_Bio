package com.example.testsprint0projbio.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private LinearLayout popupLayout;
    private Button updateGoalPopupButton;
    private Button cancelPopupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafica);

        // Referencias a vistas
        stepsTextView = findViewById(R.id.stepsTextView);
        distanceTextView = findViewById(R.id.distanceTextView);
        goalTextView = findViewById(R.id.goalTextView);
        updateGoalButton = findViewById(R.id.updateGoalButton);
        popupLayout = findViewById(R.id.popupLayout);
        goalInputEditText = findViewById(R.id.goalInputEditText);
        updateGoalPopupButton = findViewById(R.id.updateGoalPopupButton);
        cancelPopupButton = findViewById(R.id.cancelPopupButton);

        // Instancia de StepCounterManager
        stepCounterManager = new StepCounterManager(this, stepsTextView, distanceTextView, goalTextView);
        stepCounterManager.registerSensorListener();

        // Configurar botón para mostrar el popup
        updateGoalButton.setOnClickListener(v -> popupLayout.setVisibility(View.VISIBLE));

        // Configurar botón de actualización dentro del popup
        updateGoalPopupButton.setOnClickListener(v -> {
            String goalInput = goalInputEditText.getText().toString();
            if (!goalInput.isEmpty()) {
                try {
                    int newGoal = Integer.parseInt(goalInput);
                    stepCounterManager.updateTargetFromInput(goalInputEditText, goalTextView);
                    goalTextView.setText(newGoal + " pasos");
                } catch (NumberFormatException e) {
                    goalInputEditText.setError("Por favor, ingresa un número válido");
                }
            } else {
                goalInputEditText.setError("Este campo no puede estar vacío");
            }
            popupLayout.setVisibility(View.GONE);
        });

        // Configurar botón de cancelar
        cancelPopupButton.setOnClickListener(v -> popupLayout.setVisibility(View.GONE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stepCounterManager.unregisterSensorListener();
    }
}
