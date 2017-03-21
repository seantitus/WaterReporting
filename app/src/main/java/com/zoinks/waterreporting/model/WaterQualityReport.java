package com.zoinks.waterreporting.model;

import java.util.Date;

/**
 * Information holder - represents a single water quality report in the model
 * Only workers and managers may create one
 *
 * Created by Nancy on 03/19/2017.
 */

public class WaterQualityReport extends WaterReport {
    private WaterQualityCondition condition;
    private double virusPPM;
    private double contaminantPPM;

    public WaterQualityReport(Date timestamp, User author, double latitude, double longitude,
                              WaterQualityCondition cond, double virusPPM, double contaminantPPM) {
        super(timestamp, author, latitude, longitude);
        this.condition = cond;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }

    @Override
    public String getSnippet() {
        return condition.toString() + " / Virus: " + virusPPM + " / Contaminant: " + contaminantPPM;
    }

    @Override
    public String toString() {
        return super.toString() + " " + condition + " / Virus: " + virusPPM + " / Contaminant: "
                + contaminantPPM;
    }

    /**
     * Gets virus PPM for the report
     *
     * @return virus PPM for the report
     */
    public double getVirusPPM() {
        return virusPPM;
    }

    /**
     * Gets contaminant PPM for the report
     * @return contaminant PPM for the report
     */
    public double getContaminantPPM() {
        return contaminantPPM;
    }
}
