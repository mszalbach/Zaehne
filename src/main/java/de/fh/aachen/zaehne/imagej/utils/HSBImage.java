package de.fh.aachen.zaehne.imagej.utils;

import ij.ImagePlus;
import ij.ImageStack;

/**
 * Created by Marcel on 18.04.2015.
 */
public class HSBImage {




    private String title;
    private int width;
    private int height;
    private byte[] hue;
    private byte[] saturation;
    private byte[] brightness;


    public HSBImage(ImagePlus image) {
        this.title = image.getTitle();
        width = image.getWidth();
        height = image.getHeight();
        int dim = width * height;
        hue = new byte[dim];
        saturation = new byte[dim];
        brightness = new byte[dim];
        //(range -180 => +180
        image.getProcessor().convertToColorProcessor().getHSB(hue, saturation, brightness);
    }


    public ImagePlus getHueImage() {
        return new ImagePlus("Hue", getImageStack("Hue", hue));
    }

    public ImagePlus getSaturationImage() {
        return new ImagePlus("Saturation", getImageStack("Saturation", saturation));
    }

    public ImagePlus getBrightnessImage() {
        return new ImagePlus("Brightness", getImageStack("Brightness", brightness));
    }


    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getHue() {
        return hue.clone();
    }

    private ImageStack getImageStack(String name, byte[] hbs) {
        ImageStack hueStack = new ImageStack(width, height, null);
        hueStack.addSlice(name, hbs);
        return hueStack;
    }
}
