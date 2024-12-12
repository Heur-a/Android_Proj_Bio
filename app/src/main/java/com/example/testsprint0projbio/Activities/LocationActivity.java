package com.example.testsprint0projbio.Activities;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.api.MedicionHandler;
import com.example.testsprint0projbio.pojo.Medicion;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import androidx.core.app.ActivityCompat;

public class LocationActivity extends AppCompatActivity {

    private static final String TAG = "LocationActivity";
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private MedicionHandler medicionHandler;

    private TextView sensorTextView;
    private TextView locationTextView;
    private TextView distanceTextView;

    private double deviceLatitude;
    private double deviceLongitude;
    private double sensorLatitude = 39.4798; //cambiar por los que coje el sensor
    private double sensorLongitude = -0.345636;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Inicializar las vistas de texto
        locationTextView = findViewById(R.id.locationTextView);
        sensorTextView = findViewById(R.id.sensorTextView);
        distanceTextView = findViewById(R.id.distanceTextView);

        // Inicializar el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Inicializar el handler de la API
        medicionHandler = new MedicionHandler(this);

        // Obtener las coordenadas del sensor
        obtenerCoordenadasSensor();

        // Verificar permisos y obtener la ubicación del dispositivo
        getLocation();
    }

    private void obtenerCoordenadasSensor() {
        medicionHandler.getSensorCoordinates(new MedicionHandler.MeasurementCallback() {
            @Override
            public void onSuccess(Medicion medicion) {
                if (medicion.getLocY() != 0 && medicion.getLocX() != 0) {
                    sensorLatitude = medicion.getLocY();
                    sensorLongitude = medicion.getLocX();
                    Log.d(TAG, "Coordenadas del sensor asignadas correctamente");
                    String sensorText = "Latitud: " + sensorLatitude + "\nLongitud: " + sensorLongitude;
                    sensorTextView.setText(sensorText);
                    actualizarDistancia();
                } else {
                    Log.e(TAG, "Las coordenadas del sensor son inválidas");
                }
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Error fetching sensor coordinates", t);
                Toast.makeText(LocationActivity.this, "Error al obtener las coordenadas del sensor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        deviceLatitude = location.getLatitude();
                        deviceLongitude = location.getLongitude();
                        String locationText = "Latitud: " + deviceLatitude + "\nLongitud: " + deviceLongitude;
                        locationTextView.setText(locationText);

                        Log.d(TAG, String.format("Device Location: Lat = %.4f, Lon = %.4f", deviceLatitude, deviceLongitude));

                        // Actualizar la distancia si las coordenadas del sensor ya están disponibles
                        actualizarDistancia();
                    } else {
                        Toast.makeText(LocationActivity.this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarDistancia() {
        if (sensorLatitude != 0 && sensorLongitude != 0 && deviceLatitude != 0 && deviceLongitude != 0) {
            int distance = calcularDistancia(deviceLatitude, deviceLongitude, sensorLatitude, sensorLongitude);
            distanceTextView.setText("Distancia al sensor: " + distance + " m");
            // Log del mensaje formateado
            Log.d(TAG, String.format(
                    "Localizacion{ LatitudSensor: %.6f, LongitudSensor: %.6f, LatitudMovil: %.6f, LongitudMovil: %.6f }",
                    sensorLatitude, sensorLongitude, deviceLatitude, deviceLongitude));

            Log.d(TAG, String.format("Distancia al sensor: %d m", distance));
        } else {
            Log.w(TAG, "No se pueden calcular distancias con coordenadas inválidas");
        }

    }


    // Radio de la Tierra en kilómetros
    private static final double RADIO_TIERRA = 6371.0;

    /**
     * Calcula la distancia entre dos puntos geográficos en una esfera.
     *
     * @param latA Latitud del punto A en grados
     * @param lonA Longitud del punto A en grados
     * @param latB Latitud del punto B en grados
     * @param lonB Longitud del punto B en grados
     * @return La distancia en metros
     */
    public static int calcularDistancia(double latA, double lonA, double latB, double lonB) {
        // Convertir grados a radianes
        double latARad = toRadians(latA);
        double lonARad = toRadians(lonA);
        double latBRad = toRadians(latB);
        double lonBRad = toRadians(lonB);

        // Aplicar la fórmula de distancia entre dos puntos en una esfera
        double deltaLon = lonBRad - lonARad;
        double distanciaAngular = Math.acos(
                sin(latARad) * sin(latBRad) +
                        cos(latARad) * cos(latBRad) * cos(deltaLon)
        );

        // Convertir la distancia angular a distancia física en kilómetros
        double distancia = RADIO_TIERRA * distanciaAngular;
        // Convertir a metros y redondear al entero más cercano
        return (int) Math.round((distancia));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}