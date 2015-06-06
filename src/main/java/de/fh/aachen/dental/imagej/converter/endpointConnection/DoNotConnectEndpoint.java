package de.fh.aachen.dental.imagej.converter.endpointConnection;

import ij.ImagePlus;
import skeleton_analysis.SkeletonResult;

/**
 * Created by foobar on 06.06.15.
 */
public class DoNotConnectEndpoint implements EndpointConnectionStrategy {

    @Override
    public void connectEndpoints(ImagePlus image, SkeletonResult result) {
        //This is the null strategy
    }


}
