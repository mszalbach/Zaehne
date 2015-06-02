package de.fh.aachen.dental.imagej.converter;

import ij.ImagePlus;
import ij.gui.NewImage;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by foobar on 31.05.15.
 */
public class ConnectRegionsTest {

    @Test
    public void testConvert() throws Exception {
        ImagePlus image = NewImage.createByteImage("B/W Image", 5, 5, 1, NewImage.FILL_BLACK);

        image.getProcessor().setValue(255);
        image.getProcessor().setBackgroundValue(0);


        image.getProcessor().set(0,0,255);
        image.getProcessor().set(2,0,255);

        ImagePlus convertedImage = new ConnectRegions().convert(image);

        assertThat(convertedImage.getProcessor().get(1,0), is(255));
    }

}