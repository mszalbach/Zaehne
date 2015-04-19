package de.fh.aachen.zaehne.imagej.utils;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ColorProcessor;

import java.awt.*;

/**
 * Created by Marcel on 19.04.2015.
 */
public class AwtHSBImage extends HSBImage {

    private float[] hue;
    private float[] saturation;
    private float[] brightness;

    public AwtHSBImage(ImagePlus image) {
        super(image);
        this.hue = new float[getHeight() * getWidth()];
        this.saturation = new float[getHeight() * getWidth()];
        this.brightness = new float[getHeight() * getWidth()];
        getHSB(hue, saturation, brightness);
    }

    private void getHSB(float[] hue, float[] saturation, float[] brightness) {
        int c, r, g, b;
        float[] hsb = new float[3];
        ColorProcessor colorProcessor = original.getProcessor().convertToColorProcessor();
        for (int i = 0; i < getWidth() * getHeight(); i++) {
            c = colorProcessor.get(i);
            r = (c & 0xff0000) >> 16;
            g = (c & 0xff00) >> 8;
            b = c & 0xff;
            hsb = Color.RGBtoHSB(r, g, b, hsb);
            hue[i] = hsb[0];
            saturation[i] = hsb[1];
            brightness[i] = hsb[2];
        }
    }

    @Override
    public ImagePlus getHSBStack() {
        return getHSBStack("AWT HSB Stack", hue, saturation, brightness);
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
