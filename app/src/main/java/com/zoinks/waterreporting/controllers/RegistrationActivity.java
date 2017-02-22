package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.UserSvcProvider;
import com.zoinks.waterreporting.model.UserType;

import static com.zoinks.waterreporting.R.id.username;

public class RegistrationActivity extends AppCompatActivity {
    private UserSvcProvider usp = UserSvcProvider.getInstance();

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

        mFirstName = (EditText) findViewById(R.id.firstname);
        mLastName = (EditText) findViewById(R.id.lastname);
        mUsernameView = (AutoCompleteTextView) findViewById(username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPrivilegeSpinner = (Spinner) findViewById(R.id.spinner);

        // populate spinner with valid user privilege levels
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UserType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrivilegeSpinner.setAdapter(adapter);

        Button mRegisterButton = (Button) findViewById(R.id.username_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });
    }

    private void attemptRegistration() {
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        UserType privilege = (UserType) mPrivilegeSpinner.getSelectedItem();

        if (usp.register(firstName, lastName, username, password, privilege)) {
            finish();
        } else {
            mUsernameView.setError(getString(R.string.error_username_taken));
        }
    }
}
