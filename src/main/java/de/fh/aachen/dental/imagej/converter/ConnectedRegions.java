package de.fh.aachen.dental.imagej.converter;

import ij.ImagePlus;
import util.FindConnectedRegions;

import java.util.List;

/**
 * Created by foobar on 25.05.15.
 */
public class ConnectedRegions implements Converter {

    FindConnectedRegions.Results results;

    @Override
    public ImagePlus convert(ImagePlus image) {
        FindConnectedRegions fcr = new util.FindConnectedRegions();
        results = fcr.run(image,
                true,
                true,
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
