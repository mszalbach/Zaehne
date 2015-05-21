package de.fh.aachen.dental.imagej.converter;

import ij.ImagePlus;

/**
 * Created by marcel on 21.05.15.
 */
public interface Converter {
    ImagePlus convert(ImagePlus image);
}