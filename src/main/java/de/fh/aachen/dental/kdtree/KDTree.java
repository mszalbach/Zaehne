package de.fh.aachen.dental.kdtree;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.index.kdtree.KdNode;
import com.vividsolutions.jts.index.kdtree.KdTree;

import java.util.*;

/**
 * Created by foobar on 31.05.15.
 */
public class KDTree {

    KdTree tree;

    public KDTree() {
        this.tree = new KdTree();
    }

    public KDTree(double tolerance) {
        this.tree = new KdTree(tolerance);
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public KdNode insert(Coordinate p) {
        return tree.insert(p);
    }

    public KdNode insert(Coordinate p, Object data) {
        return tree.insert(p, data);
    }

    public List<KdNode> query(Envelope queryEnv) {
        return tree.query(queryEnv);
    }

    public void insert(Collection<Coordinate> collection) {
        for (Coordinate coordinate : collection) {
            tree.insert(coordinate);
        }
    }

    public List<Coordinate> getNearestNeighbors(Coordinate startPoint, double distance) {
        return getNearestNeighbors(startPoint, distance, distance);
    }


    public List<Coordinate> getNearestNeighbors(Coordinate startPoint, double deltaX, double deltaY) {
        Envelope queryEnv = new Envelope(startPoint);
        queryEnv.expandBy(deltaX, deltaY);
        List<KdNode> erg = tree.query(queryEnv);
        List<Coordinate> orderedList = new ArrayList<Coordinate>();
        for (KdNode node : erg) {
            //do not add startPoint because this is not a neighbor
            if(!startPoint.equals(node.getCoordinate())) {
                orderedList.add(node.getCoordinate());
            }
        }

        Collections.sort(orderedList, new EuclideanComparator(startPoint));
        return orderedList;
    }

    private static class EuclideanComparator implements Comparator<Coordinate> {

        private Coordinate startCoordinate;

        public EuclideanComparator(Coordinate startCoordinate) {

            this.startCoordinate = startCoordinate;
        }

        @Override
        public int compare(Coordinate o1, Coordinate o2) {
            Double d1 = startCoordinate.distance(o1);
            Double d2 = startCoordinate.distance(o2);

            return d1.compareTo(d2);
        }
    }
}
