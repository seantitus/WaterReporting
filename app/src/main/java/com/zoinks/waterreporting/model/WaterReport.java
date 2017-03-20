package com.zoinks.waterreporting.model;

import java.util.Date;

/**
 * Information holder - represents a single water report in the model
 *
 * Created by Nancy on 03/01/2017.
 */

public abstract class WaterReport {
    private static int nextId = 0;

    private final int id;
    private final User author;
    private final Date timestamp;
    private final double latitude;
    private final double longitude;

    WaterReport(Date timestamp, User author, double latitude, double longitude) {
        this.id = nextId++;
        this.author = author;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Creates the snippet to be displayed on the map markers; generally type + condition
     *
     * @return the text to be displayed when a map marker is clicked
     */
    public abstract String getSnippet();

    /**
     * Returns the id of the water report
     * No associated setter
     *
     * @return id of the water report
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the latitude associated with the water report
     *
     * @return the latitude associated with the water report
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude associated with the water report
     *
     * @return the longitude associated with the water report
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets the location (latitude,longitude) associated with the water report
     *
     * @return the location (latitude,longitude) associated with the water report
     */
    public String getLocation() {
        return latitude + ", " + longitude;
    }

    @Override
    public String toString() {
        return id + " " + author.getUsername() + " " + timestamp + " " + latitude + "," + longitude;
    }
}
