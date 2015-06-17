package de.fh.aachen.dental.imagej.processor;

import ij.ImagePlus;
import ij.process.ImageConverter;

/**
 * Created by marcel on 21.05.15.
 */
public class ImageTo8Bit implements Preprocessor {


    @Override
    public ImagePlus process(ImagePlus image) {

        ImageConverter imageConverter = new ImageConverter(image);
        imageConverter.convertToGray8();
        image.setTitle(image.getTitle() + " 8Bit");
        return image;
    }
}
