package com.zoinks.waterreporting.model;

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
}
