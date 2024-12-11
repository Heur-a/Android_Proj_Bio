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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieData;

import java.util.List;

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
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafica);

        // Obtener las referencias de los TextViews y EditText desde el layout
        stepsTextView = findViewById(R.id.stepsTextView);
        distanceTextView = findViewById(R.id.distanceTextView);
        goalTextView = findViewById(R.id.goalTextView);
        goalInputEditText = findViewById(R.id.goalInputEditText);
        updateGoalButton = findViewById(R.id.updateGoalButton);
        popupLayout = findViewById(R.id.popupLayout);
        updateGoalPopupButton = findViewById(R.id.updateGoalPopupButton);
        cancelPopupButton = findViewById(R.id.cancelPopupButton);
        pieChart = findViewById(R.id.pieChart);

        // Crear el StepCounterManager y pasarle las referencias
        stepCounterManager = new StepCounterManager(this, stepsTextView, distanceTextView, goalTextView);

        // Registrar el sensor cuando la actividad se cree
        stepCounterManager.registerSensorListener();

        // Mostrar el popup cuando se presione el botón de cambiar objetivo
        updateGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupLayout.setVisibility(View.VISIBLE); // Muestra el popup
            }
        });

        // Acción cuando se presiona el botón de "Actualizar"
        updateGoalPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llama al método para actualizar el objetivo
                stepCounterManager.updateTargetFromInput(goalInputEditText); // Llamamos a la lógica para actualizar el objetivo
                popupLayout.setVisibility(View.GONE); // Cierra el popup
                updatePieChart(); // Actualiza el gráfico
            }
        });

        // Acción cuando se presiona el botón de "Cancelar"
        cancelPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Solo cerramos el popup sin hacer cambios
                popupLayout.setVisibility(View.GONE); // Cierra el popup
            }
        });

        // Actualizar el gráfico al iniciar
        updatePieChart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desregistrar el sensor cuando la actividad se destruya
        stepCounterManager.unregisterSensorListener();
    }

    private void updatePieChart() {
        // Obtén los pasos y el objetivo actualizados desde StepCounterManager
        int totalSteps = stepCounterManager.getTotalSteps(); // Obtiene los pasos actuales
        int goal = stepCounterManager.getGoal(); // Obtiene el objetivo guardado

        // Si no se ha establecido un objetivo, no se muestra el gráfico
        if (goal == 0) {
            goalTextView.setText("Set a goal first");
            return;
        }

        // Verificar si los pasos han superado o igualado el objetivo
        if (totalSteps >= goal) {
            goalTextView.setText("Objetivo Conseguido");
            totalSteps = goal; // Para asegurarnos de que el gráfico no muestre más de lo necesario
        } else {
            goalTextView.setText(goal + " pasos");
        }

        // Datos para el gráfico circular
        float progress = totalSteps;
        float remaining = goal - progress;

        // Si el progreso ya alcanzó o superó el objetivo, no necesitamos mostrar el "remaining"
        if (remaining < 0) remaining = 0;

        // Crear las entradas para el gráfico
        PieEntry progressEntry = new PieEntry(progress, "Steps");
        PieEntry remainingEntry = new PieEntry(remaining, "Remaining");

        // Crear un conjunto de datos para el gráfico circular
        PieDataSet dataSet = new PieDataSet(List.of(progressEntry, remainingEntry), "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // Crear los datos para el gráfico
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // Actualiza el gráfico
    }

}
