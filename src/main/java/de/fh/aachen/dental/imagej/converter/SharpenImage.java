package de.fh.aachen.dental.imagej.converter;

import ij.ImagePlus;

/**
 * Created by marcel on 21.05.15.
 */
public class SharpenImage implements Converter {


    @Override
    public ImagePlus convert(ImagePlus image) {
        image.getProcessor().sharpen();
        return image;
    }
}