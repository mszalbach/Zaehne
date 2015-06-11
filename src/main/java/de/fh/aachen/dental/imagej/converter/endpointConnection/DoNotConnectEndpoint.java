package de.fh.aachen.dental.imagej.converter.endpointConnection;

import ij.ImagePlus;
import ij.gui.PointRoi;
import skeleton_analysis.Point;
import skeleton_analysis.SkeletonResult;

import java.util.List;

/**
 * Created by foobar on 06.06.15.
 */
public class DoNotConnectEndpoint implements EndpointConnectionStrategy {

    @Override
    public void connectEndpoints(ImagePlus image, SkeletonResult result) {

        markPointsInImage(image, result.getListOfEndPoints());

    }

    private void markPointsInImage(ImagePlus image, List<Point> pointList) {
        int npoints = pointList.size();
        int[] xpoints = new int[npoints];
        int[] ypoints = new int[npoints];

        for (int i = 0; i < npoints; i++) {
            Point point = pointList.get(i);
            xpoints[i] = point.x;
            ypoints[i] = point.y;


        }

        PointRoi points = new PointRoi(xpoints, ypoints, npoints);
        image.setRoi(points);
    }


}
