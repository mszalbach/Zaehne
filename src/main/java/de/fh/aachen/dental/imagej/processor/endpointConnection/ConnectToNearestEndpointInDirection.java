package de.fh.aachen.dental.imagej.processor.endpointConnection;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.index.kdtree.KdNode;
import de.fh.aachen.dental.imagej.ImageJUtils;
import de.fh.aachen.dental.kdtree.KDTree;
import ij.ImagePlus;
import skeleton_analysis.Point;
import skeleton_analysis.SkeletonResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by foobar on 06.06.15.
 */
public class ConnectToNearestEndpointInDirection implements EndpointConnectionStrategy {

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

            int[] neighbor = ImageJUtils.getNeighbor(image,(int)coordinate.x,(int)coordinate.y,3);
            int xDiff = (int)coordinate.x - neighbor[0];
            int yDiff = (int)coordinate.y - neighbor[1];

            double xDelta = image.getWidth() / 4.0;
            double yDelta = image.getWidth() / 4.0;

            Envelope env = new Envelope(coordinate);

            if(xDiff > 0) {
                //search right
                env.expandToInclude(coordinate.x + image.getWidth() / 4.0, coordinate.y);
                env.expandBy(0,50);
            }

             else if(xDiff < 0) {
                //search left
                env.expandToInclude(coordinate.x - image.getWidth() / 4.0, coordinate.y);
                env.expandBy(0,50);
            } else

            if(xDiff == 0) {
                //search short
            }

            if(yDiff > 0) {
                //search right
                env.expandToInclude(coordinate.x, coordinate.y + image.getWidth() / 4.0);
                env.expandBy(50,0);
            }

            else if(yDiff < 0) {
                //search left
                env.expandToInclude(coordinate.x, coordinate.y - image.getWidth() / 4.0);
                env.expandBy(50,0);
            } else

            if(yDiff == 0) {
                //search short
            }

            List<KdNode> erg = tree.query(env);
            List<Coordinate> neighbours = new ArrayList<Coordinate>();
            for (KdNode node : erg) {
                if(node.getCoordinate() != coordinate ) {
                    neighbours.add(node.getCoordinate());
                }
            }

            Collections.sort(neighbours, new KDTree.EuclideanComparator(coordinate));

            if (!neighbours.isEmpty()) {
                Coordinate neighbour = neighbours.get(0);
                image.getProcessor().drawLine((int) coordinate.x, (int) coordinate.y, (int) neighbour.x, (int) neighbour.y);
            }

        }
    }
}
