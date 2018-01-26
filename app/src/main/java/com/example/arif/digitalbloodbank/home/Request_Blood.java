package com.example.arif.digitalbloodbank.home;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.arif.digitalbloodbank.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Request_Blood extends Fragment {

    public static final String TAG = "Request_Blood_Frag";
    private Button btn_post_new;

    View view;

    private static final int Time_id = 1;
    private static final int Date_id = 0;


    int year, month, day,mHour,mMinute;



    Calendar mcurrentDate=Calendar.getInstance();
    private EditText name, details, phone;
    private Spinner BloodGroup, Location;
    private Button btnPost, btnCancel, btnTimePicker, btnDatePicker;

    // FireBase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseuser;
    private FirebaseUser currentUser;

    String date1, time1, Str_bloodGroup, Str_Location, Str_name, Str_details, Str_phone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_request__blood, container, false);

        init();
        btnListener();
        itemlistener();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseuser = FirebaseDatabase.getInstance().getReference("user");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*btn_post_new = view.findViewById(R.id.req_post_new);


        btn_post_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent post_new = new Intent(getActivity(), Post_New.class);
                startActivity(post_new);
            }
        });*/

    }

    private void btnListener() {
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Time Pick ", Toast.LENGTH_SHORT).show();

                mHour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
                mMinute = mcurrentDate.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {



                                String AmPm = "", Hours = "";
                                if (hourOfDay == 00 || hourOfDay == 12) {
                                    Hours = "12";
                                } else {
                                    Hours = Integer.toString(hourOfDay % 12);
                                }

                                if (hourOfDay >= 12) {
                                    AmPm = " PM";
                                } else {
                                    AmPm = " AM";
                                }

                                StringBuilder STR_BU_TM = new StringBuilder().append(Hours).append(":").append(mMinute).append(" ").append(AmPm);

                                time1 = STR_BU_TM.toString();
                                Log.i(TAG, time1);

                                btnTimePicker.setText(time1);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();




                // showDialog(Time_id);
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                year=mcurrentDate.get(Calendar.YEAR);
                month=mcurrentDate.get(Calendar.MONTH);
                day=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
                {

                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                    {
                        StringBuilder StrBu = new StringBuilder().append(selectedyear).append("-").append(selectedmonth+1).append("-").append(selectedday);

                        date1 = StrBu.toString();
                        btnDatePicker.setText(date1);
                        int month_k=selectedmonth+1;

                        Log.i(TAG, date1);

                    }
                },year, month, day);
                mDatePicker.setTitle("Please select date");


                // TODO Hide Future Date Here
                //mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

                // TODO Hide Past Date Here
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                mDatePicker.show();


                //showDialog(Date_id);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //finish();
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
                    Toast.makeText(getActivity(), "Posted Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void getValue() {
        name = view.findViewById(R.id.post_name);
        details = view.findViewById(R.id.post_hospital);
        phone = view.findViewById(R.id.post_phone);
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


        btnPost = view.findViewById(R.id.post_post);
        btnCancel = view.findViewById(R.id.post_cancel);
        btnTimePicker = view.findViewById(R.id.post_time_picker);
        btnDatePicker = view.findViewById(R.id.post_date_picker);

        BloodGroup = view.findViewById(R.id.post_blood_group);
        Location = view.findViewById(R.id.post_location);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Blood_array, R.layout.custom_spinner);
        BloodGroup.setPrompt("Choose Blood Group");

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        // Apply the adapter to the spinner
        BloodGroup.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
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
                Toast.makeText(getActivity(), "Required Blood Group", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
    }





}
