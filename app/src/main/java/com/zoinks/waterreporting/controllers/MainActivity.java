package com.zoinks.waterreporting.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.User;
import com.zoinks.waterreporting.model.UserSvcProvider;
import com.zoinks.waterreporting.model.UserType;

public class MainActivity extends AppCompatActivity {
    private UserSvcProvider usp = UserSvcProvider.getInstance();

    private TextView mUsernameView;
    private TextView mFirstnameView;
    private TextView mLastnameView;
    private TextView mPrivilegeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUsernameView = (TextView) findViewById(R.id.username_label);
        mFirstnameView = (TextView) findViewById(R.id.firstname_label);
        mLastnameView = (TextView) findViewById(R.id.lastname_label);
        mPrivilegeView = (TextView) findViewById(R.id.privilege_label);

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

        Button updateProfile = (Button) findViewById(R.id.update_profile);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchUpdate = new Intent(MainActivity.this, UpdateProfileActivity.class);
                startActivity(launchUpdate);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLabels();
    }

    private void updateLabels() {
        User current = usp.getCurrentUser();
        mUsernameView.setText(String.format("Username: %s", current.getUsername()));
        mFirstnameView.setText(String.format("First Name: %s", current.getFirstName()));
        mLastnameView.setText(String.format("Last Name: %s", current.getLastName()));
        UserType privilege = UserType.values()[current.checkPrivilege() / 10 - 1];
        mPrivilegeView.setText(String.format("Privilege: %s", privilege.name()));
    }

}
