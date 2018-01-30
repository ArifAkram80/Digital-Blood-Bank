package com.example.arif.digitalbloodbank;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arif.digitalbloodbank.home.Home_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Login extends AppCompatActivity {
    private EditText email, pass;
    private Button ButtonLogin, ButtonSignup;
    private ProgressDialog dialog;
    private TextView ButtonRecovery;
    private FirebaseAuth mAuth;
    private DatabaseReference rUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        rUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButtonRecovery = findViewById(R.id.ButtonRecovery);
        ButtonSignup = findViewById(R.id.ButtonSignup);
        ButtonLogin = findViewById(R.id.ButtonLogin);
        mAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(Login.this);
        dialog.setMessage("please wait..");
        dialog.setTitle("Login");
        dialog.setCancelable(false);


        ButtonRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(getApplicationContext(), RecoverAccount.class);
                startActivity(I);
            }
        });


        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                startLogin();
            }

        });


        ButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(getApplicationContext(), Signup_form.class);
                startActivity(I);
            }
        });

    }

    private void startLogin() {
        email = (EditText) findViewById(R.id.UserEmail);
        pass = (EditText) findViewById(R.id.UserPassword);
        String tEmail = email.getText().toString().trim();
        String tPass = pass.getText().toString().trim();
        if (!TextUtils.isEmpty(tEmail) && !TextUtils.isEmpty(tPass)) {
            mAuth.signInWithEmailAndPassword(tEmail, tPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                dialog.cancel();

                                String current_user_id = mAuth.getCurrentUser().getUid();

                                String DeviceToken = FirebaseInstanceId.getInstance().getToken();

                                rUserDatabase.child(current_user_id).child("device_token").setValue(DeviceToken).addOnSuccessListener(
                                        new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                        }
                                );
                                // Sign in success, update UI with the signed-in user's information

                            } else {
                                dialog.cancel();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Wrong Email or Password!",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {
            dialog.cancel();
            Toast.makeText(getApplicationContext(), "Enter Email and Password.",
                    Toast.LENGTH_SHORT).show();


        }


    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

            Intent I = new Intent(getApplicationContext(), Home_Activity.class);
            startActivity(I);
            finish();

        }
    }


    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


}