package com.zoinks.waterreporting.model;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Class to manage information and services related to water reports
 * Implements Singleton design pattern
 *
 * Created by Nancy on 03/01/2017.
 */

public class WaterReportSvcProvider {
    private static WaterReportSvcProvider wrsp;
    private WaterReport currentWaterReport;
    private static final List<WaterReport> list = new ArrayList<>();
    private static final UserSvcProvider usp = UserSvcProvider.getInstance();

    private WaterReportSvcProvider() {
        // hardcoded data
        addSourceReport(81, 130.1, WaterSourceType.STREAM, WaterSourceCondition.TREATABLE_MUDDY);
        addSourceReport(-37, -27, WaterSourceType.WELL, WaterSourceCondition.POTABLE);
        addSourceReport(-2, 3, WaterSourceType.OTHER, WaterSourceCondition.TREATABLE_CLEAR);
        addSourceReport(77, 21, WaterSourceType.LAKE, WaterSourceCondition.TREATABLE_MUDDY);
        addSourceReport(-23.1, 31.6, WaterSourceType.OTHER, WaterSourceCondition.TREATABLE_CLEAR);
        // dummy reports for testing historical reports
        for (int month = 0; month < 12; month++) {
            Date time = new GregorianCalendar(2016, month, 2).getTime();
            list.add(new WaterQualityReport(time, usp.getCurrentUser(), 0, 0,
                    WaterQualityCondition.TREATABLE, (month % 4), (month % 4 + 10)));
        }
        Date time = new GregorianCalendar(2016, 2, 3).getTime();
        list.add(new WaterQualityReport(time,  usp.getCurrentUser(), 0, 0,
                WaterQualityCondition.UNSAFE, 10, 1));
    }

    /**
     * Gets the instance of WaterReportSvcProvider (should only be one - singleton design pattern)
     *
     * @return the instance of WaterReportSvcProvider
     */
    public static WaterReportSvcProvider getInstance() {
        if (wrsp == null) {
            wrsp = new WaterReportSvcProvider();
        }
        return wrsp;
    }

    /**
     * Get the list of water reports
     *
     * @return the list of water reports
     */
    public List<WaterReport> getReports() {
        return list;
    }

    /**
     * Get the list of water source reports
     *
     * @return the list of water source reports
     */
    public List<WaterReport> getSourceReports() {
        List<WaterReport> sourceList = new ArrayList<>();
        for (WaterReport wr : list) {
            if (wr instanceof WaterSourceReport) {
                sourceList.add(wr);
            }
        }
        return sourceList;
    }

    /**
     * Get the list of water quality reports for managers to view
     *
     * @return the list of water quality reports
     */
    public List<WaterReport> getQualityReports() {
        List<WaterReport> qualityList = new ArrayList<>();
        for (WaterReport wr : list) {
            if (wr instanceof WaterQualityReport) {
                qualityList.add(wr);
            }
        }
        return qualityList;
    }

    /**
     * Gets the water report that was just clicked to be displayed
     *
     * @return the water report that was just clicked to be displayed
     */
    public WaterReport getCurrentWaterReport() {
        return currentWaterReport;
    }

    /**
     * Sets the water report that the user just clicked on
     *
     * @param currentWaterReport the report to be displayed
     */
    public void setCurrentWaterReport(WaterReport currentWaterReport) {
        this.currentWaterReport = currentWaterReport;
    }

    /**
     * Adds a water source report where author is the current user and time is current time
     */
    public void addSourceReport(double latitude, double longitude, WaterSourceType type,
                                WaterSourceCondition condition) {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        list.add(new WaterSourceReport(time, usp.getCurrentUser(), latitude, longitude, type,
                condition));
    }

    /**
     * Adds a water quality report where author is the current user and time is current time
     */
    public void addQualityReport(double latitude, double longitude, WaterQualityCondition condition,
                                 double virusPPM, double contaminantPPM) {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        list.add(new WaterQualityReport(time, usp.getCurrentUser(), latitude, longitude, condition,
                virusPPM, contaminantPPM));
    }

    /**
     * Removes the given report - only managers can do this
     * TODO: write to security log with current timestamp and current user
     *
     * @param report to be removed
     */
    public void deleteReport(WaterReport report) {
        list.remove(report);
    }

    /**
     * Returns the data for the year requested, 12 data points, one for each month
     *
     * @param year the year from which data is requested
     * @param virus true if the manager wishes to graph virus PPM, false to graph contaminant PPM
     */
    public List<Entry> getYearsData(int year, boolean virus) {
        List<List<WaterQualityReport>> monthArray = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            monthArray.add(new ArrayList<WaterQualityReport>());
        }
        for (WaterReport wr: list) {
            if (wr instanceof WaterQualityReport && wr.getYear() == year) {
                List<WaterQualityReport> month = monthArray.get(wr.getMonth());
                month.add((WaterQualityReport) wr);
                monthArray.remove(wr.getMonth());
                monthArray.add(wr.getMonth(), month);
            }
        }

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
