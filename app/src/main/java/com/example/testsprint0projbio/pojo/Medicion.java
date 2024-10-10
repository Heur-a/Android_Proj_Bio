package com.example.testsprint0projbio.pojo;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Medicion {
    private double medida;
    private String lugar;
    private String tipo_gas;
    private final String hora; // Canviat a String

    // Constructor
    public Medicion(double medida, String lugar, String tipo_gas) {
        this.medida = medida;
        this.lugar = lugar;
        this.tipo_gas = tipo_gas;

        // Formatant l'hora a String amb el format desitjat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        this.hora = sdf.format(new Date()); // Assignar l'hora actual en el format correcte
    }

    // Getters
    public double getMedida() {
        return medida;
    }

    public String getLugar() {
        return lugar;
    }

    public String getTipo_gas() {
        return tipo_gas;
    }

    public String getHora() {
        return hora; // Canviat a String
    }

    // Setters
    public void setMedida(double medida) {
        this.medida = medida;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setTipo_gas(String tipo_gas) {
        this.tipo_gas = tipo_gas;
    }

    // No cal setter per a hora, ja que s'assigna automàticament al crear l'objecte

    // Mètode per a convertir l'objecte a JSON amb l'ordre desitjat
    public String toJson() {
        JsonObject jsonObject = new JsonObject();

        // Afegint els camps en l'ordre desitjat
        jsonObject.addProperty("medida", medida);
        jsonObject.addProperty("lugar", lugar);
        jsonObject.addProperty("tipo_gas", tipo_gas); // Nom del camp en el JSON
        jsonObject.addProperty("hora", hora);

        return jsonObject.toString(); // Retorna el JSON com a cadena
    }

    // Mètode toString per a mostrar l'objecte en format cadena
    @NonNull
    @Override
    public String toString() {
        return "Medicion{" +
                "medida=" + medida +
                ", lugar='" + lugar + '\'' +
                ", tipoGas='" + tipo_gas + '\'' +
                ", hora='" + hora + '\'' + // Mostra com a String
                '}';
    }
}
