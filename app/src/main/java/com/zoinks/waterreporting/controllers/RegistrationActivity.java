package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.UserType;

import static com.zoinks.waterreporting.R.id.username;

public class RegistrationActivity extends AppCompatActivity {
    private final Facade facade = Facade.getInstance();

    // UI references.
    private EditText mFirstName;
    private EditText mLastName;
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private Spinner mPrivilegeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mUsernameView = (AutoCompleteTextView) findViewById(username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPrivilegeSpinner = (Spinner) findViewById(R.id.spinner);

        // populate spinner with valid user privilege levels
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                UserType.getValues());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrivilegeSpinner.setAdapter(adapter);

        Button mRegisterButton = (Button) findViewById(R.id.username_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });

        Button mCancelButton = (Button) findViewById(R.id.cancel_register_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Registration canceled.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void attemptRegistration() {
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        UserType privilege = UserType.get((mPrivilegeSpinner.getSelectedItemPosition() + 1) * 10);

        // Reset errors.
        mFirstName.setError(null);
        mLastName.setError(null);
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check if user actually input stuff
        if (TextUtils.isEmpty(firstName)) {
            mFirstName.setError(getString(R.string.error_field_required));
            focusView = mFirstName;
            cancel = true;
        } else if (TextUtils.isEmpty(lastName)) {
            mLastName.setError(getString(R.string.error_field_required));
            focusView = mLastName;
            cancel = true;
        } else if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt registration and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // no errors, register!
            if (facade.registerUser(firstName, lastName, username, password, privilege)) {
                Toast.makeText(getApplicationContext(), "Registration successful! Please login now.",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                mUsernameView.setError(getString(R.string.error_username_taken));
            }
        }
    }
}
