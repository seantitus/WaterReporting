package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.TextView;

import com.zoinks.waterreporting.R;

/**
 * Created by stefan on 3/1/17.
 */

public class SubmitWaterSourceReportActivity extends AppCompatActivity {

    private TextView mLongitudeView;
    private TextView mLatitudeView;
    private Spinner mWaterSourceTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_submit_water_source_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

}
