package de.fh.aachen.zaehne.utils;

import de.fh.aachen.zaehne.utils.Range;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static de.fh.aachen.zaehne.utils.RangeUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by foobar on 16.04.15.
 */
@RunWith(Parameterized.class)
public class RangeUtilsTest {

    private final double value;
    private final Range oldRange;
    private final Range newRange;
    private double solution;

    public RangeUtilsTest(double value, Range oldRange, Range newRange, double solution) {

        this.value = value;
        this.oldRange = oldRange;
        this.newRange = newRange;
        this.solution = solution;
    }

    @Parameterized.Parameters(name= "{index}: convert {0}: {1}->{2}")
    public static Object[][] data() {
        return new Object[][]{{0, new Range(0, 0), new Range(0, 0), 0},
                {0, new Range(0, 2), new Range(0, 1), 0},
                {2, new Range(0, 2), new Range(0, 1), 1},
                {5, new Range(0, 10), new Range(0, 20), 10},
                {0, new Range(-5, 5), new Range(0, 20), 10},
        };
    }

    @Test
    public void testConvertByte() throws Exception {
        assertThat(convertValueToNewRange(value, oldRange, newRange), is(solution));
    }
}