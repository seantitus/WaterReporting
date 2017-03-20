package com.zoinks.waterreporting.model;

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
}
