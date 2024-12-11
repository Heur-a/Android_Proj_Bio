package com.example.testsprint0projbio.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StepCounterManager implements SensorEventListener {

    private final Context context;
    private final SensorManager sensorManager;
    private final Sensor stepSensor;
    private final SharedPreferences sharedPreferences;
    private TextView stepsTextView, distanceTextView, goalTextView;

    private static final float STEP_LENGTH_METERS = 0.75f; // Longitud promedio de un paso en metros

    public StepCounterManager(Context context, TextView stepsTextView, TextView distanceTextView, TextView goalTextView) {
        this.context = context;
        this.stepsTextView = stepsTextView;
        this.distanceTextView = distanceTextView;
        this.goalTextView = goalTextView;

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sharedPreferences = context.getSharedPreferences("StepData", Context.MODE_PRIVATE);

        loadGoal();
    }

    private void loadGoal() {
        // Lee el objetivo guardado en SharedPreferences
        int savedGoal = sharedPreferences.getInt("goal", 0);  // Valor predeterminado es 0 si no está guardado

        // Si el objetivo es mayor que 0, lo mostramos en la interfaz
        if (savedGoal > 0 && goalTextView != null) {
            goalTextView.setText(savedGoal + " pasos");
        }
    }

    public void registerSensorListener() {
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void unregisterSensorListener() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int totalSteps = (int) event.values[0];
            saveStepData(totalSteps);
            updateUI(totalSteps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void saveStepData(int totalSteps) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("steps", totalSteps);
        editor.putFloat("distance", totalSteps * STEP_LENGTH_METERS);
        editor.apply();
    }

    private void updateUI(int totalSteps) {
        if (stepsTextView != null) {
            stepsTextView.setText(String.valueOf(totalSteps));
        }
        if (distanceTextView != null) {
            float distance = totalSteps * STEP_LENGTH_METERS;
            distanceTextView.setText(String.format("%.2f m", distance));
        }
    }

    public void updateTargetFromInput(EditText goalInputEditText) {
        // Obtiene el valor ingresado por el usuario
        String inputText = goalInputEditText.getText().toString().trim();

        // Validamos que el input no esté vacío y sea un número
        if (!inputText.isEmpty()) {
            try {
                // Convertimos el valor a un número (en este caso, pasos)
                int newGoal = Integer.parseInt(inputText);

                // Actualizamos el TextView con el nuevo objetivo
                goalTextView.setText(newGoal + " pasos");

                // Guarda el objetivo actualizado en SharedPreferences si es necesario
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("goal", newGoal);
                editor.apply();

            } catch (NumberFormatException e) {
                // Si la conversión falla (no es un número válido)
                Toast.makeText(context, "Por favor ingrese un número válido.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "El objetivo no puede estar vacío.", Toast.LENGTH_SHORT).show();
        }
    }
    public int getTotalSteps() {
        return sharedPreferences.getInt("steps", 0);  // Devuelve el número de pasos almacenados
    }

    public int getGoal() {
        return sharedPreferences.getInt("goal", 0);  // Devuelve el objetivo almacenado
    }


}
