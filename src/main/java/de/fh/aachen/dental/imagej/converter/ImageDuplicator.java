package de.fh.aachen.dental.imagej.converter;

import ij.ImagePlus;
import ij.plugin.Duplicator;

/**
 * Created by marcel on 21.05.15.
 */
public class ImageDuplicator implements Converter {

    @Override
    public ImagePlus convert(ImagePlus image) {
        ImagePlus duplicate =image.duplicate();
        return duplicate;
    }
}
