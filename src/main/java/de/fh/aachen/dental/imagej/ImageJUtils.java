package de.fh.aachen.dental.imagej;

import ij.ImagePlus;

/**
 * Created by marcel on 10.06.15.
 */
public class ImageJUtils {

    private ImageJUtils() {}

    public static void checkNeighborhood(ImagePlus image, int x, int y, int[][] neighborhood) {

        int height = image.getHeight();
        int width = image.getWidth();

        assert(neighborhood.length %2 == 1);
        assert(neighborhood[0].length %2 == 1);

        int regionLength = neighborhood.length;
        int regionAdd = (regionLength-1)/2;

        for( int xi = -regionAdd; xi <= regionAdd; xi++) {
            for(int yi= -regionAdd; yi <=regionAdd;yi++) {
                int xh = x+xi;
                int yh = y+yi;

                if(xh >= 0 && xh<width && yh >=0 && yh <height) {
                    neighborhood[xi+regionAdd][yi+regionAdd] = image.getProcessor().getPixel(xh,yh);
                }
            }
        }

    }

    public static int[] getNeighbor(ImagePlus image, int x, int y, int regionLength) {

        int height = image.getHeight();
        int width = image.getWidth();

        assert (regionLength % 2 == 1);

        int regionAdd = (regionLength - 1) / 2;

        for (int xi = -regionAdd; xi <= regionAdd; xi++) {
            for (int yi = -regionAdd; yi <= regionAdd; yi++) {
                int xh = x + xi;
                int yh = y + yi;

                if (xh > 0 && xh < width && yh > 0 && yh < height) {
                    if (image.getProcessor().getPixel(xh, yh) == 255) {
                        return new int[]{xh, yh};
                    }
                }
            }
        }
        return null;
    }

}
