package com.zoinks.waterreporting.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.User;
import com.zoinks.waterreporting.model.UserSvcProvider;
import com.zoinks.waterreporting.model.UserType;

import static com.zoinks.waterreporting.R.id.username;

/**
 * Created by stefan on 2/22/17.
 */

public class UpdateProfileActivity extends AppCompatActivity {
    private UserSvcProvider usp = UserSvcProvider.getInstance();


    private EditText mUsernameView;
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_attributes);

        mUsernameView = (EditText) findViewById(R.id.username);
        mFirstNameView = (EditText) findViewById(R.id.firstname);
        mLastNameView = (EditText) findViewById(R.id.lastname);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mRegisterButton = (Button) findViewById(R.id.submit_profile_update);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdate();
            }
        });

        Button mGoHomeButton = (Button) findViewById(R.id.go_home);
        mGoHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchHome = new Intent(UpdateProfileActivity.this, MainActivity.class);
                startActivity(launchHome);
            }
        });
    }

    private void attemptUpdate() {
        User current = usp.getCurrentUser();
        String firstName = mFirstNameView.getText().toString().length() > 0
                ? mFirstNameView.getText().toString()
                : current.getFirstName();
        String lastName = mFirstNameView.getText().toString().length() > 0
                ? mLastNameView.getText().toString()
                : current.getLastName();
        String username = mUsernameView.getText().toString().length() > 0
                ? mUsernameView.getText().toString()
                : current.getUsername();
        String password = mPasswordView.getText().toString().length() > 0
                ? mPasswordView.getText().toString()
                : current.getPassword();
        Log.d("Vals", firstName + " " + lastName + " " + username + " " + password);

        if (usp.update(firstName, lastName, username, current.getUsername(), password)) {
            finish();
            Intent launchReturn = new Intent(UpdateProfileActivity.this, MainActivity.class);
            startActivity(launchReturn);
        } else {
            mUsernameView.setError(getString(R.string.error_invalid_profile_update));
        }
    }

}
