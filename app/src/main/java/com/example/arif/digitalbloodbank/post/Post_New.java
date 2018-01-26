package com.example.arif.digitalbloodbank.post;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.arif.digitalbloodbank.Login;
import com.example.arif.digitalbloodbank.R;
import com.example.arif.digitalbloodbank.Signup_form;
import com.example.arif.digitalbloodbank.home.Home_Activity;
import com.example.arif.digitalbloodbank.home.Notice_Frag;
import com.example.arif.digitalbloodbank.home.Post;
import com.example.arif.digitalbloodbank.home.Request_Blood;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Post_New extends AppCompatActivity {
    private static final int Time_id = 1;
    private static final int Date_id = 0;


    private EditText name, details, phone;
    private Spinner BloodGroup, Location;
    private Button btnPost, btnCancel, btnTimePicker, btnDatePicker;

    // FireBase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseuser;
    private FirebaseUser currentUser;

    String date1, time1, Str_bloodGroup, Str_Location, Str_name, Str_details, Str_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__new);
        init();
        btnListener();
        itemlistener();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseuser = FirebaseDatabase.getInstance().getReference("user");

    }

    private void btnListener() {
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Time Pick ", Toast.LENGTH_SHORT).show();
                showDialog(Time_id);
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(Date_id);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getValue();
                if (check()) {

                    Post p = new Post(Str_name, Str_details, Str_phone, time1, date1, Str_bloodGroup, Str_Location, currentUser.getUid());


                    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("user");
                    data = data.push();
                    data.setValue(p);
                    finish();
                }
            }
        });


    }

    private void getValue() {
        name = findViewById(R.id.post_name);
        details = findViewById(R.id.post_hospital);
        phone = findViewById(R.id.post_phone);
        Str_name = name.getText().toString();
        Str_details = details.getText().toString();
        Str_phone = phone.getText().toString();

    }

    private boolean check() {
        if (!TextUtils.isEmpty(Str_phone) && !TextUtils.isEmpty(Str_name) && !TextUtils.isEmpty(Str_details) && !TextUtils.isEmpty(time1) && !TextUtils.isEmpty(date1) && !TextUtils.isEmpty(Str_bloodGroup) && !TextUtils.isEmpty(Str_Location))
            return true;
        else
            return false;
    }

    private void init() {


        btnPost = findViewById(R.id.post_post);
        btnCancel = findViewById(R.id.post_cancel);
        btnTimePicker = findViewById(R.id.post_time_picker);
        btnDatePicker = findViewById(R.id.post_date_picker);

        BloodGroup = findViewById(R.id.post_blood_group);
        Location = findViewById(R.id.post_location);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Blood_array, R.layout.custom_spinner);
        BloodGroup.setPrompt("Choose Blood Group");

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        // Apply the adapter to the spinner
        BloodGroup.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Location_Array, R.layout.custom_spinner);
        Location.setPrompt("Choose Location");
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        // Apply the adapter to the spinner
        Location.setAdapter(adapter2);


    }

    private void itemlistener() {
        BloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Str_bloodGroup = BloodGroup.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Required Blood Group", Toast.LENGTH_SHORT).show();

            }
        });


        Location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Str_Location = Location.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(Post_New.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(Post_New.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, time_listener, hour,
                        minute, false);

        }
        return null;
    }


    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String AmPm = "", Hours = "";
            if (hour == 00 || hour == 12) {
                Hours = "12";
            } else {
                Hours = Integer.toString(hour % 12);
            }

            if (hour >= 12) {
                AmPm = " PM";
            } else {
                AmPm = " AM";
            }
            time1 = Hours + ":" + String.valueOf(minute) + AmPm;
            btnTimePicker.setText(time1);
        }
    };


    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            date1 = String.valueOf(month+1) + "/" + String.valueOf(day)
                    + "/" + String.valueOf(year);
            btnDatePicker.setText(date1);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
    }

}
