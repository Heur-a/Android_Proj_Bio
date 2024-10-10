/**
 * @file UtilidadesTest.java
 * @brief Unit tests for the Utilidades utility class.
 *
 * This file contains unit tests for the Utilidades class, focusing on
 * various utility methods such as string and byte conversions.
 */

package com.example.testsprint0projbio.utility;

import static org.junit.Assert.*;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @class UtilidadesTest
 * @brief Unit tests for the Utilidades class.
 *
 * This class tests the methods in the Utilidades class to ensure their
 * correct functionality, including string to bytes conversion, bytes to
 * string conversion, and various other utility functions.
 */
public class UtilidadesTest {

    /**
     * @brief Test the stringToBytes method.
     *
     * This test verifies that the stringToBytes method correctly converts a
     * string into its byte representation using UTF-8 encoding.
     */
    @Test
    public void testStringToBytes() {
        String input = "Hello";
        byte[] expected = "Hello".getBytes(StandardCharsets.UTF_8);
        byte[] result = Utilidades.stringToBytes(input);
        assertArrayEquals(expected, result);
    }

    /**
     * @brief Test the bytesToString method.
     *
     * This test checks that the bytesToString method converts a byte array
     * back into the correct string representation.
     */
    @Test
    public void testBytesToString() {
        byte[] input = "Hello".getBytes(StandardCharsets.UTF_8);
        String expected = "Hello";
        String result = Utilidades.bytesToString(input);
        assertEquals(expected, result);
    }

    /**
     * @brief Test the dosLongToBytes method.
     *
     * This test verifies that the dosLongToBytes method converts two long
     * values into their correct byte array representation.
     */
    @Test
    public void testDosLongToBytes() {
        long msb = 0x1234567890abcdefL; // Most Significant Bits
        long lsb = 0xfedcba0987654321L; // Least Significant Bits
        byte[] expected = new byte[] {
                0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xab, (byte) 0xcd, (byte) 0xef,
                (byte) 0xfe, (byte) 0xdc, (byte) 0xba, 0x09, (byte) 0x87, 0x65, 0x43, 0x21
        };
        byte[] result = Utilidades.dosLongToBytes(msb, lsb);
        assertArrayEquals(expected, result);
    }

    /**
     * @brief Test the bytesToIntOK method for valid input.
     *
     * This test checks that bytesToIntOK correctly converts a 4-byte array
     * to an integer.
     */
    @Test
    public void testBytesToInt_OK() {
        byte[] input = {0x00, 0x00, 0x00, 0x05}; // Represents the integer 5
        int expected = 5;
        int result = Utilidades.bytesToIntOK(input);
        assertEquals(expected, result);
    }

    /**
     * @brief Test the bytesToIntOK method for too many bytes.
     *
     * This test verifies that bytesToIntOK throws an Error when provided with
     * a byte array longer than 4 bytes.
     */
    @Test(expected = Error.class)
    public void testBytesToInt_OK_TooManyBytes() {
        byte[] input = {0x00, 0x00, 0x00, 0x05, 0x01}; // 5 bytes, should throw Error
        Utilidades.bytesToIntOK(input);
    }

    /**
     * @brief Test the majorToUnsignedInt method with valid input.
     *
     * This test checks that majorToUnsignedInt correctly converts a 2-byte
     * array to an unsigned integer.
     */
    @Test
    public void testMajorToUnsignedInt_ValidInput() {
        byte[] input = {0x01, 0x02}; // 2 bytes representing the unsigned integer 258
        int expected = 258;
        int result = Utilidades.majorToUnsignedInt(input);
        assertEquals(expected, result);
    }

    /**
     * @brief Test the majorToUnsignedInt method with invalid input.
     *
     * This test verifies that majorToUnsignedInt returns 1 when provided
     * with only 1 byte.
     */
    @Test
    public void testMajorToUnsignedInt_InvalidInput() {
        byte[] input = {0x01}; // 1 byte, should return 1
        int expected = 1;
        int result = Utilidades.majorToUnsignedInt(input);
        assertEquals(expected, result);
    }

    /**
     * @brief Test the bytesToHexString method.
     *
     * This test checks that bytesToHexString correctly converts a byte
     * array into its hexadecimal string representation.
     */
    @Test
    public void testBytesToHexString() {
        byte[] input = {0x0A, 0x1B, 0x2C}; // Example byte array
        String expected = "0a:1b:2c:";
        String result = Utilidades.bytesToHexString(input);
        assertEquals(expected, result);
    }
}
