/**
 * @file Medida.java
 * @brief Class representing a measurement object.
 *
 * This class models a measurement including the value, location, gas type,
 * and the time the measurement was taken.
 */

package com.example.testsprint0projbio.pojo;

import static android.content.ContentValues.TAG;

import android.location.Location;
import android.util.Log;

/**
 * Represents a measurement with various attributes such as value, location, gas ID, and UUID.
 */
public class Medicion {

    /**
     * The measured value.
     */
    private float value;

    /**
     * The X-coordinate of the location where the measurement was taken.
     */
    private float LocX;

    /**
     * The Y-coordinate of the location where the measurement was taken.
     */
    private float LocY;

    /**
     * The identifier for the type of gas measured.
     */
    private long gasId;

    /**
     * The unique identifier for this measurement.
     */
    private String uuid;

    /**
     * Default constructor.
     * Initializes a Medida object with default values.
     */
    public Medicion() {
    }

    /**
     * Constructs a Medida object with specified values.
     *
     * @param value the measured value.
     * @param locX  the X-coordinate of the location.
     * @param locY  the Y-coordinate of the location.
     * @param gasId the gas type identifier.
     * @param uuid  the unique identifier for this measurement.
     */
    public Medicion(float value, float locX, float locY, long gasId, String uuid) {
        this.value = value;
        this.LocX = locX;
        this.LocY = locY;
        this.gasId = gasId;
        this.uuid = uuid;
    }

    public Medicion(float value, float locX, float locY, long gasId) {
        this.value = value;
        LocX = locX;
        LocY = locY;
        this.gasId = gasId;
    }

    public Medicion (Location location, float value, long gasId, String uuid) {

        if (location != null) {
            this.LocX = ((float) location.getLongitude());
            this.LocY = ((float) location.getLatitude());
        } else {
            Log.w(TAG, "Current location is null. Using default values.");
            this.LocX = (0.0f); // Valor per defecte
            this.LocY = (0.0f); // Valor per defecte
        }

        this.value = value;
        this.gasId = gasId;
        this.uuid = uuid;
    }

    /**
     * Gets the measured value.
     *
     * @return the measured value.
     */
    public float getValue() {
        return value;
    }

    /**
     * Sets the measured value.
     *
     * @param value the measured value to set.
     */
    public void setValue(float value) {
        this.value = value;
    }

    /**
     * Gets the X-coordinate of the location.
     *
     * @return the X-coordinate of the location.
     */
    public float getLocX() {
        return LocX;
    }

    /**
     * Sets the X-coordinate of the location.
     *
     * @param locX the X-coordinate to set.
     */
    public void setLocX(float locX) {
        this.LocX = locX;
    }

    /**
     * Gets the Y-coordinate of the location.
     *
     * @return the Y-coordinate of the location.
     */
    public float getLocY() {
        return LocY;
    }

    /**
     * Sets the Y-coordinate of the location.
     *
     * @param locY the Y-coordinate to set.
     */
    public void setLocY(float locY) {
        this.LocY = locY;
    }

    /**
     * Gets the gas type identifier.
     *
     * @return the gas type identifier.
     */
    public long getGasId() {
        return gasId;
    }

    /**
     * Sets the gas type identifier.
     *
     * @param gasId the gas type identifier to set.
     */
    public void setGasId(long gasId) {
        this.gasId = gasId;
    }

    /**
     * Gets the unique identifier for this measurement.
     *
     * @return the unique identifier (UUID).
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the unique identifier for this measurement.
     *
     * @param uuid the unique identifier to set.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Provides a string representation of the Medida object.
     *
     * @return a string representing the Medida object.
     */
    @Override
    public String toString() {
        return "Medida{" +
                "value=" + value +
                ", locX=" + LocX +
                ", locY=" + LocY +
                ", gasId=" + gasId +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}

