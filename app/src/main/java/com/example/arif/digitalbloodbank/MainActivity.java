package com.example.arif.digitalbloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arif.digitalbloodbank.home.Home_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button vButtSignUp, vButtLogin, vRecovery;
    private EditText vEmail,vPassword;
    private ProgressDialog dialog;


    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("please wait..");
        dialog.setTitle("Login");
        dialog.setCancelable(false);



        vButtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             login();
             //   Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        vButtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, Signup_form.class);
                startActivity(I);
            }
        });
        vRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : Start the intend of Recovery Activity
            }
        });

    }

    private void login() {

        if (vEmail.getText().toString().isEmpty() || vPassword.getText().toString().isEmpty()) {

            Toast.makeText(MainActivity.this, "Please fill up your email / password", Toast.LENGTH_LONG).show();

        } else {
            dialog.show();

            firebaseAuth.signInWithEmailAndPassword(vEmail.getText().toString(), vPassword.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {

                    dialog.cancel();
                    if (task.isSuccessful()) {

                        Toast.makeText(MainActivity.this, "Successfully Login !!  ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, Home_Activity.class);
                        startActivity(intent);
                    } else if (!task.isSuccessful()) {

                        Toast.makeText(MainActivity.this, "Wrong email or password  ", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }



    }

    private void init() {
        vButtSignUp = (Button) findViewById(R.id.ButtonSignup);
        vButtLogin = (Button) findViewById(R.id.ButtonLogin);
        vRecovery = (Button) findViewById(R.id.ButtonRecovery);
        vEmail = (EditText) findViewById(R.id.EditTextLoginEmail);
        vPassword = (EditText)findViewById(R.id.EditTextLoginPassword);

        firebaseAuth = FirebaseAuth.getInstance();

    }
}
