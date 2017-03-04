package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.User;
import com.zoinks.waterreporting.model.UserSvcProvider;

/**
 * Activity to allow users to update their profile
 *
 * Created by stefan on 2/22/17.
 */

public class UpdateProfileActivity extends AppCompatActivity {
    private final UserSvcProvider usp = UserSvcProvider.getInstance();
    private final User currentUser = usp.getCurrentUser();

    private EditText mUsernameView;
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mPasswordView;
    private EditText mEmailView;
    private EditText mAddressView;
    private EditText mPhoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        mUsernameView = (EditText) findViewById(R.id.username);
        mFirstNameView = (EditText) findViewById(R.id.firstname);
        mLastNameView = (EditText) findViewById(R.id.lastname);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailView = (EditText) findViewById(R.id.email);
        mAddressView = (EditText) findViewById(R.id.address);
        mPhoneView = (EditText) findViewById(R.id.phone);

        // set text fields with current info
        mUsernameView.setText(currentUser.getUsername());
        mFirstNameView.setText(currentUser.getFirstName());
        mLastNameView.setText(currentUser.getLastName());
        mEmailView.setText(currentUser.getEmail());
        mAddressView.setText(currentUser.getAddress());
        mPhoneView.setText(currentUser.getPhone());
        mPhoneView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    attemptUpdate();
                    return true;
                }
                return false;
            }
        });

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

        View focusView = null;
        boolean error = false;

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String email = mEmailView.getText().toString();
        String address = mAddressView.getText().toString();
        String phone = mPhoneView.getText().toString();

        // validate the info entered and set an error if necessary
        if (TextUtils.isEmpty(username)) {  // must have username
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            error = true;
        } else if (TextUtils.isEmpty(firstName)) {  // must have first name
            mFirstNameView.setError(getString(R.string.error_field_required));
            focusView = mFirstNameView;
            error = true;
        } else if (TextUtils.isEmpty(lastName)) {  // must have last name
            mLastNameView.setError(getString(R.string.error_field_required));
            focusView = mLastNameView;
            error = true;
        } else if (!TextUtils.isEmpty(email) && !email.matches("(.*)@(.*)\\.(.*)")) {  // invalid email
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            error = true;
        }

        // if an error occurred, display it, otherwise attempt to update the user
        if (error) {
            focusView.requestFocus();
        } else {
            if (usp.update(current.getUsername(), username, firstName, lastName, password, email,
                    address, phone)) {
                finish();
            } else {
                mUsernameView.setError(getString(R.string.error_username_taken));
            }
        }
    }

}
