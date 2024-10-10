/**
 * @file MainActivityTest.java
 * @brief Unit tests for MainActivity in the application.
 *
 * This file contains unit tests for the MainActivity class, ensuring that
 * the activity functions correctly and the UI components are properly initialized.
 */

package com.example.testsprint0projbio;

import android.widget.Button;
import android.widget.TextView;

import com.example.testsprint0projbio.pojo.TramaIBeacon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * @class MainActivityTest
 * @brief Unit tests for MainActivity.
 *
 * This class tests the MainActivity functionality, ensuring that UI components
 * are initialized and respond correctly to user interactions.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30)
public class MainActivityTest {

    private MainActivity activity;
    private Button botonBuscarDispositivosBTLE;
    private Button botonDetenerBusquedaDispositivosBTLE;
    private Button botonBuscarNuestroDispositivoBTLE;
    private Button enviarPostPrueba;
    private TextView showMajor;

    /**
     * @brief Set up the test environment.
     *
     * This method initializes the MainActivity and its UI components before
     * each test, ensuring a fresh state.
     */
    @Before
    public void setUp() {
        // Create the simulated activity
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .start()
                .resume()
                .get();

        // Simulate granting all necessary permissions
        ShadowApplication shadowApplication = shadowOf(activity.getApplication());
        shadowApplication.grantPermissions(
                android.Manifest.permission.BLUETOOTH_CONNECT,
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.BLUETOOTH_SCAN
        );

        // Initialize UI components
        botonBuscarDispositivosBTLE = activity.findViewById(R.id.botonBuscarDispositivosBTLE);
        botonDetenerBusquedaDispositivosBTLE = activity.findViewById(R.id.botonDetenerBusquedaDispositivosBTLE);
        botonBuscarNuestroDispositivoBTLE = activity.findViewById(R.id.botonBuscarNuestroDispositivoBTLE);
        enviarPostPrueba = activity.findViewById(R.id.enviarPostPrueba);
        showMajor = activity.findViewById(R.id.showMajor);
    }

    /**
     * @brief Test that the activity is not null.
     *
     * This test ensures that the MainActivity instance is successfully created.
     */
    @Test
    public void testActivityNotNull() {
        assertNotNull(activity);
    }

    /**
     * @brief Test that all buttons and TextView exist.
     *
     * This test checks that the UI components are properly initialized and not null.
     */
    @Test
    public void testButtonsExist() {
        assertNotNull(botonBuscarDispositivosBTLE);
        assertNotNull(botonDetenerBusquedaDispositivosBTLE);
        assertNotNull(botonBuscarNuestroDispositivoBTLE);
        assertNotNull(enviarPostPrueba);
        assertNotNull(showMajor);
    }

    /**
     * @brief Test the click event of the buscar dispositivos BTLE button.
     *
     * This test simulates a click on the buscar dispositivos BTLE button
     * and checks if the scanning process is initiated.
     */
    @Test
    public void testButtonBuscarDispositivosBTLEClick() {
        botonBuscarDispositivosBTLE.performClick();
        assertTrue(activity.isScanning);
    }

    /**
     * @brief Test the display of the major value in the TextView.
     *
     * This test simulates a TramaIBeacon instance and checks if the
     * showMajor method updates the TextView correctly with the major value.
     */
    @Test
    public void testShowMajorText() {
        activity.tib = new TramaIBeacon(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D});

        activity.isScanningOurBeacon = true;
        activity.showMajor();

        assertEquals("Ppm = [25, 26]", showMajor.getText().toString());
    }
}
