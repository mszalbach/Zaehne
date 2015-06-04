package de.fh.aachen.dental.imagej.converter;

import com.vividsolutions.jts.geom.Coordinate;
import de.fh.aachen.dental.kdtree.KDTree;
import ij.ImagePlus;
import ij.gui.PointRoi;
import skeleton_analysis.AnalyzeSkeleton_;
import skeleton_analysis.Point;
import skeleton_analysis.SkeletonResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by foobar on 04.06.15.
 */
public class FindEndpoints implements Converter {

    protected SkeletonResult result;

    protected boolean shouldConnectEndpoints = false;

    public FindEndpoints(boolean shouldConnectEndpoints) {

        this.shouldConnectEndpoints = shouldConnectEndpoints;
    }

    @Override
    public ImagePlus convert(ImagePlus image) {
        AnalyzeSkeleton_ analyse = new AnalyzeSkeleton_();
        analyse.displaySkeletons = false;

        analyse.setup("", image);

        result = analyse.run(AnalyzeSkeleton_.SHORTEST_PATH, false, true, image, true,
                false, null);

        //TODO remove elements which are to short. How to find all pixel of a result entry?

        List<Coordinate> pointList = new ArrayList<Coordinate>();
        KDTree tree = new KDTree();

        for (Point endpoint : result.getListOfEndPoints()) {
            Coordinate pointToCoordinate = new Coordinate(endpoint.x, endpoint.y, endpoint.z);
            pointList.add(pointToCoordinate);
            tree.insert(pointToCoordinate);
        }

        if (!pointList.isEmpty()) {
            markPointsInImage(image, pointList);

            if(shouldConnectEndpoints) {
                connectEndpoints(image, pointList, tree);
            }

        }


        return image;
    }

    private void connectEndpoints(ImagePlus image, List<Coordinate> pointList, KDTree tree) {
        for (Coordinate coordinate : pointList) {
            //TODO search range maybe configurable or dependen on image size?
            List<Coordinate> neighbours = tree.getNearestNeighbors(coordinate, 10000);

            if (!neighbours.isEmpty()) {
                Coordinate neighbour = neighbours.get(0);
                image.getProcessor().setValue(255);
                image.getProcessor().drawLine((int) coordinate.x, (int) coordinate.y, (int) neighbour.x, (int) neighbour.y);
            }

        }
    }

    private void markPointsInImage(ImagePlus image, List<Coordinate> pointList) {
        int npoints = pointList.size();
        int[] xpoints = new int[npoints];
        int[] ypoints = new int[npoints];

        for (int i = 0; i < npoints; i++) {
            Coordinate point = pointList.get(i);
            xpoints[i] = (int) point.x;
            ypoints[i] = (int) point.y;


        }

        PointRoi points = new PointRoi(xpoints, ypoints, npoints);
        image.setRoi(points);
    }


}
