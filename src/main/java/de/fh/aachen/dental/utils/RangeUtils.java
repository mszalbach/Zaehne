package de.fh.aachen.dental.utils;

/**
 * Created by foobar on 16.04.15.
 */
public class RangeUtils {

    private RangeUtils() {
    }

    public static double convertValueToNewRange(double value, Range oldRange, Range newRange) {
        int oldRangeDistance = oldRange.getMaxValue() - oldRange.getMinValue();
        int newRangeDistance = newRange.getMaxValue() - newRange.getMinValue();
        if (oldRangeDistance == 0) {
            return newRange.getMinValue();
        }
        double newValue = ((value - oldRange.getMinValue()) * newRangeDistance / oldRangeDistance) + newRange.getMinValue();

        return newValue;

    }
}
