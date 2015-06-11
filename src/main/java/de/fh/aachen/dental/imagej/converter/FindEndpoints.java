package de.fh.aachen.dental.imagej.converter;

import de.fh.aachen.dental.imagej.converter.endpointConnection.EndpointConnectionStrategy;
import ij.ImagePlus;
import skeleton_analysis.AnalyzeSkeleton_;
import skeleton_analysis.SkeletonResult;

/**
 * Created by foobar on 04.06.15.
 */
public class FindEndpoints implements Converter {

    protected SkeletonResult result;
    private EndpointConnectionStrategy connectionStrategy;


    public FindEndpoints(EndpointConnectionStrategy connectionStrategy) {
        this.connectionStrategy = connectionStrategy;
    }

    @Override
    public ImagePlus convert(ImagePlus image) {
        AnalyzeSkeleton_ analyse = new AnalyzeSkeleton_();
        analyse.displaySkeletons = false;

        analyse.setup("", image);

        result = analyse.run(AnalyzeSkeleton_.NONE, false, true, image, true,
                false, null);

        connectionStrategy.connectEndpoints(image, result);


        return image;
    }

    public void setConnectionStrategy(EndpointConnectionStrategy connectionStrategy) {
        this.connectionStrategy = connectionStrategy;
    }
}
