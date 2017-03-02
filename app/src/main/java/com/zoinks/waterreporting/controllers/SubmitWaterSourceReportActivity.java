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
import com.zoinks.waterreporting.model.UserType;
import com.zoinks.waterreporting.model.WaterReportSvcProvider;
import com.zoinks.waterreporting.model.WaterSourceCondition;
import com.zoinks.waterreporting.model.WaterSourceType;

/**
 * Created by stefan on 3/1/17.
 */

public class SubmitWaterSourceReportActivity extends AppCompatActivity {
    private WaterReportSvcProvider wsp;

    private TextView mLongitudeView;
    private TextView mLatitudeView;
    private Spinner mWaterSourceTypeSpinner;
    private Spinner mWaterConditionTypeSpinner;

    private View mProgressView;
    private View mSubmissionFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_submit_water_source_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wsp = WaterReportSvcProvider.getInstance();

        mLongitudeView = (EditText) findViewById(R.id.longitude);
        mLatitudeView = (EditText) findViewById(R.id.latitude);
        mWaterSourceTypeSpinner = (Spinner) findViewById(R.id.type_spinner);
        mWaterConditionTypeSpinner = (Spinner) findViewById(R.id.condition_spinner);

        mSubmissionFormView = findViewById(R.id.submit_water_source_report_form);
        mProgressView = findViewById(R.id.submit_water_source_report_progress);

        ArrayAdapter<String> adapter
                = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, WaterSourceType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWaterSourceTypeSpinner.setAdapter(adapter);

        ArrayAdapter<String> conditionAdapter
                = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, WaterSourceCondition.values());
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWaterConditionTypeSpinner.setAdapter(conditionAdapter);

        Button mRegisterButton = (Button) findViewById(R.id.submit_water_source_report_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmission();
            }
        });

        Button mCancelButton
                = (Button) findViewById(R.id.cancel_submit_water_source_report_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void attemptSubmission() {
        String latString = mLatitudeView.getText().toString();
        String longString = mLongitudeView.getText().toString();

        View focusView = null;
        boolean cancel = false;

        if (TextUtils.isEmpty(latString)) {
            focusView = mLatitudeView;
            cancel = true;
        } else if (TextUtils.isEmpty(longString)) {
            focusView = mLongitudeView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            return;
        }


        double latitude = Double.parseDouble(latString);
        double longitude = Double.parseDouble(longString);
        WaterSourceType waterSourceType
                = (WaterSourceType) mWaterSourceTypeSpinner.getSelectedItem();
        WaterSourceCondition waterSourceCondition
                = (WaterSourceCondition) mWaterConditionTypeSpinner.getSelectedItem();
        wsp.addReport(latitude, longitude, waterSourceType, waterSourceCondition);

        Intent toMain = new Intent(SubmitWaterSourceReportActivity.this, MainActivity.class);
        startActivity(toMain);
        finish();
    }

}
