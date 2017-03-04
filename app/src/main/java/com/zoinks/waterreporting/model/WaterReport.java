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
     * Returns the id of the water report
     * No associated setter
     *
     * @return id of the water report
     */
    public int getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocation() {
        return latitude + "," + longitude;
    }

    @Override
    public String toString() {
        return id + " " + author.getUsername() + " " + timestamp + " " + latitude + "," + longitude;
    }
}
