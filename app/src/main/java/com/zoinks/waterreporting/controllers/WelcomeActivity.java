package com.zoinks.waterreporting.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.zoinks.waterreporting.R;

public class WelcomeActivity extends AppCompatActivity {

    /* FIREBASE */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent launchLogin = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(launchLogin);
            }
        });

        Button register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener((arg0) -> {
                Intent launchRegister = new Intent(WelcomeActivity.this, RegistrationActivity.class);
                startActivity(launchRegister);
            });
    }

}
