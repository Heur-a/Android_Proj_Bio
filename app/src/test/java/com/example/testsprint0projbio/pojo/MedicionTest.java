package com.example.testsprint0projbio.pojo;

import org.junit.Test;
import static org.junit.Assert.*;

public class MedicionTest {

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
