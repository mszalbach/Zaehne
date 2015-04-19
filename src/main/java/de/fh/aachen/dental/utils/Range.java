package de.fh.aachen.dental.utils;

/**
 * Created by Marcel on 18.04.2015.
 */
public class Range {
    private int minValue;
    private int maxValue;

    public Range(int minValue, int maxValue) {

        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return "{" + minValue + "," + maxValue + '}';
    }
}
