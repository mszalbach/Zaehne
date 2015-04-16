package de.fh.aachen.zaehne;

/**
 * Created by foobar on 16.04.15.
 */
public class RangeUtils {

    private RangeUtils() {
    }

    public static int convertValueToNewRange(int value, Range oldRange, Range newRange) {
        int oldRangeDistance = oldRange.getMaxValue() - oldRange.getMinValue();
        int newRangeDistance = newRange.getMaxValue() - newRange.getMinValue();
        if (oldRangeDistance == 0) {
            return newRange.getMinValue();
        }
        float newValue = ((value - oldRange.getMinValue()) * newRangeDistance / oldRangeDistance) + newRange.getMinValue();

        return Math.round(newValue);

    }

    public static class Range {
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
}
