package de.fh.aachen.dental.imagej;

import ij.ImagePlus;
import ij.gui.NewImage;
import org.junit.Before;

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

}