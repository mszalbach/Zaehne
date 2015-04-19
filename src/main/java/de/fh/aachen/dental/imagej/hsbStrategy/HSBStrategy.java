package de.fh.aachen.dental.imagej.hsbStrategy;

import ij.ImagePlus;

/**
 * Created by Marcel on 19.04.2015.
 */
public interface HSBStrategy {
    /**
     * @param image to convert to HSB space
     * @return Object with Hue, Saturation and Brightness Array. Only byte[],short[], float[], int[] are supported by ImageJ API
     */
    Object[] execute(ImagePlus image);
}
