package com.zoinks.waterreporting;

import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.UserType;
import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.WaterReport;
import com.zoinks.waterreporting.model.WaterSourceCondition;
import com.zoinks.waterreporting.model.WaterSourceReport;
import com.zoinks.waterreporting.model.WaterSourceType;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
/**
 * Created by Pragya on 4/5/2017.
 */

public class addSourceReportTest {
    private Facade facade;
    private Calendar calendar;
    private Date time;

    @Test
    public void userAdd() {
        facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser", "testPassword", UserType.USER);
        facade.login("testUser", "testPassword");
        calendar = Calendar.getInstance();
        time = calendar.getTime();
        facade.addSourceReport(0,0, WaterSourceType.BOTTLED, WaterSourceCondition.POTABLE);
        WaterSourceReport report = new WaterSourceReport(time,"testUser",0,0, WaterSourceType.BOTTLED, WaterSourceCondition.POTABLE);
        List<WaterReport> reports = facade.getSourceReports();
        assertEquals(reports.contains(report), true);
    }

    @Test
    public void workerAdd() {
        facade = Facade.getInstance();
        facade.registerUser("Test", "Worker", "testWorker", "testPassword", UserType.WORKER);
        facade.login("testWorker", "testPassword");
        calendar = Calendar.getInstance();
        time = calendar.getTime();
        facade.addSourceReport(1,0, WaterSourceType.BOTTLED, WaterSourceCondition.POTABLE);
        WaterSourceReport report = new WaterSourceReport(time,"testWorker",1,0, WaterSourceType.BOTTLED, WaterSourceCondition.POTABLE);
        List<WaterReport> reports = facade.getSourceReports();
        assertEquals(reports.contains(report), true);
    }

    @Test
    public void managerAdd() {
        facade = Facade.getInstance();
        facade.registerUser("Test", "Man", "testMan", "testPassword", UserType.MANAGER);
        facade.login("testMan", "testPassword");
        calendar = Calendar.getInstance();
        time = calendar.getTime();
        facade.addSourceReport(2,0, WaterSourceType.BOTTLED, WaterSourceCondition.POTABLE);
        WaterSourceReport report = new WaterSourceReport(time,"testMan",2,0, WaterSourceType.BOTTLED, WaterSourceCondition.POTABLE);
        List<WaterReport> reports = facade.getSourceReports();
        assertEquals(reports.contains(report), true);
    }
}
