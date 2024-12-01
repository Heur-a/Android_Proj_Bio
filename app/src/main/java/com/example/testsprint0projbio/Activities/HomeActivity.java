package com.example.testsprint0projbio.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testsprint0projbio.R;
import com.example.testsprint0projbio.services.MedidasSensorHandlerService;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        //start backgound service
        startService(new Intent(this, MedidasSensorHandlerService.class));
    }
}




