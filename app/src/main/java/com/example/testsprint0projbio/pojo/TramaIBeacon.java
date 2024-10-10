/**
 * @file TramaIBeacon.java
 * @brief This class represents the structure of an iBeacon frame.
 *
 * The iBeacon frame is typically used for proximity marketing and location-based services,
 * and consists of multiple fields such as a UUID, major and minor numbers, and signal strength.
 * This class extracts and stores these fields from the raw byte data of a Bluetooth Low Energy (BLE) scan.
 */

package com.example.testsprint0projbio.pojo;

import java.util.Arrays;

/**
 * @class TramaIBeacon
 * @brief Class representing an iBeacon frame extracted from BLE scan data.
 *
 * This class parses the byte array from a Bluetooth LE scan to extract the iBeacon fields like UUID,
 * major, minor, and TX power.
 */
public class TramaIBeacon {

    /**
     * @brief Prefix containing various iBeacon information (9 bytes).
     */
    private byte[] prefijo = null; // 9 bytes

    /**
     * @brief The UUID of the iBeacon (16 bytes).
     */
    private byte[] uuid = null; // 16 bytes

    /**
     * @brief Major value of the iBeacon (2 bytes).
     */
    private byte[] major = null; // 2 bytes

    /**
     * @brief Minor value of the iBeacon (2 bytes).
     */
    private byte[] minor = null; // 2 bytes

    /**
     * @brief Transmission power of the iBeacon (1 byte).
     */
    private byte txPower = 0; // 1 byte

    /**
     * @brief The complete byte array containing the iBeacon data.
     */
    private byte[] losBytes;

    /**
     * @brief Advertising flags from the BLE scan (3 bytes).
     */
    private byte[] advFlags = null; // 3 bytes

    /**
     * @brief Advertising header containing length and type (2 bytes).
     */
    private byte[] advHeader = null; // 2 bytes

    /**
     * @brief The company identifier (Apple in this case) (2 bytes).
     */
    private byte[] companyID = new byte[2]; // 2 bytes

    /**
     * @brief Type of iBeacon (1 byte).
     */
    private byte iBeaconType = 0; // 1 byte

    /**
     * @brief Length of the iBeacon data (1 byte).
     */
    private byte iBeaconLength = 0; // 1 byte

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the iBeacon prefix.
     * @return A byte array representing the iBeacon prefix (9 bytes).
     */
    public byte[] getPrefijo() {
        return prefijo;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the iBeacon UUID.
     * @return A byte array representing the iBeacon UUID (16 bytes).
     */
    public byte[] getUUID() {
        return uuid;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the iBeacon major value.
     * @return A byte array representing the iBeacon major value (2 bytes).
     */
    public byte[] getMajor() {
        return major;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the iBeacon minor value.
     * @return A byte array representing the iBeacon minor value (2 bytes).
     */
    public byte[] getMinor() {
        return minor;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the iBeacon transmission power.
     * @return A byte representing the iBeacon transmission power (TX Power).
     */
    public byte getTxPower() {
        return txPower;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the complete byte array containing the iBeacon frame.
     * @return A byte array representing the entire iBeacon frame.
     */
    public byte[] getLosBytes() {
        return losBytes;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the advertising flags from the BLE scan.
     * @return A byte array representing the advertising flags (3 bytes).
     */
    public byte[] getAdvFlags() {
        return advFlags;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the advertising header containing length and type.
     * @return A byte array representing the advertising header (2 bytes).
     */
    public byte[] getAdvHeader() {
        return advHeader;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the company identifier (usually 2 bytes).
     * @return A byte array representing the company ID (2 bytes).
     */
    public byte[] getCompanyID() {
        return companyID;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the iBeacon type.
     * @return A byte representing the iBeacon type.
     */
    public byte getiBeaconType() {
        return iBeaconType;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Gets the length of the iBeacon data.
     * @return A byte representing the length of the iBeacon data.
     */
    public byte getiBeaconLength() {
        return iBeaconLength;
    }

    // -------------------------------------------------------------------------------

    /**
     * @brief Constructor to parse the byte array and extract iBeacon fields.
     * @param bytes The byte array containing the BLE scan data.
     *
     * This constructor takes the raw byte array from a Bluetooth LE scan and extracts
     * relevant iBeacon data fields such as the prefix, UUID, major, minor, and TX power.
     */
    public TramaIBeacon(byte[] bytes) {
        this.losBytes = bytes;

        // Extracting the iBeacon fields from the byte array
        prefijo = Arrays.copyOfRange(losBytes, 0, 8 + 1); // 9 bytes
        uuid = Arrays.copyOfRange(losBytes, 9, 24 + 1); // 16 bytes
        major = Arrays.copyOfRange(losBytes, 25, 26 + 1); // 2 bytes
        minor = Arrays.copyOfRange(losBytes, 27, 28 + 1); // 2 bytes
        txPower = losBytes[29]; // 1 byte

        // Extracting advertising fields
        advFlags = Arrays.copyOfRange(prefijo, 0, 2 + 1); // 3 bytes
        advHeader = Arrays.copyOfRange(prefijo, 3, 4 + 1); // 2 bytes
        companyID = Arrays.copyOfRange(prefijo, 5, 6 + 1); // 2 bytes
        iBeaconType = prefijo[7]; // 1 byte
        iBeaconLength = prefijo[8]; // 1 byte
    }
}
