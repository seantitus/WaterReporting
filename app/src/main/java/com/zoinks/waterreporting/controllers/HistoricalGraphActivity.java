package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.WaterReportSvcProvider;

import java.util.List;

public class HistoricalGraphActivity extends AppCompatActivity {
    private final WaterReportSvcProvider wrsp = WaterReportSvcProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_graph);

        LineChart lineChart = (LineChart) findViewById(R.id.graph);

        int year = getIntent().getIntExtra("Year", 0);
        boolean virus = getIntent().getBooleanExtra("Virus", true);
        List<Entry> entries = wrsp.getYearsData(year, virus);
        LineDataSet dataSet = new LineDataSet(entries, year + "");

        LineData data = new LineData(dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // configure chart
        if (virus) {
            lineChart.getDescription().setText("Average Virus PPM per Month in " + year);
        } else {
            lineChart.getDescription().setText("Average Contaminant PPM per Month in " + year);
        }
        lineChart.setDrawGridBackground(false);
        lineChart.setNoDataText("No water quality data available for " + year);

        // set data on chart
        if (entries.isEmpty()) {
            // cannot pass an empty list because package will throw an IndexOutOfBoundsException
            lineChart.setData(null);
        } else {
            lineChart.setData(data);
        }
    }
}
