package com.zoinks.waterreporting.model;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class to manage information and services related to water reports
 * Has package visibility because it should only be accessed from the facade, not directly
 *
 * Created by Nancy on 03/01/2017.
 */

class WaterReportSvcProvider {
    private final List<WaterSourceReport> sourceList = new ArrayList<>();
    private final List<WaterQualityReport> qualityList = new ArrayList<>();

    /**
     * Get the list of water reports
     *
     * @return the list of water reports
     */
    List<WaterReport> getReports() {
        List<WaterReport> list = new ArrayList<>();
        list.addAll(sourceList);
        list.addAll(qualityList);
        return list;
    }

    /**
     * Get the list of water source reports
     *
     * @return the list of water source reports
     */
    List<WaterReport> getSourceReports() {
        return new ArrayList<WaterReport>(sourceList);
    }

    /**
     * Get the list of water quality reports for managers to view
     *
     * @return the list of water quality reports
     */
    List<WaterReport> getQualityReports() {
        return new ArrayList<WaterReport>(qualityList);
    }

    /**
     * Adds a water source report where time is current time
     *
     * @param latitude latitude of the water report
     * @param longitude longitude of the water report
     * @param type type of water at the water source
     * @param condition condition of water at the water source
     * @param user author of the water report
     */
    void addSourceReport(double latitude, double longitude, WaterSourceType type,
                                WaterSourceCondition condition, User user) {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        sourceList.add(new WaterSourceReport(time, user.getUsername(), latitude, longitude, type,
                condition));
    }

    /**
     * Adds a water quality report where time is current time
     *
     * @param latitude latitude of the water report
     * @param longitude longitude of the water report
     * @param condition condition of water at the water source
     * @param virusPPM virus concentration measured in PPM at the water source
     * @param contaminantPPM contaminant concentration measured in PPM at the water source
     * @param user author of the water report
     */
    void addQualityReport(double latitude, double longitude, WaterQualityCondition condition,
                                 double virusPPM, double contaminantPPM, User user) {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        qualityList.add(new WaterQualityReport(time, user.getUsername(), latitude, longitude,
                condition, virusPPM, contaminantPPM));
    }

    /**
     * Returns the data for the year requested, 12 points, one for each month for the location requested
     *
     * @param year the year from which data is requested
     * @param latitude the latitude of the reports to get
     * @param longitude the longitude of the reports to get
     * @param virus true if the manager wishes to graph virus PPM, false to graph contaminant PPM
     */
    List<Entry> getYearsData(int year, double latitude, double longitude, boolean virus) {
        // set up array of linked lists, where each linked list is one month's worth of reports
        List<List<WaterQualityReport>> monthArray = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            monthArray.add(new ArrayList<WaterQualityReport>());
        }

        // add water reports to the appropriate bucket
        for (WaterQualityReport wr: qualityList) {
            if (wr.getYear() == year && wr.getLatitude() == latitude && wr.getLongitude() == longitude) {
                List<WaterQualityReport> month = monthArray.get(wr.getMonth());
                month.add(wr);
                monthArray.remove(wr.getMonth());
                monthArray.add(wr.getMonth(), month);
            }
        }

        // in each bucket, average the ppm for the reports for that month
        List<Entry> dataList = new ArrayList<>();
        for (int month = 0; month < 12; month++) {
            List<WaterQualityReport> yearList = monthArray.get(month);
            if (yearList.size() != 0) {  // a month with no data will just not have a plot point
                double sum = 0;
                for (WaterQualityReport wr : yearList) {
                    if (virus) {
                        sum += wr.getVirusPPM();
                    } else {
                        sum += wr.getContaminantPPM();
                    }
                }
                double average = sum / yearList.size();
                dataList.add(new Entry(month, (float) average));
            }
        }

        return dataList;
    }
}
