package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.User;
import com.zoinks.waterreporting.model.UserSvcProvider;

/**
 * Created by stefan on 2/22/17.
 */

public class UpdateProfileActivity extends AppCompatActivity {
    private UserSvcProvider usp = UserSvcProvider.getInstance();
    private User currentUser = usp.getCurrentUser();

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

        // set text fields with current info
        mUsernameView.setText(currentUser.getUsername());
        mFirstNameView.setText(currentUser.getFirstName());
        mLastNameView.setText(currentUser.getLastName());

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
                finish();
            }
        });
    }

    private void attemptUpdate() {
        User current = usp.getCurrentUser();
        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        Log.d("Vals", firstName + " " + lastName + " " + username + " " + password);

        if (usp.update(firstName, lastName, username, current.getUsername(), password)) {
            finish();
        } else {
            mUsernameView.setError(getString(R.string.error_invalid_profile_update));
        }
    }

}
