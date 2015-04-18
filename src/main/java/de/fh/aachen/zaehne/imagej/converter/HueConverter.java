package de.fh.aachen.zaehne.imagej.converter;

import de.fh.aachen.zaehne.imagej.utils.HSBImage;
import de.fh.aachen.zaehne.utils.Range;
import de.fh.aachen.zaehne.utils.RangeUtils;
import ij.ImagePlus;

/**
 * Created by Marcel on 18.04.2015.
 */
public class HueConverter extends AbstractHSBConverter {


    @Override
    public ImagePlus convert(HSBImage image) {
        byte[] hue = image.getHue();

        float[] hueFloat = new float[image.getWidth() * image.getHeight()];
        for (int i = 0; i < hue.length; i++) {
            hueFloat[i] = (float) RangeUtils.convertValueToNewRange(hue[i], new Range(-180, 180), new Range(0, 360));
        }

        return createImage(image, hueFloat, null, null);
    }
}
