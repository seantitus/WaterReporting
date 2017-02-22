package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.UserSvcProvider;

public class MainActivity extends AppCompatActivity {
    private UserSvcProvider usp = UserSvcProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, usp.getCurrentUser().getUsername(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                usp.logout();
                finish();
            }
        });
    }

}
