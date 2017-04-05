package com.zoinks.waterreporting;

import com.github.mikephil.charting.data.Entry;
import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.UserType;
import com.zoinks.waterreporting.model.WaterQualityCondition;
import com.zoinks.waterreporting.model.WaterSourceCondition;
import com.zoinks.waterreporting.model.WaterSourceType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * Created by Stefan on 4/5/17.
 */
public class GetYearsDataTest {
    private Facade facade;
    private Calendar calendar;
    private Date time;

    @Before
    public void setUp() {
        facade = Facade.getInstance();
        facade.registerUser("Test", "User", "testUser", "testPassword", UserType.USER);
        facade.login("testUser", "testPassword");
        calendar = Calendar.getInstance();
        time = calendar.getTime();
    }

    @After
    public void tearDown() {
        facade.logout();
        facade = null;
        calendar = null;
        time = null;
    }

    @Test
    public void testNegativeYear() {
        try {
            facade.getYearsData(-1000, 0.0, 0.0, true);
        } catch (Exception e) {
            fail("WaterReportSvcProvider.getYearsData cannot accommodate"
                + " negative year values");
        }
    }

    @Test
    public void testUnexpectedLongitude() {
        try {
            facade.getYearsData(2000, 180.1, 0.0, true);
            facade.getYearsData(2000, -180.1, 0.0, true);
        } catch (Exception e) {
            fail("WaterReportSvcProvider.getYearsData() cannot accommodate"
                    + " longitudes not in [-180.0, 180.0]");
        }
    }

    @Test
    public void testUnexpectedLatitude() {
        try {
            facade.getYearsData(2000, 0.0, -100.0, true);
            facade.getYearsData(2000, 0.0, 100.0, true);
        } catch (Exception e) {
            fail("WaterReportSvcProvider.getYearsData() cannot accommodate"
                    + " latitudes not in [-90.0, 90.0]");
        }
    }

    @Test
    public void testInitiallyEmptyList() {
        List<Entry> emptyList = facade.getYearsData(2015,
                120.0, 120.0, true);
        assertTrue("Non empty list returned from facade"
                + " with no water reports", 0 == emptyList.size());
    }

    @Test
    public void testNonYearReportPresence() {
        facade.addQualityReport(100.0 , 125.0, WaterQualityCondition.SAFE,
            50.0, 30.0);
        facade.addSourceReport(100.0 , 125.0, WaterSourceType.BOTTLED,
                WaterSourceCondition.POTABLE);
        List<Entry> list = facade.getYearsData(2015, 100.0, 125.0, true);
        assertTrue("WaterReportsSvcProvider.getYearsData() returns"
                + " non empty list when empty year is requested",
                list.size() == 0);
    }

    @Test
    public void testYearWaterQualityReportsVirusPpm() {
        facade.addQualityReport(100.0 , 125.0, WaterQualityCondition.SAFE,
                100.0, 30.0);
        facade.addQualityReport(100.0 , 125.0, WaterQualityCondition.SAFE,
                50.0, 30.0);
        facade.addQualityReport(100.0 , 125.0, WaterQualityCondition.SAFE,
                0.0, 30.0);
        List<Entry> list = facade.getYearsData(2017, 100.0, 125.0, true);
        assertTrue("Reports generated at the same time are partitioned"
            + " into separate Entry objects", list.size() == 1);
        assertTrue("getYearsData() does not average correctly",
                list.get(0).getY() == 50.0);
        calendar.setTime(time);
        int oMonth = calendar.get(Calendar.MONTH);
        assertTrue("getYearsData() does not assign correct months",
                ((int) list.get(0).getX()) == oMonth);
    }

    @Test
    public void testYearWaterQualityReportsContaminantPpm() {
        facade.addQualityReport(100.0 , 125.0, WaterQualityCondition.SAFE,
                100.0, 120.0);
        facade.addQualityReport(100.0 , 125.0, WaterQualityCondition.SAFE,
                50.0, 60.0);
        facade.addQualityReport(100.0 , 125.0, WaterQualityCondition.SAFE,
                0.0, 90.0);
        List<Entry> list = facade.getYearsData(2017, 100.0, 125.0, false);
        assertTrue("Reports generated at the same time are partitioned"
                + " into separate Entry objects", list.size() == 1);
        assertTrue("getYearsData() does not average correctly",
                list.get(0).getY() == 90.0);
        calendar.setTime(time);
        int oMonth = calendar.get(Calendar.MONTH);
        assertTrue("getYearsData() does not assign correct months",
                ((int) list.get(0).getX()) == oMonth);
    }


}
