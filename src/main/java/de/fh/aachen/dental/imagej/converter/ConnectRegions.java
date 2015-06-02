package de.fh.aachen.dental.imagej.converter;

import com.vividsolutions.jts.geom.Coordinate;
import de.fh.aachen.dental.kdtree.KDTree;
import ij.ImagePlus;
import ij.gui.PointRoi;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by foobar on 30.05.15.
 */
public class ConnectRegions implements Converter {

    private static final int WHITE = 255;
    private static final int BLACK = 0;

    private static final int[][] ONE_DOT = new int[][]{
            {0, 0, 0},
            {0, 255, 0},
            {0, 0, 0}
    };


    @Override
    public ImagePlus convert(ImagePlus image) {
        ImageProcessor processor = image.getProcessor();

        List<Coordinate> pointList = new ArrayList<Coordinate>();
        KDTree tree = new KDTree();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int[][] neighborhood = new int[3][3];

                if (processor.getPixel(x, y) != processor.getBackgroundValue()) {
                    //neighborhood method did not work as expected
                    neighborhood[0][0] = processor.getPixel(x - 1, y - 1);
                    neighborhood[1][0] = processor.getPixel(x, y - 1);
                    neighborhood[2][0] = processor.getPixel(x + 1, y - 1);
                    neighborhood[0][1] = processor.getPixel(x - 1, y);
                    neighborhood[1][1] = processor.getPixel(x, y);
                    neighborhood[2][1] = processor.getPixel(x + 1, y);
                    neighborhood[0][2] = processor.getPixel(x - 1, y + 1);
                    neighborhood[1][2] = processor.getPixel(x, y + 1);
                    neighborhood[2][2] = processor.getPixel(x + 1, y + 1);


                    if (Arrays.deepEquals(neighborhood, ONE_DOT)) {
                        pointList.add(new Coordinate(x, y));
                    }
                }
            }
        }


        if (!pointList.isEmpty()) {
            markPointsInImage(image, pointList);

            tree.insert(pointList);

            for (Coordinate coordinate : pointList) {
                List<Coordinate> neighbours = tree.getNearestNeighbors(coordinate, 3);

                if (!neighbours.isEmpty()) {
                    Coordinate neighbour = neighbours.get(0);
                    image.getProcessor().drawLine((int) coordinate.x, (int) coordinate.y, (int) neighbour.x, (int) neighbour.y);
                }

            }


        }
        return image;
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
