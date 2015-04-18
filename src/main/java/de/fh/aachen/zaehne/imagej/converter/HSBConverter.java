package de.fh.aachen.zaehne.imagej.converter;

import de.fh.aachen.zaehne.imagej.utils.HSBImage;
import ij.ImagePlus;

/**
 * Created by Marcel on 18.04.2015.
 */
public interface HSBConverter {
    ImagePlus convert(HSBImage image);
}
