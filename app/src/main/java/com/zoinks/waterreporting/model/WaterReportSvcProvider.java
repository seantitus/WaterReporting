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
    private static final List<WaterReport> list = new ArrayList<>();
    private static UserSvcProvider usp = UserSvcProvider.getInstance();

    private WaterReportSvcProvider() {
        // TODO: pre-fill testing info
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
     * Adds a water report, author as the current user and time as current time
     */
    public void addWaterSourceReport(double latitude, double longitude,
                          WaterSourceType waterSourceType) {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        User u = usp.getCurrentUser();
        list.add(new WaterSourceReport(time, u, latitude, longitude, waterSourceType));
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
