package com.example.apellido.disss;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.EditText;
import android.widget.TextView;

public class StepCounterManager implements SensorEventListener {

    private Context context;
    private TextView stepsTextView;
    private TextView distanceTextView;
    private TextView goalTextView;
    private SensorManager sensorManager;
    private Sensor stepCounter;
    private int totalSteps = 0;
    private int previousSteps = 0;
    private double distanceCovered = 0.0;
    private int currentSteps = 0;
    private int targetSteps = 10000;  // Objetivo de pasos predeterminado
    public boolean isSensorAvailable;

    // Constructor actualizado
    public StepCounterManager(Context context, TextView stepsTextView, TextView distanceTextView, TextView goalTextView) {
        this.context = context;
        this.stepsTextView = stepsTextView;
        this.distanceTextView = distanceTextView;
        this.goalTextView = goalTextView;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        this.isSensorAvailable = stepCounter != null;
    }

    // Método para registrar el sensor
    public void registerSensorListener() {
        if (isSensorAvailable) {
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_UI);
        } else {
            stepsTextView.setText("Sensor no disponible");
        }
    }

    // Método para desregistrar el sensor
    public void unregisterSensorListener() {
        if (isSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

    // Método para establecer el objetivo de pasos
    public void setTargetSteps(int target) {
        this.targetSteps = target;
        goalTextView.setText("Objetivo: " + targetSteps + " pasos");
    }

    // Método para obtener los pasos actuales
    public int getCurrentSteps() {
        return currentSteps;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event != null && event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (totalSteps == 0) {
                totalSteps = (int) event.values[0];
            }

            currentSteps = (int) event.values[0] - totalSteps + previousSteps;
            distanceCovered = currentSteps * 0.78; // Longitud promedio de zancada (0.78 m)

            stepsTextView.setText("Pasos: " + currentSteps);
            distanceTextView.setText(String.format("Distancia: %.2f m", distanceCovered));

            // Verifica si se ha alcanzado el objetivo de pasos
            if (currentSteps >= targetSteps) {
                goalTextView.setText("¡Objetivo alcanzado!");
            } else {
                goalTextView.setText("Objetivo: " + targetSteps + " pasos");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se requiere implementar
    }

    // Método para actualizar el objetivo desde un EditText
    public void updateTargetFromInput(EditText goalInputEditText) {
        try {
            int newTarget = Integer.parseInt(goalInputEditText.getText().toString());
            setTargetSteps(newTarget);
        } catch (NumberFormatException e) {
            goalInputEditText.setError("Por favor ingresa un número válido");
        }
    }
}
