package com.example.arif.digitalbloodbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Hospital_Map extends AppCompatActivity {

    MAPFragment mHospitalsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital__map);
        getSupportFragmentManager().beginTransaction().add(R.id.FragMap, new MAPFragment()).commit();
    }
}
