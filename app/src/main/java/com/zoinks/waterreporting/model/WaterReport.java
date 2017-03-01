package com.zoinks.waterreporting.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Information holder - represents a single water report in the model
 *
 * Created by Nancy on 03/01/2017.
 */

public class WaterReport {
    private static int nextId = 0;

    private int id;
    private User author;
    private Date timestamp;

    public WaterReport(User author) {
        this.id = nextId++;
        this.author = author;
        Calendar calendar = Calendar.getInstance();
        this.timestamp = calendar.getTime();
    }

    /**
     * Returns the id of the water report
     * No associated setter
     *
     * @return id of the water report
     */
    public int getId() {
        return this.id;
    }

    public void setAuthor(User newAuthor) {
        author = newAuthor;
    }

    public User getAuthor() {
        return author;
    }

    public void setTimestamp(Date newDate) {
        timestamp = newDate;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
