package de.fh.aachen.zaehne.imagej.utils;

import ij.ImagePlus;
import ij.ImageStack;

/**
 * Created by Marcel on 19.04.2015.
 */
public abstract class HSBImage implements HSBImageInterface {

    protected final ImagePlus original;

    public HSBImage(ImagePlus image) {
        this.original = image;
    }

    @Override
    public int getWidth() {
        return original.getWidth();
    }

    @Override
    public int getHeight() {
        return original.getHeight();
    }

    @Override
    public String getTitle() {
        return original.getTitle();
    }


    protected ImagePlus getHSBStack(String title, Object hue, Object saturation, Object brightness) {
        ImageStack hsbStack = new ImageStack(getWidth(), getHeight(), null);
        hsbStack.addSlice(HUE, hue);
        hsbStack.addSlice(SATURATION, saturation);
        hsbStack.addSlice(BRIGHTNESS, brightness);
        return new ImagePlus(title, hsbStack);
    }

    protected ImagePlus getHueImage(Object hue) {
        return new ImagePlus(HUE, getImageStack(HUE, hue));
    }

    protected ImagePlus getSaturationImage(Object saturation) {
        return new ImagePlus(SATURATION, getImageStack(SATURATION, saturation));
    }

    protected ImagePlus getBrightnessImage(Object brightness) {
        return new ImagePlus(BRIGHTNESS, getImageStack(BRIGHTNESS, brightness));
    }


    private ImageStack getImageStack(String name, Object hbs) {
        ImageStack hueStack = new ImageStack(getWidth(), getHeight(), null);
        hueStack.addSlice(name, hbs);
        return hueStack;
    }


}
