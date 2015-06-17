package de.fh.aachen.dental.imagej.processor;

import ij.ImagePlus;
import ij.gui.NewImage;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by foobar on 22.05.15.
 */
public class ImageResizeTest {
    ImagePlus image;

    @Before
    public void init() {
        image = NewImage.createByteImage("Resizable Image", 1000, 1000, 1, NewImage.FILL_BLACK);
    }


    @Test
    public void test_resize() throws InterruptedException {
        ImagePlus resizedImage = new ImageResize(500,500,2).process(image);
        assertThat(resizedImage.getWidth(),is(500) );
        assertThat(resizedImage.getHeight(),is(500) );
    }

    @Test
    public void test_no_resize() throws InterruptedException {
        ImagePlus resizedImage = new ImageResize(1500,1500,2).process(image);
        assertThat(resizedImage.getWidth(),is(1000) );
        assertThat(resizedImage.getHeight(),is(1000) );
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_resize_with_factor_one() throws InterruptedException {
        new ImageResize(1500,1500,1).process(image);
    }
}
