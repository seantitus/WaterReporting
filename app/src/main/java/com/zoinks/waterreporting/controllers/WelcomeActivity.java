package com.zoinks.waterreporting.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.Facade;

import java.io.File;

/**
 * Welcome splash screen - first thing the user sees when opening the app
 * Has options for logging in or registering
 *
 * Created by Nancy on 2/15/17.
 */
public class WelcomeActivity extends AppCompatActivity {
    private final Facade facade = Facade.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent launchLogin = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(launchLogin);
            }
        });

        Button register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent launchLogin = new Intent(WelcomeActivity.this, RegistrationActivity.class);
                startActivity(launchLogin);
            }
        });

        // load in data
        facade.loadData(new File(this.getFilesDir(), Facade.USER_JSON),
                new File(this.getFilesDir(), Facade.REPORT_JSON));
    }

    @Override
    protected void onStop() {
        super.onStop();

        /*
         * saves data; this is done in onStop() because onDestroy(), the last method of the activity
         * lifecycle, is not always guaranteed to be called, but onStop() is - kinda
         *
         * might need to be done in onPause() or onSaveInstanceState(Bundle)
         */
        facade.saveData(new File(this.getFilesDir(), Facade.USER_JSON),
                new File(this.getFilesDir(), Facade.REPORT_JSON));
    }
}
