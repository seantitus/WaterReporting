package com.zoinks.waterreporting.model;

import java.util.Date;

/**
 * Information holder - represents a single water source report in the model
 * Any user can submit one
 *
 * Created by Nancy on 03/01/2017.
 */

public class WaterSourceReport extends WaterReport {
    public WaterSourceReport(Date timestamp, User author, double latitude, double longitude) {
        super(timestamp, author, latitude, longitude);
    }
}
