package de.fh.aachen.zaehne.imagej.converter;

import de.fh.aachen.zaehne.imagej.utils.HSBImage;
import ij.ImagePlus;
import ij.ImageStack;

/**
 * Created by Marcel on 18.04.2015.
 */
public abstract class AbstractHSBConverter implements HSBConverter {

    @Override
    public abstract ImagePlus convert(HSBImage image);

    protected ImagePlus createImage(HSBImage image, Object hue, Object saturation, Object brightness) {
        ImageStack hsbStack = new ImageStack(image.getWidth(), image.getHeight(), null);

        if (hue != null) {
            hsbStack.addSlice("Hue Convert", hue);
        }

        if (saturation != null) {
            hsbStack.addSlice("Saturation Convert", saturation);
        }

        if (brightness != null) {
            hsbStack.addSlice("Brightness Convert", brightness);
        }


        return new ImagePlus(image.getTitle() + " Hue Convert", hsbStack);
    }
}
