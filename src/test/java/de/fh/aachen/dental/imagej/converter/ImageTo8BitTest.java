package de.fh.aachen.dental.imagej.converter;

import ij.ImagePlus;
import ij.gui.NewImage;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by foobar on 22.05.15.
 */
public class ImageTo8BitTest {

    @Test
    public void test_to_8Bit() throws InterruptedException {
        ImagePlus image = NewImage.createRGBImage("Color Image", 1000, 1000, 1, NewImage.FILL_RANDOM);
        assertThat(image.getProcessor().isGrayscale(), is(false));
        ImagePlus grayImage = new ImageTo8Bit().convert(image);

        assertThat(grayImage.getProcessor().isGrayscale(),is(true) );
    }
}
