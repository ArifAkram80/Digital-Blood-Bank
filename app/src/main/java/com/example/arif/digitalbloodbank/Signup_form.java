package com.example.arif.digitalbloodbank;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Signup_form extends AppCompatActivity {

    private Spinner BloodGroup, Gender;
    String bloodGroup = "Choose Blood", gender = "I am";

    Button btnLoc;

    GoogleLocation location ;

    String Loca = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        setTitle("Registration Form");
        location = new GoogleLocation(this);
        init();
        itemlistener();

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location.GetSingleUpdateLocation();
                if (location.getLocation_Co_oridinate() != null)
                {
                    Loca = location.getLocation_Co_oridinate().toString();
                }
            }
        });





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 111) {
            Log.i("SignUp", "Permission Under Constraction");
            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    location.locationManager.requestSingleUpdate(location.criteria, location.locationListener, location.looper);
                }
            }
            else{
              //  Toast.makeText(this, "Location Is Required", Toast.LENGTH_SHORT).show();
                Log.i("SignUp", "Permission Not Granted");
            }
        }
    }


    private void init() {

        btnLoc = (Button) findViewById(R.id.location);

        BloodGroup = (Spinner) findViewById(R.id.BloodGroup);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Blood_array, R.layout.custom_spinner);
        BloodGroup.setPrompt("Choose Blood");
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner);
// Apply the adapter to the spinner
        BloodGroup.setAdapter(adapter);


        Gender = (Spinner) findViewById(R.id.Gender);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Gender_Array, R.layout.custom_spinner);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(R.layout.custom_spinner);
// Apply the adapter2 to the spinner
        Gender.setAdapter(adapter2);

    }

    private void itemlistener() {
        BloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bloodGroup = BloodGroup.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Signup_form.this, "Required Blood Group", Toast.LENGTH_SHORT).show();
                Log.d("Spinner", bloodGroup);

            }
        });

        Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = Gender.getItemAtPosition(i).toString();
                Log.d("Spinner", gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Signup_form.this, "Required Blood Group", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
