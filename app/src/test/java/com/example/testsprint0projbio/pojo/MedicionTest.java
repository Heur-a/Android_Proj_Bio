/**
 * @file MedicionTest.java
 * @brief Unit tests for the Medicion class.
 *
 * This file contains unit tests for the Medicion class, focusing on its constructor,
 * getter and setter methods, JSON conversion, and string representation.
 */

package com.example.testsprint0projbio.pojo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @class MedicionTest
 * @brief Unit tests for the Medicion class.
 *
 * This class contains tests to verify the functionality of the Medicion class,
 * including testing the constructor, setters and getters, JSON serialization,
 * and string representation.
 */
public class MedicionTest {

    /**
     * @brief Test the Medicion class constructor.
     *
     * This test verifies that the Medicion object is created correctly with
     * the specified measure, place, and gas type, and that the time is not null.
     */
    @Test
    public void testMedicionConstructor() {
        // Arrange
        double medida = 12.5;
        String lugar = "Barcelona";
        String tipo_gas = "CO2";

        // Act
        Medicion medicion = new Medicion(medida, lugar, tipo_gas);

        // Assert
        assertEquals(12.5, medicion.getMedida(), 0.01);
        assertEquals("Barcelona", medicion.getLugar());
        assertEquals("CO2", medicion.getTipo_gas());
        assertNotNull(medicion.getHora()); // Hora should not be null
    }

    /**
     * @brief Test the setters and getters of the Medicion class.
     *
     * This test verifies that the setter methods correctly update the values
     * of the Medicion object's properties.
     */
    @Test
    public void testSettersAndGetters() {
        // Arrange
        Medicion medicion = new Medicion(10.0, "Madrid", "O2");

        // Act
        medicion.setMedida(20.5);
        medicion.setLugar("Valencia");
        medicion.setTipo_gas("NO2");

        // Assert
        assertEquals(20.5, medicion.getMedida(), 0.01);
        assertEquals("Valencia", medicion.getLugar());
        assertEquals("NO2", medicion.getTipo_gas());
    }

    /**
     * @brief Test the JSON serialization of the Medicion class.
     *
     * This test verifies that the toJson method produces the expected JSON
     * representation of the Medicion object.
     */
    @Test
    public void testToJson() {
        // Arrange
        Medicion medicion = new Medicion(25.0, "Sevilla", "CO");

        // Act
        String json = medicion.toJson();

        // Assert
        assertTrue(json.contains("\"medida\":25.0"));
        assertTrue(json.contains("\"lugar\":\"Sevilla\""));
        assertTrue(json.contains("\"tipo_gas\":\"CO\""));
        assertTrue(json.contains("\"hora\":\"")); // Hora will contain the current time, just check its existence
    }

    /**
     * @brief Test the string representation of the Medicion class.
     *
     * This test verifies that the toString method produces a meaningful
     * string representation of the Medicion object.
     */
    @Test
    public void testToString() {
        // Arrange
        Medicion medicion = new Medicion(15.0, "Zaragoza", "CH4");

        // Act
        String result = medicion.toString();

        // Assert
        assertTrue(result.contains("medida=15.0"));
        assertTrue(result.contains("lugar='Zaragoza'"));
        assertTrue(result.contains("tipoGas='CH4'"));
        assertTrue(result.contains("hora=")); // Hora will contain the current time
    }
}
