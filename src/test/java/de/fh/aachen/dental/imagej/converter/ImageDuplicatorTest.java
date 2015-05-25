package de.fh.aachen.dental.imagej.converter;

import ij.ImagePlus;
import ij.gui.NewImage;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by foobar on 25.05.15.
 */
public class ImageDuplicatorTest {

    @Test
    public void test_duplicated_image() throws Exception {
        ImagePlus image = NewImage.createRGBImage("Color Image", 1000, 1000, 1, NewImage.FILL_RANDOM);
        ImagePlus clonedImage = new ImageDuplicator().convert(image);
        assertFalse(image == clonedImage);
        assertThat(clonedImage.getDimensions(),is(image.getDimensions()));
    }
}