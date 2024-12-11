package com.example.testsprint0projbio.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

    public void updateTargetFromInput(TextView goalInputEditText, TextView goalTextView) {
        // Obtén el texto ingresado en el TextView de entrada
        String goalInputText = goalInputEditText.getText().toString();

        // Verifica que el texto no esté vacío
        if (goalInputText.isEmpty()) {
            goalInputEditText.setError("El objetivo no puede estar vacío");
            return;
        }

        try {
            // Convierte el texto a un número entero
            int newGoal = Integer.parseInt(goalInputText);

            // Verifica que el número sea positivo
            if (newGoal <= 0) {
                goalInputEditText.setError("El objetivo debe ser un número mayor a cero");
                return;
            }

            // Actualiza el TextView goalTextView con el nuevo objetivo
            goalTextView.setText(String.valueOf(newGoal));

            // Muestra un mensaje de éxito (opcional)
            Toast.makeText(goalInputEditText.getContext(), "Objetivo actualizado a: " + newGoal, Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            // Muestra un error si el formato no es un número válido
            goalInputEditText.setError("Por favor, ingresa un número válido");
        }
    }

    public void setDailyStepGoal(int newGoal) {
    }
}
