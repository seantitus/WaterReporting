package com.zoinks.waterreporting.model;

import java.util.Date;

/**
 * Information holder - represents a single water source report in the model
 * Any user can submit one
 *
 * Created by Nancy on 03/01/2017.
 */

public class WaterSourceReport extends WaterReport {
    private final WaterSourceType type;
    private final WaterSourceCondition condition;

    public WaterSourceReport(Date timestamp, User author, double latitude, double longitude,
                             WaterSourceType type, WaterSourceCondition condition) {
        super(timestamp, author, latitude, longitude);
        this.type = type;
        this.condition = condition;
    }

    /**
     * Returns the type of water at the water source for this water source report
     *
     * @return the type of water at the water source for this water source report
     */
    public WaterSourceType getType() {
        return type;
    }

    /**
     * Returns the condition of water at the water source for this water source report
     *
     * @return the condition of water at the water source for this water source report
     */
    public WaterSourceCondition getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return super.toString() + " " + type + " " + condition;
    }
}
