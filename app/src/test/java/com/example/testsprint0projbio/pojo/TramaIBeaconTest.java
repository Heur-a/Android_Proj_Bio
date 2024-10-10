/**
 * @file TramaIBeaconTest.java
 * @brief Unit tests for the TramaIBeacon class.
 *
 * This file contains unit tests for the TramaIBeacon class, focusing on
 * parsing functionality and verifying the correctness of its attributes.
 */

package com.example.testsprint0projbio.pojo;

import org.junit.Test;
import static org.junit.Assert.*;

import androidx.annotation.NonNull;

/**
 * @class TramaIBeaconTest
 * @brief Unit tests for the TramaIBeacon class.
 *
 * This class contains tests to verify the functionality of the TramaIBeacon class,
 * particularly the parsing of iBeacon data and the accuracy of its getter methods.
 */
public class TramaIBeaconTest {

    /**
     * @brief Test the parsing of iBeacon data in the TramaIBeacon class.
     *
     * This test verifies that the TramaIBeacon object correctly parses
     * the given byte array into its respective attributes such as prefix, UUID,
     * major, minor, and TX power.
     */
    @Test
    public void testTramaIBeaconParsing() {
        // Arrange: Create a byte array simulating an iBeacon
        TramaIBeacon tramaIBeacon = getTramaIBeacon();

        // Assert: Verify that the values are correct
        assertArrayEquals(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08}, tramaIBeacon.getPrefijo());
        assertArrayEquals(new byte[]{(byte) 0xF1, (byte) 0xF2, (byte) 0xF3, (byte) 0xF4, (byte) 0xF5, (byte) 0xF6, (byte) 0xF7, (byte) 0xF8, (byte) 0xF1, (byte) 0xF2, (byte) 0xF3, (byte) 0xF4, (byte) 0xF5, (byte) 0xF6, (byte) 0xF7, (byte) 0xF8}, tramaIBeacon.getUUID());
        assertArrayEquals(new byte[]{0x10, 0x20}, tramaIBeacon.getMajor());
        assertArrayEquals(new byte[]{0x30, 0x40}, tramaIBeacon.getMinor());
        assertEquals((byte) 0xC5, tramaIBeacon.getTxPower());
    }

    /**
     * @brief Helper method to create a TramaIBeacon instance with test data.
     *
     * This method generates a byte array containing simulated iBeacon data and
     * returns a new instance of the TramaIBeacon class.
     *
     * @return A non-null instance of TramaIBeacon populated with test data.
     */
    private static @NonNull TramaIBeacon getTramaIBeacon() {
        byte[] testData = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, // Prefix (9 bytes)
                (byte) 0xF1, (byte) 0xF2, (byte) 0xF3, (byte) 0xF4, (byte) 0xF5, (byte) 0xF6, (byte) 0xF7, (byte) 0xF8,// UUID (16 bytes)
                (byte) 0xF1, (byte) 0xF2, (byte) 0xF3, (byte) 0xF4, (byte) 0xF5, (byte) 0xF6, (byte) 0xF7, (byte) 0xF8,// UUID (16 bytes)
                0x10, 0x20, // Major (2 bytes)
                0x30, 0x40, // Minor (2 bytes)
                (byte) 0xC5  // TX Power (1 byte)
        };

        // Act: Create an instance of TramaIBeacon
        return new TramaIBeacon(testData);
    }
}
