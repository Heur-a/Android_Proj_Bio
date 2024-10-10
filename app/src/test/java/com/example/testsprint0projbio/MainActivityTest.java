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

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30)
public class MainActivityTest {

    private MainActivity activity;
    private Button botonBuscarDispositivosBTLE;
    private Button botonDetenerBusquedaDispositivosBTLE;
    private Button botonBuscarNuestroDispositivoBTLE;
    private Button enviarPostPrueba;
    private TextView showMajor;

    @Before
    public void setUp() {
        // Crear l'activitat simulada
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .start()
                .resume()
                .get();

        // Simular que s'han concedit tots els permisos necessaris
        ShadowApplication shadowApplication = shadowOf(activity.getApplication());
        shadowApplication.grantPermissions(
                android.Manifest.permission.BLUETOOTH_CONNECT,
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.BLUETOOTH_SCAN
        );

        // Inicialitzar els components UI
        botonBuscarDispositivosBTLE = activity.findViewById(R.id.botonBuscarDispositivosBTLE);
        botonDetenerBusquedaDispositivosBTLE = activity.findViewById(R.id.botonDetenerBusquedaDispositivosBTLE);
        botonBuscarNuestroDispositivoBTLE = activity.findViewById(R.id.botonBuscarNuestroDispositivoBTLE);
        enviarPostPrueba = activity.findViewById(R.id.enviarPostPrueba);
        showMajor = activity.findViewById(R.id.showMajor);
    }

    @Test
    public void testActivityNotNull() {
        // Comprovar que l'activitat no és null
        assertNotNull(activity);
    }

    @Test
    public void testButtonsExist() {
        // Comprovar que els botons i TextView existeixen
        assertNotNull(botonBuscarDispositivosBTLE);
        assertNotNull(botonDetenerBusquedaDispositivosBTLE);
        assertNotNull(botonBuscarNuestroDispositivoBTLE);
        assertNotNull(enviarPostPrueba);
        assertNotNull(showMajor);
    }

    @Test
    public void testButtonBuscarDispositivosBTLEClick() {
        // Simular el clic en el botó de buscar dispositius BTLE
        botonBuscarDispositivosBTLE.performClick();

        // Comprovar que es va iniciar l'escaneig
        assertTrue(activity.isScanning);
    }

    @Test
    public void testShowMajorText() {
        // Simular un valor major
        activity.tib = new TramaIBeacon(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D});

        activity.isScanningOurBeacon = true;
        // Simular el clic per mostrar el valor major
        activity.showMajor();

        // Comprovar que el TextView s'actualitza correctament
        assertEquals("Ppm = [25, 26]", showMajor.getText().toString());
    }
}
