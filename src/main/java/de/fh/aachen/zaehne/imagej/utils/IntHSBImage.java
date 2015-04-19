package de.fh.aachen.zaehne.imagej.utils;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ColorProcessor;

import java.awt.*;

/**
 * Created by Marcel on 19.04.2015.
 */
public class IntHSBImage extends HSBImage {

    public static final double maxValue = 255.0;
    private int[] hue;
    private int[] saturation;
    private int[] brightness;

    public IntHSBImage(ImagePlus image) {
        super(image);
        this.hue = new int[getHeight() * getWidth()];
        this.saturation = new int[getHeight() * getWidth()];
        this.brightness = new int[getHeight() * getWidth()];
        getHSB(hue, saturation, brightness);
    }

    private void getHSB(int[] hue, int[] saturation, int[] brightness) {
        int c, r, g, b;
        float[] hsb = new float[3];
        ColorProcessor colorProcessor = original.getProcessor().convertToColorProcessor();
        for (int i = 0; i < getWidth() * getHeight(); i++) {
            c = colorProcessor.get(i);
            r = (c & 0xff0000) >> 16;
            g = (c & 0xff00) >> 8;
            b = c & 0xff;
            hsb = Color.RGBtoHSB(r, g, b, hsb);
            hue[i] = (int) (hsb[0] * maxValue);
            saturation[i] = (int) (hsb[1] * maxValue);
            brightness[i] = (int) (hsb[2] * maxValue);
        }
    }

    @Override
    public ImagePlus getHSBStack() {
        return getHSBStack("INT HSB Stack", hue, saturation, brightness);
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
