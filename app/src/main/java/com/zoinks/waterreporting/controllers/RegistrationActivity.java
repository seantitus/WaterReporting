package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.UserSvcProvider;

public class RegistrationActivity extends AppCompatActivity {
    private UserSvcProvider usp = UserSvcProvider.getInstance();

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mRegisterButton = (Button) findViewById(R.id.username_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptRegistration() {
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (usp.register(username, password)) {
            finish();
        } else {
            mUsernameView.setError(getString(R.string.error_username_taken));
        }
    }
}
