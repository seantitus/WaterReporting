package com.zoinks.waterreporting.model;

/**
 * The six types of water that water source reports can indicate
 *
 * Created by Nancy on 03/01/2017.
 */

public enum WaterSourceType {
    BOTTLED (0), WELL (1), STREAM (2), LAKE (3), SPRING (4), OTHER (5);

    private int ordinal;

    WaterSourceType(int ordinal) {
        this.ordinal = ordinal;
    }

    /**
     * Returns the ordinal int associated with the water source type
     *
     * @return the ordinal int associated with the water source type
     */
    public int getOrdinal() {
        return ordinal;
    }
}
