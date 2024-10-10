package com.example.testsprint0projbio.utility;

import static org.junit.Assert.*;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UtilidadesTest {


    @Test
    public void testStringToBytes() {
        String input = "Hello";
        byte[] expected = "Hello".getBytes(StandardCharsets.UTF_8);
        byte[] result = Utilidades.stringToBytes(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBytesToString() {
        byte[] input = "Hello".getBytes(StandardCharsets.UTF_8);
        String expected = "Hello";
        String result = Utilidades.bytesToString(input);
        assertEquals(expected, result);
    }

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

    @Test
    public void testBytesToInt_OK() {
        byte[] input = {0x00, 0x00, 0x00, 0x05}; // Represents the integer 5
        int expected = 5;
        int result = Utilidades.bytesToIntOK(input);
        assertEquals(expected, result);
    }

    @Test(expected = Error.class)
    public void testBytesToInt_OK_TooManyBytes() {
        byte[] input = {0x00, 0x00, 0x00, 0x05, 0x01}; // 5 bytes, should throw Error
        Utilidades.bytesToIntOK(input);
    }

    @Test
    public void testMajorToUnsignedInt_ValidInput() {
        byte[] input = {0x01, 0x02}; // 2 bytes representing the unsigned integer 258
        int expected = 258;
        int result = Utilidades.majorToUnsignedInt(input);
        assertEquals(expected, result);
    }

    @Test
    public void testMajorToUnsignedInt_InvalidInput() {
        byte[] input = {0x01}; // 1 byte, should return 1
        int expected = 1;
        int result = Utilidades.majorToUnsignedInt(input);
        assertEquals(expected, result);
    }

    @Test
    public void testBytesToHexString() {
        byte[] input = {0x0A, 0x1B, 0x2C}; // Example byte array
        String expected = "0a:1b:2c:";
        String result = Utilidades.bytesToHexString(input);
        assertEquals(expected, result);
    }
}