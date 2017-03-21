package com.zoinks.waterreporting.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zoinks.waterreporting.R;

public class HistoricalReportOptionsActivity extends AppCompatActivity {
    private TextView mYearView;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_report_options);

        mYearView = (TextView) findViewById(R.id.year);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio);

        Button mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmission();
            }
        });

        Button mCancelButton
                = (Button) findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * Attempts to create a graph of historical data - validates the data entered
     */
    private void attemptSubmission() {
        String yearString = mYearView.getText().toString();

        // check that users input a year, otherwise tell them it's required
        if (TextUtils.isEmpty(yearString)) {
            mYearView.setError(getString(R.string.error_field_required));
            mYearView.requestFocus();
        } else {
            int year = Integer.parseInt(yearString);
            int selectedId = mRadioGroup.getCheckedRadioButtonId();
            Intent display = new Intent(HistoricalReportOptionsActivity.this,
                    HistoricalGraphActivity.class);
            display.putExtra("Year", year);
            display.putExtra("Virus", selectedId == 0);
            startActivity(display);
        }
    }
}
