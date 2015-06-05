package de.fh.aachen.dental.imagej.converter;

import ij.ImagePlus;
import ij.gui.NewImage;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by foobar on 05.06.15.
 */
public class ConnectedRegionsTest {

    private ImagePlus image;
    private ConnectedRegions connectedRegions;

    @Before
    public void setup() {
        image = NewImage.createByteImage("B/W Image", 10, 10, 1, NewImage.FILL_BLACK);
        image.getProcessor().setValue(255);
        image.getProcessor().setBackgroundValue(0);
        connectedRegions = new ConnectedRegions();
    }

    @Test
    public void testConvert() throws Exception {
        image.getProcessor().drawLine(0, 0, 3, 0);
        image.getProcessor().drawLine(0, 2, 2, 2);

        connectedRegions.convert(image);

        assertThat(connectedRegions.getRegionInfoList().size(), is(2));
        assertThat(connectedRegions.getRegionInfoList().get(0).getNumberOfPoints(), is(4));
        assertThat(connectedRegions.getRegionInfoList().get(1).getNumberOfPoints(), is(3));

        ImagePlus region1 = connectedRegions.getRegionInfoList().get(0).getImage();
        ImagePlus region2 = connectedRegions.getRegionInfoList().get(1).getImage();

        assertThat(region1.getProcessor().get(3,0), is(255));
        assertThat(region2.getProcessor().get(2,2), is(255));

    }

    @Test
    public void testKeepOnlyGreatestRegion() throws Exception {
        image.getProcessor().drawLine(0, 0, 3, 0);
        image.getProcessor().drawLine(0, 2, 2, 2);

        connectedRegions.keepOnlyLargestRegion = true;

        ImagePlus largestRegion = connectedRegions.convert(image);


        assertThat(largestRegion.getProcessor().get(3,0), is(255));
        assertThat(largestRegion.getProcessor().get(2,2), is(0));

    }
}