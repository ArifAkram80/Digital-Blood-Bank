package com.example.arif.digitalbloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button ButtSignUp, ButtLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtSignUp = (Button) findViewById(R.id.ButtonSignup);
        ButtLogin = (Button) findViewById(R.id.ButtonLogin);

        ButtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, Signup_form.class);
                startActivity(I);
            }
        });

    }
}
