package de.fh.aachen.zaehne.imagej.utils;

import ij.ImagePlus;

/**
 * Created by Marcel on 19.04.2015.
 */
public interface HSBImageInterface {
    String SATURATION = "Saturation";
    String BRIGHTNESS = "Brightness";
    String HUE = "Hue";

    ImagePlus getHSBStack();

    ImagePlus getHueImage();

    ImagePlus getSaturationImage();

    ImagePlus getBrightnessImage();

    int getWidth();

    int getHeight();

    String getTitle();
}
