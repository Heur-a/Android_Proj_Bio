/**
 * @file Medicion.java
 * @brief Class representing a measurement object.
 *
 * This class models a measurement including the value, location, gas type,
 * and the time the measurement was taken.
 */

package com.example.testsprint0projbio.pojo;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @class Medicion
 * @brief Represents a measurement with details such as value, location, and gas type.
 *
 * This class contains attributes for storing the measurement value, the location
 * where it was taken, the type of gas, and the timestamp of the measurement.
 */
public class Medicion {
    private double medida;      ///< Measurement value
    private String lugar;       ///< Location of the measurement
    private String tipo_gas;    ///< Type of gas being measured
    private final String hora;   ///< Time of the measurement in String format

    /**
     * @brief Constructor for creating a measurement object.
     * @param medida The value of the measurement.
     * @param lugar The location where the measurement was taken.
     * @param tipo_gas The type of gas being measured.
     */
    public Medicion(double medida, String lugar, String tipo_gas) {
        this.medida = medida;
        this.lugar = lugar;
        this.tipo_gas = tipo_gas;

        // Formatting the time as a String in the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        this.hora = sdf.format(new Date()); // Assigning the current time in the correct format
    }

    // Getters

    /**
     * @brief Gets the measurement value.
     * @return The measurement value.
     */
    public double getMedida() {
        return medida;
    }

    /**
     * @brief Gets the location of the measurement.
     * @return The location.
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * @brief Gets the type of gas being measured.
     * @return The type of gas.
     */
    public String getTipo_gas() {
        return tipo_gas;
    }

    /**
     * @brief Gets the time of the measurement.
     * @return The time as a String.
     */
    public String getHora() {
        return hora; // Time is stored as a String
    }

    // Setters

    /**
     * @brief Sets the measurement value.
     * @param medida The new measurement value.
     */
    public void setMedida(double medida) {
        this.medida = medida;
    }

    /**
     * @brief Sets the location of the measurement.
     * @param lugar The new location.
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @brief Sets the type of gas being measured.
     * @param tipo_gas The new type of gas.
     */
    public void setTipo_gas(String tipo_gas) {
        this.tipo_gas = tipo_gas;
    }

    // No setter needed for 'hora', as it is automatically assigned when the object is created

    /**
     * @brief Converts the object to a JSON string.
     * @return A JSON representation of the object as a String.
     */
    public String toJson() {
        JsonObject jsonObject = new JsonObject();

        // Adding fields in the desired order
        jsonObject.addProperty("medida", medida);
        jsonObject.addProperty("lugar", lugar);
        jsonObject.addProperty("tipo_gas", tipo_gas); // Name of the field in JSON
        jsonObject.addProperty("hora", hora);

        return jsonObject.toString(); // Returns the JSON as a string
    }

    /**
     * @brief Converts the object to a String representation.
     * @return A String representation of the Medicion object.
     */
    @NonNull
    @Override
    public String toString() {
        return "Medicion{" +
                "medida=" + medida +
                ", lugar='" + lugar + '\'' +
                ", tipoGas='" + tipo_gas + '\'' +
                ", hora='" + hora + '\'' + // Display as String
                '}';
    }
}
