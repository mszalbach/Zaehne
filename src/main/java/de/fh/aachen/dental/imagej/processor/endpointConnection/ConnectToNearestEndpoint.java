package de.fh.aachen.dental.imagej.processor.endpointConnection;

import com.vividsolutions.jts.geom.Coordinate;
import de.fh.aachen.dental.kdtree.KDTree;
import ij.ImagePlus;
import skeleton_analysis.Point;
import skeleton_analysis.SkeletonResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by foobar on 06.06.15.
 */
public class ConnectToNearestEndpoint implements EndpointConnectionStrategy {

    @Override
    public void connectEndpoints(ImagePlus image, SkeletonResult result) {
        List<Coordinate> pointList = new ArrayList<Coordinate>();
        KDTree tree = new KDTree();

        for (Point endpoint : result.getListOfEndPoints()) {
            Coordinate pointToCoordinate = new Coordinate(endpoint.x, endpoint.y, endpoint.z);
            pointList.add(pointToCoordinate);
            tree.insert(pointToCoordinate);
        }

        if (!pointList.isEmpty()) {
            connectEndpoints(image, pointList, tree);
        }

    }

    private void connectEndpoints(ImagePlus image, List<Coordinate> pointList, KDTree tree) {
        image.getProcessor().setValue(255);
        for (Coordinate coordinate : pointList) {
            List<Coordinate> neighbours = tree.getNearestNeighbors(coordinate, image.getWidth() / 2.0, image.getHeight() / 2.0);

            if (!neighbours.isEmpty()) {
                Coordinate neighbour = neighbours.get(0);
                image.getProcessor().drawLine((int) coordinate.x, (int) coordinate.y, (int) neighbour.x, (int) neighbour.y);
            }

        }
    }
}
