package com.example.testsprint0projbio.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.testing.WorkManagerTestInitHelper;

import com.example.testsprint0projbio.pojo.TramaIBeacon;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RobolectricTestRunner;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @class PeticionarioRESTWorkerTest
 * @brief Unit test for PeticionarioRESTWorker class focusing on POST request functionality.
 *
 * This test uses Robolectric to simulate the Android environment and ensure the REST POST request
 * works as expected.
 */
@RunWith(RobolectricTestRunner.class)
public class PeticionarioRESTWorkerTest {

    private Context context;

    /**
     * @brief Set up the environment before each test.
     *
     * Initialize the WorkManager for testing purposes and ensure proper configuration
     * of the test environment.
     */
    @Before
    public void setUp() {
        // Context setup using Robolectric's ApplicationProvider
        context = ApplicationProvider.getApplicationContext();

        // Initialize WorkManager for testing
        WorkManagerTestInitHelper.initializeTestWorkManager(context);
    }

    /**
     * @brief Test for successfully sending a POST request with valid data.
     *
     * This test verifies the correct behavior of the POST method when provided
     * with valid measurement data in the form of a TramaIBeacon object.
     */
    @Test
    public void testPostTramaIBeacon_Success() throws ExecutionException, InterruptedException {
        // Mock the TramaIBeacon object with valid data
        TramaIBeacon tib = mock(TramaIBeacon.class);
        when(tib.getMajor()).thenReturn(new byte[]{0x00, 0x0A}); // Mocked major value

        // Call the POST method to create the OneTimeWorkRequest
        PeticionarioRESTWorker.POST(tib, context);

        // Verify that WorkManager enqueued the work
        WorkManager workManager = WorkManager.getInstance(context);
        List<WorkInfo> workInfos = workManager.getWorkInfosByTag(PeticionarioRESTWorker.class.getName()).get();

        // Ensure that work was correctly enqueued
        assertNotNull(workInfos);
        assertEquals(1, workInfos.size());

        // Retrieve the first WorkInfo (should only be one)
        WorkInfo workInfo = workInfos.get(0);

        // Assert the WorkInfo is in a successful state
        //assertEquals(WorkInfo.State.ENQUEUED, workInfo.getState());

        // Wait for work to complete (asynchronously) to test the result
        workManager.getWorkInfosForUniqueWork(PeticionarioRESTWorker.class.getName()).get();

        // Assert work completion and successful output data
        //workInfo = workInfos.get(0);
        //assertEquals(WorkInfo.State.SUCCEEDED, workInfo.getState());

        // Verify output data contains the response code and body
        Data outputData = workInfo.getOutputData();
        int responseCode = outputData.getInt(PeticionarioRESTWorker.KEY_RESPONSE_CODE, -1);
        String responseBody = outputData.getString(PeticionarioRESTWorker.KEY_RESPONSE_BODY);

        // Ensure the response code is 201 (Created)
        assertEquals(201, responseCode);

        // Ensure that the response body is not null
        assertNotNull(responseBody);
        Log.d("TestResponse", "Response body: " + responseBody);
    }
}
