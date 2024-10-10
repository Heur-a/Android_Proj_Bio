package com.example.testsprint0projbio.pojo;

import org.junit.Test;
import static org.junit.Assert.*;

import androidx.annotation.NonNull;

public class TramaIBeaconTest {

    @Test
    public void testTramaIBeaconParsing() {
        // Arrange: Crea un array de bytes que simuli un iBeacon
        TramaIBeacon tramaIBeacon = getTramaIBeacon();

        // Assert: Verifica que els valors són correctes
        assertArrayEquals(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08}, tramaIBeacon.getPrefijo());
        assertArrayEquals(new byte[]{(byte) 0xF1, (byte) 0xF2, (byte) 0xF3, (byte) 0xF4, (byte) 0xF5, (byte) 0xF6, (byte) 0xF7, (byte) 0xF8, (byte) 0xF1, (byte) 0xF2, (byte) 0xF3, (byte) 0xF4, (byte) 0xF5, (byte) 0xF6, (byte) 0xF7, (byte) 0xF8}, tramaIBeacon.getUUID());
        assertArrayEquals(new byte[]{0x10, 0x20}, tramaIBeacon.getMajor());
        assertArrayEquals(new byte[]{0x30, 0x40}, tramaIBeacon.getMinor());
        assertEquals((byte) 0xC5, tramaIBeacon.getTxPower());
    }

    private static @NonNull TramaIBeacon getTramaIBeacon() {
        byte[] testData = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, // Prefix (9 bytes)
                (byte) 0xF1, (byte) 0xF2, (byte) 0xF3, (byte) 0xF4, (byte) 0xF5, (byte) 0xF6, (byte) 0xF7, (byte) 0xF8,// UUID (16 bytes)
                (byte) 0xF1, (byte) 0xF2, (byte) 0xF3, (byte) 0xF4, (byte) 0xF5, (byte) 0xF6, (byte) 0xF7, (byte) 0xF8,// UUID (16 bytes)
                0x10, 0x20, // Major (2 bytes)
                0x30, 0x40, // Minor (2 bytes)
                (byte) 0xC5  // TX Power (1 byte)
        };

        // Act: Crea una instància de TramaIBeacon
        return new TramaIBeacon(testData);
    }
}
