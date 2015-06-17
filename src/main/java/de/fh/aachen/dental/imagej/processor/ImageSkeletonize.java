package de.fh.aachen.dental.imagej.processor;

import Skeletonize3D_.Skeletonize3D_;
import ij.ImagePlus;

/**
 * Created by foobar on 04.06.15.
 */
public class ImageSkeletonize implements Preprocessor {

    public ImagePlus process(ImagePlus image) {
        Skeletonize3D_ skel = new Skeletonize3D_();
        skel.setup("",image);
        skel.run(image.getProcessor());
        image.setTitle(image.getTitle() + " Skeletonized");
        return image;
    }
}
