package de.fh.aachen.dental.imagej.converter;

import ij.ImagePlus;
import util.FindConnectedRegions;

/**
 * Created by foobar on 25.05.15.
 */
public class ConnectedRegions implements Converter {

    @Override
    public ImagePlus convert(ImagePlus image) {
        FindConnectedRegions fcr = new util.FindConnectedRegions();
        FindConnectedRegions.Results results = fcr.run(image,
                true,
                false,
                true,
                true,
                false,
                false,
                false,
                100,
                1,
                -1,
                true);
        return results.allRegions;
    }
}
