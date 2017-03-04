package com.zoinks.waterreporting.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        // hardcoded data for testing the recycler view
        addReport(81, 130.1, WaterSourceType.STREAM, WaterSourceCondition.TREATABLE_MUDDY);
        addReport(-37, -27, WaterSourceType.WELL, WaterSourceCondition.POTABLE);
        addReport(8, 31, WaterSourceType.BOTTLED, WaterSourceCondition.POTABLE);
        addReport(-2, 3, WaterSourceType.OTHER, WaterSourceCondition.TREATABLE_CLEAR);
        addReport(77, 21, WaterSourceType.LAKE, WaterSourceCondition.TREATABLE_MUDDY);
        addReport(1, -6.771, WaterSourceType.SPRING, WaterSourceCondition.WASTE);
        addReport(42, 20.1, WaterSourceType.STREAM, WaterSourceCondition.TREATABLE_MUDDY);
        addReport(37, 27, WaterSourceType.WELL, WaterSourceCondition.POTABLE);
        addReport(32.2, -111.2, WaterSourceType.STREAM, WaterSourceCondition.POTABLE);
        addReport(-23.1, 31.6, WaterSourceType.OTHER, WaterSourceCondition.TREATABLE_CLEAR);
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
     * Adds a water report where author is the current user and time is current time
     */
    public void addReport(double latitude, double longitude, WaterSourceType type,
                          WaterSourceCondition condition) {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        list.add(new WaterSourceReport(time, usp.getCurrentUser(), latitude, longitude, type,
                condition));
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
}
