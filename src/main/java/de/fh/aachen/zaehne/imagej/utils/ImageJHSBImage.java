package de.fh.aachen.zaehne.imagej.utils;

import ij.ImagePlus;
import ij.ImageStack;

/**
 * Created by Marcel on 18.04.2015.
 */
public class ImageJHSBImage extends HSBImage {

    private byte[] hue;
    private byte[] saturation;
    private byte[] brightness;


    public ImageJHSBImage(ImagePlus image) {
        super(image);
        int dim = getWidth() * getHeight();
        hue = new byte[dim];
        saturation = new byte[dim];
        brightness = new byte[dim];
        //(range -180 => +180
        image.getProcessor().convertToColorProcessor().getHSB(hue, saturation, brightness);
    }

    @Override
    public ImagePlus getHSBStack() {
        return new ImagePlus("ImageJ HSB Stack", original.getProcessor().convertToColorProcessor().getHSBStack());
    }

    @Override
    public ImagePlus getHueImage() {
        return getHueImage(hue);
    }


    @Override
    public ImagePlus getSaturationImage() {
        return getSaturationImage(saturation);
    }


    @Override
    public ImagePlus getBrightnessImage() {
        return getBrightnessImage(brightness);
    }
}
