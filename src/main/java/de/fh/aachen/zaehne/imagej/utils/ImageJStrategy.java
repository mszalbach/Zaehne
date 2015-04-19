package de.fh.aachen.zaehne.imagej.utils;

import ij.ImagePlus;
import ij.ImageStack;

/**
 * Created by Marcel on 18.04.2015.
 */
public class ImageJStrategy implements HSBStrategy {

    @Override
    public Object[] execute(ImagePlus image) {
        int dim = image.getWidth() * image.getHeight();
        byte[] hue = new byte[dim];
        byte[] saturation = new byte[dim];
        byte[] brightness = new byte[dim];
        //(range -180 => +180
        image.getProcessor().convertToColorProcessor().getHSB(hue, saturation, brightness);
        return new Object[]{hue, saturation, brightness};
    }
}
