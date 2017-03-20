package com.zoinks.waterreporting.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.WaterQualityCondition;
import com.zoinks.waterreporting.model.WaterReportSvcProvider;

public class SubmitWaterQualityReportActivity extends AppCompatActivity {
    private final WaterReportSvcProvider wrsp = WaterReportSvcProvider.getInstance();

    private TextView mLongitudeView;
    private TextView mLatitudeView;
    private Spinner mWaterConditionSpinner;
    private TextView mVirusPPMView;
    private TextView mContaminantPPMView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_water_quality_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLongitudeView = (EditText) findViewById(R.id.longitude);
        mLatitudeView = (EditText) findViewById(R.id.latitude);
        mWaterConditionSpinner = (Spinner) findViewById(R.id.condition_spinner);
        mVirusPPMView = (EditText) findViewById(R.id.virusPPM);
        mContaminantPPMView = (EditText) findViewById(R.id.contaminantPPM);

        ArrayAdapter<String> conditionAdapter
                = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, WaterQualityCondition.values());
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWaterConditionSpinner.setAdapter(conditionAdapter);

        Button mSubmitButton = (Button) findViewById(R.id.submit_report_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmission();
            }
        });

        Button mCancelButton
                = (Button) findViewById(R.id.cancel_submit_report_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Attempts to create a new water report - validates the data entered
     */
    private void attemptSubmission() {
        String latString = mLatitudeView.getText().toString();
        String longString = mLongitudeView.getText().toString();
        String virusString = mVirusPPMView.getText().toString();
        String contaminantString = mContaminantPPMView.getText().toString();

        View focusView = null;
        boolean cancel = false;

        // check that users input a number, otherwise tell them it's required
        if (TextUtils.isEmpty(latString)) {
            mLatitudeView.setError(getString(R.string.error_field_required));
            focusView = mLatitudeView;
            cancel = true;
        } else if (TextUtils.isEmpty(longString)) {
            mLongitudeView.setError(getString(R.string.error_field_required));
            focusView = mLongitudeView;
            cancel = true;
        }

        // if there was an error, set the error on the view and return
        if (cancel) {
            focusView.requestFocus();
            return;
        }

        // check that the inputted values are a valid range for latitude/longitude
        double latitude = Double.parseDouble(latString);
        double longitude = Double.parseDouble(longString);
        if (latitude < -90.0 || latitude > 90) {
            mLatitudeView.setError(getString(R.string.invalid_latitude));
            focusView = mLatitudeView;
            cancel = true;
        } else if (longitude < -180.0 || longitude > 180.0) {
            mLongitudeView.setError(getString(R.string.invalid_longitude));
            focusView = mLongitudeView;
            cancel = true;
        }

        // if there was an error, set the error on the view and return
        if (cancel) {
            focusView.requestFocus();
            return;
        }

        // check that the inputted PPM values are valid
        double virusPPM = Double.parseDouble(virusString);
        double contaminantPPM = Double.parseDouble(contaminantString);
        if (virusPPM < 0 || virusPPM > 1000000) {
            mVirusPPMView.setError(getString(R.string.invalid_PPM));
            focusView = mVirusPPMView;
            cancel = true;
        } else if (contaminantPPM < 0 || contaminantPPM > 1000000) {
            mContaminantPPMView.setError(getString(R.string.invalid_PPM));
            focusView = mContaminantPPMView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            WaterQualityCondition condition
                    = (WaterQualityCondition) mWaterConditionSpinner.getSelectedItem();
            wrsp.addQualityReport(latitude, longitude, condition, virusPPM, contaminantPPM);

            Intent toMain = new Intent(SubmitWaterQualityReportActivity.this, MainActivity.class);
            startActivity(toMain);
            finish();
        }
    }
}
