package de.fh.aachen.zaehne.imagej.utils;

import ij.ImagePlus;
import ij.ImageStack;

/**
 * Created by Marcel on 19.04.2015.
 */
public class HSBImage {

    private static final String HUE = "Hue";
    private static final String SATURATION = "Saturation";
    private static final String BRIGHTNESS = "Brightness";
    protected final ImagePlus original;
    protected String title;
    protected Object hue;
    protected Object saturation;
    protected Object brightness;

    public HSBImage(String title, ImagePlus image, HSBStrategy strategy) {
        this.title = title;
        this.original = image;
        Object[] hsb = strategy.execute(image);
        hue = hsb[0];
        saturation = hsb[1];
        brightness = hsb[2];
    }

    public int getWidth() {
        return original.getWidth();
    }

    public int getHeight() {
        return original.getHeight();
    }

    public String getTitle() {
        return original.getTitle();
    }


    public ImagePlus getHSBStack() {
        ImageStack hsbStack = new ImageStack(getWidth(), getHeight(), null);
        hsbStack.addSlice(HUE, hue);
        hsbStack.addSlice(SATURATION, saturation);
        hsbStack.addSlice(BRIGHTNESS, brightness);
        return new ImagePlus(title, hsbStack);
    }

    public ImagePlus getHueImage() {
        return new ImagePlus(HUE, getImageStack(HUE, hue));
    }

    public ImagePlus getSaturationImage() {
        return new ImagePlus(SATURATION, getImageStack(SATURATION, saturation));
    }

    public ImagePlus getBrightnessImage() {
        return new ImagePlus(BRIGHTNESS, getImageStack(BRIGHTNESS, brightness));
    }


    private ImageStack getImageStack(String name, Object hbs) {
        ImageStack hueStack = new ImageStack(getWidth(), getHeight(), null);
        hueStack.addSlice(name, hbs);
        return hueStack;
    }


}
