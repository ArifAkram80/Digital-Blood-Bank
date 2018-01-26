package com.example.arif.digitalbloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RecoverAccount extends AppCompatActivity {
    private EditText RecoveryEmail;
    private Button EmailSubmit;
    private ProgressDialog dialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_account);
        RecoveryEmail = (EditText) findViewById(R.id.RecoveryEmail);
        EmailSubmit = (Button) findViewById(R.id.EmailSubmit);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(RecoverAccount.this);
        dialog.setMessage("please wait..");
        dialog.setTitle("Submitting your Email");
        dialog.setCancelable(false);

        EmailSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RecoveryEmail.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please fill up your email", Toast.LENGTH_LONG).show();

                } else {
                    dialog.show();

                    auth.sendPasswordResetEmail(RecoveryEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.cancel();
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "We have send you a email", Toast.LENGTH_SHORT).show();
                                Intent I = new Intent(RecoverAccount.this, Signup_form.class);
                                startActivity(I);
                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong! Try later...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });


    }
}
