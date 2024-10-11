/**
 * @file Utilidades.java
 * @brief Utility class containing static methods for data manipulation.
 *
 * This class provides various utility methods for converting between strings, bytes, UUIDs, 
 * and integers. It is useful for tasks related to BLE data processing and other general purposes.
 */

package com.example.testsprint0projbio.utility;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @class Utilidades
 * @brief Utility class containing static methods for data conversion.
 */
public class Utilidades {

    /**
     * @brief Converts a String into a byte array.
     * @param texto The String to convert.
     * @return The corresponding byte array for the given String.
     */
    public static byte[] stringToBytes(String texto) {
        return texto.getBytes();
    }

    /**
     * @brief Converts a String into a UUID.
     *
     * This method converts a 16-character String into a UUID by splitting it into 
     * two parts and constructing the UUID from its most and least significant bits.
     *
     * @param uuid The String to convert.
     * @return The corresponding UUID for the given String.
     * @throws Error if the String does not have exactly 16 characters.
     */
    public static UUID stringToUUID(String uuid) {
        if (uuid.length() != 16) {
            throw new Error("stringUUID: the string does not have 16 characters");
        }
        byte[] bytes = uuid.getBytes();

        String mostSignificant = uuid.substring(0, 8);
        String leastSignificant = uuid.substring(8, 16);
        UUID result = new UUID(
                Utilidades.bytesToLong(mostSignificant.getBytes()),
                Utilidades.bytesToLong(leastSignificant.getBytes())
        );

        return result;
    }


    /**
     * @brief Converts a byte array into a String.
     * @param bytes The byte array to convert.
     * @return The corresponding String representation of the byte array.
     */
    public static String bytesToString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append((char) b);
        }
        return sb.toString();
    }

    /**
     * @brief Converts two long values into a byte array.
     * @param mostSignificant The first long value (most significant bits).
     * @param leastSignificant The second long value (least significant bits).
     * @return The corresponding byte array for the two long values.
     */
    public static byte[] dosLongToBytes(long mostSignificant, long leastSignificant) {
        ByteBuffer buffer = ByteBuffer.allocate(2 * Long.BYTES);
        buffer.putLong(mostSignificant);
        buffer.putLong(leastSignificant);
        return buffer.array();
    }

    /**
     * @brief Converts a byte array into an integer.
     * @param bytes The byte array to convert.
     * @return The corresponding integer for the byte array.
     */
    public static int bytesToInt(byte[] bytes) {
        return new BigInteger(bytes).intValue();
    }

    /**
     * @brief Converts a byte array into a long value.
     * @param bytes The byte array to convert.
     * @return The corresponding long value for the byte array.
     */
    public static long bytesToLong(byte[] bytes) {
        return new BigInteger(bytes).longValue();
    }

    /**
     * @brief Converts a byte array into an integer (with safety checks).
     *
     * This method converts a byte array into an integer while checking if 
     * the array contains more than 4 bytes, in which case an error is thrown.
     *
     * @param bytes The byte array to convert.
     * @return The corresponding integer for the byte array.
     * @throws Error if the byte array contains more than 4 bytes.
     */
    public static int bytesToIntOK(byte[] bytes) {
        if (bytes == null) {
            return 0;
        }

        if (bytes.length > 4) {
            throw new Error("Too many bytes to convert to int");
        }

        int result = 0;
        for (byte b : bytes) {
            result = (result << 8) + (b & 0xFF);
        }

        if ((bytes[0] & 0x8) != 0) {
            result = -((byte) result) - 1;
        }

        return result;
    }

    /**
     * @brief Converts a byte array into an unsigned integer.
     *
     * This function converts a byte array of two bytes into a 16-bit unsigned integer.
     * If the byte array does not contain exactly two bytes, it returns 1.
     *
     * @param bytes The byte array to convert (must have a length of 2).
     * @return The corresponding unsigned integer for the two bytes, or 1 if invalid length.
     */
    public static int majorToUnsignedInt(byte[] bytes) {
        if (bytes.length != 2) {
            return 1;
        }
        return ((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF);
    }

    /**
     * @brief Converts a byte array into a hexadecimal String.
     * @param bytes The byte array to convert.
     * @return The corresponding hexadecimal String for the byte array.
     */
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b)).append(':');
        }
        return sb.toString();
    }

}
