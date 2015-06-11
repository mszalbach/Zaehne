package de.fh.aachen.dental.imagej;

import ij.ImagePlus;
import ij.gui.NewImage;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by marcel on 10.06.15.
 */
public class ImageJUtilsTest {

    private ImagePlus image;

    @Before
    public void setup() {
        image = NewImage.createByteImage("B/W Image", 10, 10, 1, NewImage.FILL_BLACK);
        image.getProcessor().setValue(255);
        image.getProcessor().setBackgroundValue(0);
    }

    @Test
    public void testGetNeighborhood1() throws Exception {

            image.getProcessor().drawPixel(0, 0);
            image.getProcessor().drawPixel(1,1);

            int[][] neighborhood = new int[3][3];
            ImageJUtils.getNeighborhood(image, 1, 1, neighborhood);

            int[][] expectedNeighborhood = new int[][]{
                    {255,0,0},
                    {0,255,0},
                    {0,0,0},
            };

        assertThat(neighborhood,is(expectedNeighborhood));
    }
}