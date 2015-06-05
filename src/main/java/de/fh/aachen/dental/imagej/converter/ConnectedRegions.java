package de.fh.aachen.dental.imagej.converter;

import ij.ImagePlus;
import util.FindConnectedRegions;

/**
 * Created by foobar on 25.05.15.
 */
public class ConnectedRegions implements Converter {

    protected FindConnectedRegions.Results results;
    protected boolean keepOnlyLargestRegion = false;

    public ConnectedRegions() {

    }

    public ConnectedRegions(boolean keepOnlyLargestRegion) {

        this.keepOnlyLargestRegion = keepOnlyLargestRegion;
    }

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

        if (keepOnlyLargestRegion) {
            FindConnectedRegions.Region maxRegion = results.regionInfo.get(0);
            int maxRegionIndex = 0;
            for (int i = 1; i < results.regionInfo.size(); i++) {
                FindConnectedRegions.Region region = results.regionInfo.get(i);
                if (region.compareTo(maxRegion) > 0) {
                    maxRegion = region;
                    maxRegionIndex = i;
                }
            }
            ImagePlus largestRegion = results.perRegion.get(maxRegionIndex);
            largestRegion.setTitle(image.getTitle());
            return largestRegion;
        }

        return results.allRegions;
    }
}
