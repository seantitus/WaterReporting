package com.zoinks.waterreporting.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zoinks.waterreporting.R;

/**
 * Welcome splash screen - first thing the user sees when opening the app
 * Has options for logging in or registering
 *
 * Created by Nancy on 2/15/17.
 */
public class WelcomeActivity extends AppCompatActivity {

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
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent launchLogin = new Intent(WelcomeActivity.this, RegistrationActivity.class);
                startActivity(launchLogin);
            }
        });
    }

}
