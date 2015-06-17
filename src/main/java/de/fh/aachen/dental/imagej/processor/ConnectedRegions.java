package de.fh.aachen.dental.imagej.processor;

import ij.ImagePlus;
import util.FindConnectedRegions;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by foobar on 25.05.15.
 */
public class ConnectedRegions implements Preprocessor {

    protected boolean keepOnlyLargestRegion = false;

    private LinkedList<RegionInfo> regionInfoList = new LinkedList<RegionInfo>();

    public ConnectedRegions() {

    }

    public ConnectedRegions(boolean keepOnlyLargestRegion) {

        this.keepOnlyLargestRegion = keepOnlyLargestRegion;
    }

    @Override
    public ImagePlus process(ImagePlus image) {
        FindConnectedRegions fcr = new util.FindConnectedRegions();
        FindConnectedRegions.Results results = fcr.run(image,
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

        for (int i = 0; i < results.regionInfo.size(); i++) {
            FindConnectedRegions.Region region = results.regionInfo.get(i);
            ImagePlus regionImage = results.perRegion.get(i);
            regionInfoList.add(new RegionInfo(regionImage, region));

        }

        if (keepOnlyLargestRegion) {
            Collections.sort(regionInfoList);
            ImagePlus largestRegion = regionInfoList.getLast().getImage();
            largestRegion.setTitle(image.getTitle());
            return largestRegion;
        }

        return results.allRegions;
    }

    public LinkedList<RegionInfo> getRegionInfoList() {
        return regionInfoList;
    }

    public static class RegionInfo implements Comparable<RegionInfo> {

        private ImagePlus image;
        private FindConnectedRegions.Region region;

        public RegionInfo(ImagePlus image, FindConnectedRegions.Region region) {
            this.image = image;
            this.region = region;
        }

        public FindConnectedRegions.Region getRegion() {
            return region;
        }

        public ImagePlus getImage() {
            return image;
        }

        public int getNumberOfPoints() {
            return region.getNumberOfPoints();
        }

        @Override
        public int compareTo(RegionInfo o) {
            return region.compareTo(o.region);
        }

    }
}
