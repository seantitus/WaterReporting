package com.zoinks.waterreporting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The four water conditions that water source reports can indicate
 *
 * Created by Nancy on 03/01/2017.
 */

public enum WaterSourceCondition {
    WASTE(0), TREATABLE_MUDDY(1), TREATABLE_CLEAR(2), POTABLE(3);

    private final int ordinal;

    WaterSourceCondition(int ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public String toString() {
        switch (this) {
            case WASTE:
                return "Waste";
            case TREATABLE_MUDDY:
                return "Treatable-Muddy";
            case TREATABLE_CLEAR:
                return "Treatable-Clear";
            case POTABLE:
                return "Potable";
            default:
                return "Error";
        }
    }

    /**
     * Returns the ordinal int associated with the water source condition
     *
     * @return the ordinal int associated with the water source condition
     */
    public int getOrdinal() {
        return ordinal;
    }

    /**
     * Gets a list of the string values of the water source conditions (for spinner population)
     *
     * @return a list of the string values of the water source conditions (for spinner population)
     */
    public static List<String> getValues() {
        List<String> list = new ArrayList<>();
        list.add("Waste");
        list.add("Treatable-Muddy");
        list.add("Treatable-Clear");
        list.add("Potable");
        return list;
    }

    /**
     * Gets the WaterSourceCondition associated with a certain ordinal
     *
     * @param ordinal the ordinal for which to get the WaterSourceCondition
     * @return the WaterSourceCondition associated with a certain ordinal
     */
    public static WaterSourceCondition get(int ordinal) {
        switch (ordinal) {
            case 0:
                return WASTE;
            case 1:
                return TREATABLE_MUDDY;
            case 2:
                return TREATABLE_CLEAR;
            case 3:
                return POTABLE;
            default:
                return null;
        }
    }
}

