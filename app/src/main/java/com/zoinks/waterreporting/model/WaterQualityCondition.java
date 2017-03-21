package com.zoinks.waterreporting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The three water quality conditions that water quality reports can indicate
 *
 * Created by Nancy on 03/19/2017.
 */

public enum WaterQualityCondition {
    SAFE(0), TREATABLE(1), UNSAFE(2);

    private final int ordinal;

    WaterQualityCondition(int ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public String toString() {
        switch (this) {
            case SAFE:
                return "Safe";
            case TREATABLE:
                return "Treatable";
            case UNSAFE:
                return "Unsafe";
            default:  // should never get here
                return "Error";
        }
    }

    /**
     * Returns the ordinal int associated with the water quality condition
     *
     * @return the ordinal int associated with the water quality condition
     */
    public int getOrdinal() {
        return ordinal;
    }

    /**
     * Gets a list of the string values of the water quality conditions (for spinner population)
     *
     * @return a list of the string values of the water quality conditions (for spinner population)
     */
    public static List<String> getValues() {
        List<String> list = new ArrayList<>();
        list.add("Safe");
        list.add("Treatable");
        list.add("Unsafe");
        return list;
    }

    /**
     * Gets the WaterQualityCondition associated with a certain ordinal
     *
     * @param ordinal the ordinal for which to get the WaterQualityCondition
     * @return the WaterQualityCondition associated with a certain ordinal
     */
    public static WaterQualityCondition get(int ordinal) {
        switch (ordinal) {
            case 0:
                return SAFE;
            case 1:
                return TREATABLE;
            case 2:
                return UNSAFE;
            default:
                return null;
        }
    }
}
