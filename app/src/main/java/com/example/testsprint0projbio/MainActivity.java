package com.example.testsprint0projbio;

import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.content.ContentValues.TAG;

import static androidx.activity.result.contract.ActivityResultContracts.*;

import android.Manifest.permission;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

// ------------------------------------------------------------------
// ------------------------------------------------------------------

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import com.example.testsprint0projbio.api.PeticionarioRESTWorker;
import com.example.testsprint0projbio.pojo.LoginActivity;
import com.example.testsprint0projbio.utility.Utilidades;
import com.example.testsprint0projbio.pojo.PrincipalActivity;
import com.example.testsprint0projbio.pojo.TramaIBeacon;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import androidx.annotation.Nullable;


// ------------------------------------------------------------------
// Header for MainActivity class
/**
 * @file MainActivity.java
 * @brief Main activity for Bluetooth LE scanning application.
 * @author Alex Escrivá Caravaca
 * @date 30/09/2024
 *
 * This class handles Bluetooth LE scanning, device detection,
 * and communication with a REST API for sending data.
 */
// ------------------------------------------------------------------

// ------------------------------------------------------------------
// ------------------------------------------------------------------

/**
 * @class MainActivity
 * @brief Activity class that manages Bluetooth LE scanning and interaction.
 *
 * This activity is responsible for scanning Bluetooth Low Energy (LE)
 * devices, processing scan results, and communicating with a REST API.
 */
public class MainActivity extends AppCompatActivity {

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    public static final String ETIQUETA_LOG = ">>>>"; ///< Log tag for logging output

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    public BluetoothLeScanner elEscanner; ///< Bluetooth LE scanner instance

    ScanCallback callbackDelEscaneo = null; ///< Callback for Bluetooth scan results

    private final String uuidString = "holaMundoNosVemo"; ///< UUID to filter scanned device
    TramaIBeacon tib; ///< Object to hold iBeacon data

    // Variable per a seguir l'estat de l'escaneig
    boolean isScanningOurBeacon = false; ///< Indicates if our specific beacon is being scanned
    boolean isScanning = false; ///< General scanning status

    public TextView showMajor;  ///< TextView for displaying the major value
    public Button enviarPostPrueba; ///< Button for sending test POST request
    public Button EncenderEnvioPost; ///< Button for enabling post sending

    private String qrResult;



    // --------------------------------------------------------------
    // --------------------------------------------------------------
    /**
     * @brief Starts scanning for Bluetooth LE devices.
     *
     * This method initializes the scan callback and starts scanning
     * for Bluetooth LE devices. It checks for necessary permissions
     * and logs the results.
     *
     * @return void
     */
    void buscarTodosLosDispositivosBTLE() {
        Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): empieza ");

        Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): instalamos scan callback ");

        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult resultado) {
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): onScanResult() ");

                mostrarInformacionDispositivoBTLE(resultado);
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): onBatchScanResults() ");

            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): onScanFailed() ");

            }
        };

        Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): empezamos a escanear ");

        if (ActivityCompat.checkSelfPermission(this, permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermissionLuancher.launch(permission.BLUETOOTH_SCAN);
            }
        }
        this.elEscanner.startScan(this.callbackDelEscaneo);

        isScanning = true;

    } // ()

    // --------------------------------------------------------------
    /**
     * @brief Displays information about detected Bluetooth LE device.
     *
     * This method logs information about the detected device, including
     * its name, address, RSSI, and iBeacon data.
     *
     * @param resultado The ScanResult containing the detected device's information.
     *
     * @return void
     */
    // --------------------------------------------------------------
    private void mostrarInformacionDispositivoBTLE(ScanResult resultado) {


        BluetoothDevice bluetoothDevice = resultado.getDevice();
        byte[] bytes = Objects.requireNonNull(resultado.getScanRecord()).getBytes();
        int rssi = resultado.getRssi();

        Log.d(ETIQUETA_LOG, " ******");
        Log.d(ETIQUETA_LOG, " * DISPOSITIVO DETECTADO BTLE *** ");
        Log.d(ETIQUETA_LOG, " ******");
        if (ActivityCompat.checkSelfPermission(this, BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermissionLuancher.launch(BLUETOOTH_CONNECT);
            }
        }
        Log.d(ETIQUETA_LOG, " nombre = " + bluetoothDevice.getName());
        Log.d(ETIQUETA_LOG, " toString = " + bluetoothDevice);

        Log.d(ETIQUETA_LOG, " dirección = " + bluetoothDevice.getAddress());
        Log.d(ETIQUETA_LOG, " rssi = " + rssi);

        Log.d(ETIQUETA_LOG, " bytes = " + new String(bytes));
        Log.d(ETIQUETA_LOG, " bytes (" + bytes.length + ") = " + Utilidades.bytesToHexString(bytes));

        TramaIBeacon tib = new TramaIBeacon(bytes);

        Log.d(ETIQUETA_LOG, " ----------------------------------------------------");
        Log.d(ETIQUETA_LOG, " prefijo  = " + Utilidades.bytesToHexString(tib.getPrefijo())); // Log the prefix
        Log.d(ETIQUETA_LOG, "          advFlags = " + Utilidades.bytesToHexString(tib.getAdvFlags())); // Log advertising flags
        Log.d(ETIQUETA_LOG, "          advHeader = " + Utilidades.bytesToHexString(tib.getAdvHeader())); // Log advertising header
        Log.d(ETIQUETA_LOG, "          companyID = " + Utilidades.bytesToHexString(tib.getCompanyID())); // Log company ID
        Log.d(ETIQUETA_LOG, "          iBeacon type = " + Integer.toHexString(tib.getiBeaconType())); // Log iBeacon type
        Log.d(ETIQUETA_LOG, "          iBeacon length 0x = " + Integer.toHexString(tib.getiBeaconLength()) + " ( "
                + tib.getiBeaconLength() + " ) ");  // Log iBeacon length
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToHexString(tib.getUUID())); // Log UUID
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToString(tib.getUUID())); // Log UUID as string
        Log.d(ETIQUETA_LOG, " major  = " + Utilidades.bytesToHexString(tib.getMajor()) + "( "
                + Utilidades.bytesToInt(tib.getMajor()) + " ) "); // Log major value
        Log.d(ETIQUETA_LOG, " minor  = " + Utilidades.bytesToHexString(tib.getMinor()) + "( "
                + Utilidades.bytesToInt(tib.getMinor()) + " ) "); // Log minor value
        Log.d(ETIQUETA_LOG, " txPower  = " + Integer.toHexString(tib.getTxPower()) + " ( " + tib.getTxPower() + " )");
        Log.d(ETIQUETA_LOG, " ******"); // Log txPower value

    } // ()
    // --------------------------------------------------------------
    /**
     * @brief Scans for a specific Bluetooth LE device.
     *
     * This method initializes the scan callback for scanning a specific
     * Bluetooth LE device identified by the uuidString. It logs the results
     * and retrieves the corresponding iBeacon information if found.
     *
     * @return void
     */
    // --------------------------------------------------------------
    private void buscarEsteDispositivoBTLE() {
        Log.d(ETIQUETA_LOG, "buscarEsteDispositivoBTLE(): empieza");

        // Verifica si se tienen los permisos de escaneo de Bluetooth
        if (ActivityCompat.checkSelfPermission(this, permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermissionLuancher.launch(permission.BLUETOOTH_SCAN);
            }
            return; // Salir del método si no hay permisos
        }

        // Mostrar un Toast indicando que se está buscando un beacon
        Toast.makeText(this, "Buscando beacon...", Toast.LENGTH_SHORT).show();

        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult resultado) {
                super.onScanResult(callbackType, resultado);
                byte[] bytes = Objects.requireNonNull(resultado.getScanRecord()).getBytes();
                TramaIBeacon scan = new TramaIBeacon(bytes);

                // Verifica si el UUID es igual a ELENAELENAELENAE
                String beaconContent = Utilidades.bytesToString(scan.getUUID()); // Asume que el UUID es el contenido
                if (beaconContent.equals(qrResult)) {

                    try {
                        elEscanner.stopScan(new ScanCallback() {
                            @Override
                            public void onScanResult(int callbackType, ScanResult result) {
                                super.onScanResult(callbackType, result);
                            }
                        });
                    } catch (SecurityException e) {
                        Log.e(TAG, "onScanResult: ",e);
                    }

                    // Mostrar un Toast cuando se detecta el beacon con el UUID específico
                    Toast.makeText(MainActivity.this, "Beacon detectado: " + beaconContent, Toast.LENGTH_SHORT).show();

                    // Lanzar la nueva actividad al detectar el beacon
                    Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, "buscarEsteDispositivoBTLE(): onBatchScanResults()");
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, "buscarEsteDispositivoBTLE(): onScanFailed()");
            }
        };

        Log.d(ETIQUETA_LOG, "buscarEsteDispositivoBTLE(): empezamos");

        try {
            // Iniciar escaneo
            this.elEscanner.startScan(this.callbackDelEscaneo);
            isScanningOurBeacon = true;
        } catch (SecurityException e) {
            Log.e(ETIQUETA_LOG, "Error al iniciar el escaneo: " + e.getMessage());
            Toast.makeText(this, "Permisos necesarios no otorgados.", Toast.LENGTH_SHORT).show();
        }
    }

    // --------------------------------------------------------------
    /**
     * @brief Shows the major value of the detected iBeacon.
     *
     * This method updates the TextView to display the major value
     * of the detected iBeacon, if available.
     *
     * @return void
     */
    // --------------------------------------------------------------
    private void detenerBusquedaDispositivosBTLE() {

        if (this.callbackDelEscaneo == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.checkSelfPermission(this, permission.BLUETOOTH_SCAN);
        }
        this.elEscanner.stopScan(this.callbackDelEscaneo);
        this.callbackDelEscaneo = null;

    } // ()

    // --------------------------------------------------------------
    /**
     * @brief Updates the display with the major value of the detected iBeacon.
     *
     * This method checks if the scanning for the specific iBeacon is active.
     * If so, it retrieves the major value from the tib object, converts it
     * to an integer, and updates the showMajor TextView to display this value.
     * If scanning is not active, it logs a message indicating that scanning
     * is not currently taking place.
     *
     * @return void
     */
    // --------------------------------------------------------------
    void showMajor() {
        Log.d(ETIQUETA_LOG, " showMajor()");

        // Comprovar si s'està escanejant
        if (isScanningOurBeacon) {

            String display = getString(R.string.ppm) + (Utilidades.bytesToInt(tib.getMajor()));
            Log.d(ETIQUETA_LOG, " Major: " + display);
            showMajor.setText(display);

        } else {
            Log.d(ETIQUETA_LOG, " No s'està escanejant.");
        }
    } // ()

    // --------------------------------------------------------------
    /**
     * @brief Handles the button click event for searching Bluetooth LE devices.
     *
     * This method is called when the button for searching Bluetooth LE devices
     * is pressed. It logs the button press event and initiates the scanning
     * process by calling the buscarTodosLosDispositivosBTLE() method.
     *
     * @param v The View that was clicked, typically the button.
     *
     * @return void
     */
    // --------------------------------------------------------------
    public void botonBuscarDispositivosBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, " boton buscar dispositivos BTLE Pulsado");
        this.buscarTodosLosDispositivosBTLE();
    } // ()

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    /**
     * @brief Handles the button click event for searching a specific Bluetooth LE device.
     *
     * This method is called when the button for searching a specific Bluetooth LE device
     * is pressed. It logs the button press event and initiates the scanning process
     * for the specific device by calling the buscarEsteDispositivoBTLE() method.
     *
     * @param v The View that was clicked, typically the button.
     *
     * @return void
     */
    // --------------------------------------------------------------
    public void botonBuscarNuestroDispositivoBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, " boton nuestro dispositivo BTLE Pulsado");
        this.buscarEsteDispositivoBTLE();
    } // ()

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    /**
     * @brief Handles the button click event for stopping the Bluetooth LE device search.
     *
     * This method is called when the button for stopping the search for Bluetooth LE
     * devices is pressed. It logs the button press event and calls the
     * detenerBusquedaDispositivosBTLE() method to stop the scanning process.
     *
     * @param v The View that was clicked, typically the button.
     *
     * @return void
     */
    // --------------------------------------------------------------
    public void botonDetenerBusquedaDispositivosBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, " boton detener busqueda dispositivos BTLE Pulsado");
        this.detenerBusquedaDispositivosBTLE();
    } // ()

    // --------------------------------------------------------------
    /**
     * @brief Handles the button click event for sending a test POST request.
     *
     * This method is called when the button for sending a test POST request is pressed.
     * It logs the button press event and invokes the enviarPostPrueba() method
     * to initiate the sending of the test data.
     *
     * @param v The View that was clicked, typically the button.
     *
     * @return void
     */
    // --------------------------------------------------------------

    public void botonEnviarPostPrueba(View v) {
        Log.d(ETIQUETA_LOG, " boton Enviar Post Pulsado");
        this.enviarPostPrueba();
    }

    //----------------------------------------------------------------
    //----------------------------------------------------------------

    public void botonEnviarLastMajor(View v) {
        Log.d(ETIQUETA_LOG, " boton Enviar Last Major Pulsado");
        this.enviarLastMajor();
    }

    //---------------------------------------------------------------
    /**
     * @brief Handles the button click event for sending the last detected major value.
     *
     * This method is invoked when the button for sending the last detected major value
     * is pressed. It logs the button press event and calls the enviarLastMajor()
     * method to send the last major data to the server.
     *
     * @param v The View that was clicked, typically the button.
     *
     * @return void
     */
    //---------------------------------------------------------------

    /**
     * @brief Initiates the process of sending a test POST request.
     *
     * This private method is called to start the POST request sequence.
     * It invokes the POST_TEST_200() method, which constructs the request
     * and sends it to the specified URL.
     *
     * @return void
     */
    private void enviarPostPrueba() {
        POST_TEST_200();
    }

    // --------------------------------------------------------------
    /**
     * @brief Constructs and enqueues a OneTimeWorkRequest for sending test data.
     *
     * This private method builds the input data required for the POST request,
     * including the HTTP method, URL, and body containing the measurement details.
     * It then creates a OneTimeWorkRequest and enqueues it with the WorkManager
     * to perform the network operation asynchronously.
     *
     * @return void
     */
    private void POST_TEST_200() {
        Data inputData = new Data.Builder()
                .putString(PeticionarioRESTWorker.KEY_METHOD, "POST")
                .putString(PeticionarioRESTWorker.KEY_URL, "http://172.20.10.2:3000/mediciones")
                .putString(PeticionarioRESTWorker.KEY_BODY, "{ \"medida\": 50.5, \"lugar\": \"zonaelena\", \"tipo_gas\": \"CO\", \"hora\": \"2024-09-26 11:00:00\" }")
                .build();
        // Start the Worker to make the request
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(PeticionarioRESTWorker.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(this).enqueue(workRequest);
    }

    //--------------------------------------------------------------
    //--------------------------------------------------------------
    private void enviarLastMajor() {
        PeticionarioRESTWorker.POST(tib, this);
    }

    /**
     * @brief Sets up the activity and initializes UI components.
     *
     * This method is called when the activity is created. It sets
     * the content view, initializes UI components, and sets up button
     * click listeners for sending data to the server.
     *
     * @param savedInstanceState Bundle object containing activity state.
     *
     * @return void
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //qr
        Button buttonQR = findViewById(R.id.button_qr);
        buttonQR.setOnClickListener(v -> openQRCodeScanner());

        //SET XML VARIABLES
        showMajor = findViewById(R.id.showMajor);
        enviarPostPrueba = findViewById(R.id.enviarPostPrueba);
        EncenderEnvioPost = findViewById(R.id.ToggleEnviarPost);

        enviarPostPrueba.setOnClickListener(this::botonEnviarPostPrueba);
        EncenderEnvioPost.setOnClickListener(this::botonEnviarLastMajor);

        //SET SCANNER
        BluetoothAdapter elAdaptadorBT = BluetoothAdapter.getDefaultAdapter();
        this.elEscanner = elAdaptadorBT.getBluetoothLeScanner();

    } // ()

    private void openQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Escanea un código QR");
        integrator.setCameraId(0); // Usa la cámara trasera
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // El usuario canceló el escaneo
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí procesas el contenido del QR escaneado
                String qrContent = result.getContents();

                // Verifica si el contenido tiene exactamente 16 caracteres
                if (qrContent.length() == 16) {
                    // Si el texto tiene 16 caracteres, muestra el contenido del QR
                    Toast.makeText(this, "Contenido del QR válido: " + qrContent, Toast.LENGTH_LONG).show();
                    qrResult = qrContent;

                    // Esperar 5 segundos (5000 ms) antes de comenzar a escanear el beacon
                    new Handler().postDelayed(() -> {
                        // Aquí inicias el escaneo del beacon
                        comenzarEscaneoBeacon();

                    }, 5000);  // Tiempo de espera en milisegundos (5 segundos en este caso)

                } else {
                    // Si no tiene 16 caracteres, muestra un mensaje de QR no válido
                    Toast.makeText(this, "QR no válido.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void comenzarEscaneoBeacon() {
        // Inicia el proceso para escanear beacons BLE
        this.buscarEsteDispositivoBTLE();
    }

    //----------------------------------------------------------------
    /**
     * @brief Launcher for requesting permissions at runtime.
     *
     * This final variable holds an instance of ActivityResultLauncher that
     * is responsible for requesting a specific permission from the user.
     * The result of the permission request is handled through a callback.
     *
     * @note This is initialized with registerForActivityResult() and uses
     * the RequestPermission contract to handle the permission request.
     * When the user responds, it logs whether the permission was granted or denied.
     */
    //----------------------------------------------------------------

    final ActivityResultLauncher<String> requestPermissionLuancher =
            registerForActivityResult(new RequestPermission(), isGranted ->
            {
                if (isGranted) {
                    Log.d(ETIQUETA_LOG, " onRequestPermissionResult(): permisos concedidos  !!!!");
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    Log.d(ETIQUETA_LOG, " onRequestPermissionResult(): Socorro: permisos NO concedidos  !!!!");

                }
            });

} // class
// --------------------------------------------------------------
// --------------------------------------------------------------
// --------------------------------------------------------------
// --------------------------------------------------------------