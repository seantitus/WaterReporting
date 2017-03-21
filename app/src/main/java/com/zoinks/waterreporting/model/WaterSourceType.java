package com.zoinks.waterreporting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The six types of water that water source reports can indicate
 *
 * Created by Nancy on 03/01/2017.
 */

public enum WaterSourceType {
    BOTTLED (0), WELL (1), STREAM (2), LAKE (3), SPRING (4), OTHER (5);

    private final int ordinal;

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

    /**
     * Gets a list of the string values of the water source type (for spinner population)
     *
     * @return a list of the string values of the water source type (for spinner population)
     */
    public static List<String> getValues() {
        List<String> list = new ArrayList<>();
        list.add("Bottled");
        list.add("Well");
        list.add("Stream");
        list.add("Lake");
        list.add("Spring");
        list.add("Other");
        return list;
    }

    /**
     * Gets the WaterSourceType associated with a certain ordinal
     *
     * @param ordinal the ordinal for which to get the WaterSourceType
     * @return the WaterSourceType associated with a certain ordinal
     */
    public static WaterSourceType get(int ordinal) {
        switch (ordinal) {
            case 0:
                return BOTTLED;
            case 1:
                return WELL;
            case 2:
                return STREAM;
            case 3:
                return LAKE;
            case 4:
                return SPRING;
            case 5:
                return OTHER;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case BOTTLED:
                return "Bottled";
            case WELL:
                return "Well";
            case STREAM:
                return "Stream";
            case LAKE:
                return "Lake";
            case SPRING:
                return "Spring";
            case OTHER:
                return "Other";
            default:
                return "Error";
        }
    }
}
