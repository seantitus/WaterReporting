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
    private static UserSvcProvider usp = UserSvcProvider.getInstance();

    private WaterReportSvcProvider() {
        addReport(8, 31, WaterSourceType.BOTTLED, WaterSourceCondition.POTABLE);
        addReport(2, 3, WaterSourceType.OTHER, WaterSourceCondition.TREATABLECLEAR);
        addReport(77, 21, WaterSourceType.LAKE, WaterSourceCondition.TREATABLEMUDDY);
        addReport(1, 1, WaterSourceType.SPRING, WaterSourceCondition.WASTE);
        addReport(37, 27, WaterSourceType.WELL, WaterSourceCondition.POTABLE);
        addReport(32.2, 332.2, WaterSourceType.STREAM, WaterSourceCondition.POTABLE);
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

    public WaterReport getCurrentWaterReport() {
        return currentWaterReport;
    }

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
