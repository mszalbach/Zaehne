package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.converter.ImageEdgeConverter;

import javax.swing.*;

/**
 * Created by foobar on 25.05.15.
 */
public class ImageEdgeComponent extends AbstractConverterComponent {


    private JTextField radiusTextField;
    private JTextField alphaTextField;
    private JTextField upperTextField;
    private JTextField lowerTextField;

    public ImageEdgeComponent() {
        super("Image Edge Detection");

        addIndent();
        radiusTextField = addTextField("Radius");
        alphaTextField = addTextField("Alpha");
        lowerTextField = addTextField("Lower");
        upperTextField = addTextField("Higher");

        setDefaults();

    }

    @Override
    public void clear() {
        super.clear();
        setDefaults();
    }


    @Override
    public Converter getConverter() {
        double radius = new Double(radiusTextField.getText());
        float alpha = new Float(alphaTextField.getText());
        float upper = new Float(upperTextField.getText());
        float lower = new Float(lowerTextField.getText());

        return new ImageEdgeConverter(radius,alpha,upper,lower);
    }

    private void setDefaults() {
        radiusTextField.setText("15");
        alphaTextField.setText("1");
        upperTextField.setText("20");
        lowerTextField.setText("5");
    }
}
