package de.fh.aachen.zaehne;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static de.fh.aachen.zaehne.RangeUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by foobar on 16.04.15.
 */
@RunWith(Parameterized.class)
public class RangeUtilsTest {

    private final int value;
    private final Range oldRange;
    private final Range newRange;
    private int solution;

    public RangeUtilsTest(int value, Range oldRange, Range newRange, int solution) {

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
                {5, new Range(1, 10), new Range(1, 20), 9},
                {0, new Range(-5, 5), new Range(0, 20), 10},
                {100, new Range(-180, 180), new Range(0, 255), 198},
        };
    }

    @Test
    public void testConvertByte() throws Exception {
        assertThat(convertValueToNewRange(value, oldRange, newRange), is(solution));
    }
}