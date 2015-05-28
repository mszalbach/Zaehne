package de.fh.aachen.dental.imagej.converter;

import ij.IJ;
import ij.ImagePlus;
import imagescience.feature.Edges;
import imagescience.image.Aspects;
import imagescience.image.FloatImage;
import imagescience.image.Image;
import imagescience.segment.Thresholder;
import imagescience.utility.Progressor;

/**
 * Created by foobar on 25.05.15.
 */
public class FJEdgeDetection implements Converter {

    private final boolean compute;
    private final String scale;
    private final boolean suppress;
    private final String lower;
    private final String higher;

    public FJEdgeDetection(boolean compute,
                           String scale,
                           boolean suppress,
                           String lower,
                           String higher) {

        this.compute = compute;
        this.scale = scale;
        this.suppress = suppress;
        this.lower = lower;
        this.higher = higher;
    }

    //return run(image, true, "1", true, "2", "1");


    @Override
    public ImagePlus convert(ImagePlus image) {
        return run(image, compute, scale, suppress, lower, higher);
    }


    ImagePlus run(
            final ImagePlus imp,
            final boolean compute,
            final String scale,
            final boolean suppress,
            final String lower,
            final String higher
    ) {

        try {
            double scaleval, lowval = 0, highval = 0;
            boolean lowthres = true, highthres = true;
            try {
                scaleval = Double.parseDouble(scale);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid smoothing scale value");
            }
            try {
                if (lower.equals("")) lowthres = false;
                else lowval = Double.parseDouble(lower);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid lower threshold value");
            }
            try {
                if (higher.equals("")) highthres = false;
                else highval = Double.parseDouble(higher);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid higher threshold value");
            }
            final int thresmode = (lowthres ? 10 : 0) + (highthres ? 1 : 0);

            final Image img = Image.wrap(imp);
            Image newimg = new FloatImage(img);

            double[] pls = {0, 1};
            int pl = 0;
            if ((compute || suppress) && thresmode > 0)
                pls = new double[]{0, 0.9, 1};
            final Progressor progressor = new Progressor();
            progressor.display(true);

            if (compute || suppress) {
                final Aspects aspects = newimg.aspects();
                if (false) newimg.aspects(new Aspects());
                final Edges edges = new Edges();
                progressor.range(pls[pl], pls[++pl]);
                edges.progressor.parent(progressor);
                newimg = edges.run(newimg, scaleval, suppress);
                newimg.aspects(aspects);
            }

            if (thresmode > 0) {
                final Thresholder thres = new Thresholder();
                progressor.range(pls[pl], pls[++pl]);
                thres.progressor.parent(progressor);
                switch (thresmode) {
                    case 1: {
                        thres.hard(newimg, highval);
                        break;
                    }
                    case 10: {
                        thres.hard(newimg, lowval);
                        break;
                    }
                    case 11: {
                        thres.hysteresis(newimg, lowval, highval);
                        break;
                    }
                }
            }
            return newimg.imageplus();


        } catch (OutOfMemoryError e) {
            IJ.error("Not enough memory for this operation");

        } catch (IllegalArgumentException e) {
            IJ.error(e.getMessage());

        } catch (IllegalStateException e) {
            IJ.error(e.getMessage());

        } catch (Throwable e) {
            IJ.error("An unidentified error occurred while running the plugin");

        }
        return imp;
    }
}
