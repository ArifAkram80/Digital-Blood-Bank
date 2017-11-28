package com.example.arif.digitalbloodbank;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arif.digitalbloodbank.home.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.os.Build.USER;

public class Signup_form extends AppCompatActivity {


    // Resource Sppiner
    private Spinner BloodGroup, Gender;

    // Resource Button
    private Button btnLoc, btnRegi;

    // Resource Edit Text
    EditText v_username, v_password, v_Email, v_phone_number;


    static String ChooseBlood = "Choose Blood";
    static String ChooseGender = "I am";
    static String EmptyString = "";

    //Dialog
    private ProgressDialog dialog;

    // FireBase
   private FirebaseAuth firebaseAuth;
   private DatabaseReference databaseuser;


    // Default Value
    String Str_bloodGroup = "", Str_gender = "", Str_Location = "", Str_user_name = "", Str_password = "", Str_Email = "", Str_phone_number = "";
    Location user_location = null;


    // Class Object
    GoogleLocation location;
    Temp_Data_Holder Regi_data_holder;


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
                if (location.getLocation_Co_oridinate() != null) {
                    user_location = location.getLocation_Co_oridinate();
                    Str_Location = location.getLocation_Co_oridinate().toString();
                    Log.i("Location", Str_Location);
                }
            }
        });

        btnRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDataFromView();
                RegisterUser();
            }
        });


    }


    private void GetDataFromView() {


        Str_user_name = v_username.getText().toString();
        Str_password = v_password.getText().toString();
        Str_Email = v_Email.getText().toString();
        Str_phone_number = v_phone_number.getText().toString();


        Str_user_name.trim();
        Str_password.trim();
        Str_Email.trim();
        Str_phone_number.trim();




        Log.i("REGI", "a" + Str_user_name + Str_gender);
        Toast.makeText(Signup_form.this, "Hi" + Str_user_name, Toast.LENGTH_SHORT).show();


    }


    private void RegisterUser() {

        if(Str_user_name.isEmpty() || Str_password.isEmpty()|| Str_Email.isEmpty()||Str_phone_number.isEmpty()||
                Str_gender.equals(ChooseGender) || Str_bloodGroup.equals(ChooseBlood) || Str_Location.isEmpty()) {

            Toast.makeText(this, "Failed ! Fill The Form Properly !!", Toast.LENGTH_SHORT).show();

        }else if(Str_password.length() < 6){
            Toast.makeText(this, "Password Weak ! Minimum Length 6 ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            dialog.show();
            firebaseAuth.createUserWithEmailAndPassword(Str_Email,Str_password).addOnCompleteListener(Signup_form.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    dialog.cancel();
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(Signup_form.this, "Registarion Failed , Try Later !", Toast.LENGTH_SHORT).show();
                    } else if(task.isSuccessful())
                    {
                        Toast.makeText(Signup_form.this,"Account Created !",Toast.LENGTH_SHORT).show();
                        addUserToFirebase();
                        Intent I=new Intent(Signup_form.this,MainActivity.class);

                    }
                }
            });
        }

    }

    private void addUserToFirebase() {
        if (!TextUtils.isEmpty(Str_user_name) && !TextUtils.isEmpty(Str_Email) && !TextUtils.isEmpty(Str_password) && !TextUtils.isEmpty(Str_phone_number) && !TextUtils.isEmpty(Str_gender) && !TextUtils.isEmpty(Str_bloodGroup) && !TextUtils.isEmpty(Str_Location)) {
            FirebaseUser myU = FirebaseAuth.getInstance().getCurrentUser();
            String id = myU.getUid();
            user USER = new user(id,Str_user_name, Str_Email, Str_phone_number, Str_bloodGroup, Str_gender, Str_Location);

            Log.i("MyID", USER.getLocation()+ " "+ USER.getId());

            databaseuser.child(id).setValue(USER);


        } else {
            Toast.makeText(Signup_form.this, "please fillup the full form", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 111) {
            Log.i("SignUp", "Permission Under Constraction");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    location.locationManager.requestSingleUpdate(location.criteria, location.locationListener, location.looper);
                }
            } else {
                //  Toast.makeText(this, "Location Is Required", Toast.LENGTH_SHORT).show();
                Log.i("SignUp", "Permission Not Granted");
            }
        }
    }


    private void init() {

        databaseuser = FirebaseDatabase.getInstance().getReference("USER");
        //----auth-------------------------
        firebaseAuth = FirebaseAuth.getInstance();

        //----dialog------------------------
        dialog = new ProgressDialog(Signup_form.this);
        dialog.setMessage("please wait..");
        dialog.setTitle("Sign Up");
        dialog.setCancelable(false);



        // Button Initialization
        btnLoc = findViewById(R.id.xlocation);
        btnRegi = findViewById(R.id.xButtonRegister);

        // Edit Text Initialization

        v_username = findViewById(R.id.xEditTextRegisterName);
        v_password = findViewById(R.id.xEditTextRegisterPassword);
        v_Email = findViewById(R.id.xEditTextRegisterEmail);
        v_phone_number = findViewById(R.id.xEditTextRegisterMobile);


        BloodGroup = findViewById(R.id.xBloodGroup);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Blood_array, R.layout.custom_spinner);
        BloodGroup.setPrompt("Choose Blood");
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner);
// Apply the adapter to the spinner
        BloodGroup.setAdapter(adapter);


        Gender = findViewById(R.id.xGender);
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
                Str_bloodGroup = BloodGroup.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Signup_form.this, "Required Blood Group", Toast.LENGTH_SHORT).show();
                Log.d("Spinner", Str_bloodGroup);

            }
        });

        Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Str_gender = Gender.getItemAtPosition(i).toString();
                Log.d("Spinner", Str_gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(Signup_form.this, "Required Blood Group", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
