package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.converter.FJEdgeDetection;

import javax.swing.*;

/**
 * Created by foobar on 25.05.15.
 */
public class FJEdgeDetectionComponent extends ConverterComponent {


    private final JTextField lowerTextField;
    private final JTextField higherTextField;
    private JCheckBox computeCheckBox;
    private JTextField scaleTextField;
    private JCheckBox suppressCheckBox;

    public FJEdgeDetectionComponent() {
        super("FeatureJ Edge Detection");

        computeCheckBox = new JCheckBox("compute");
        panel.add(computeCheckBox, "gapleft 100px");

        scaleTextField = new JTextField();
        scaleTextField.setColumns(5);
        JLabel scaleLabel = new JLabel("Scale");
        scaleLabel.setLabelFor(scaleTextField);

        panel.add(scaleLabel);
        panel.add(scaleTextField);

        suppressCheckBox = new JCheckBox("suppress");
        panel.add(suppressCheckBox);

        lowerTextField = new JTextField();
        lowerTextField.setColumns(5);
        JLabel lowerLabel = new JLabel("Lower");
        lowerLabel.setLabelFor(lowerTextField);

        panel.add(lowerLabel);
        panel.add(lowerTextField);

        higherTextField = new JTextField();
        higherTextField.setColumns(5);
        JLabel higherLabel = new JLabel("Higher");
        lowerLabel.setLabelFor(higherTextField);

        panel.add(higherLabel);
        panel.add(higherTextField);

        setDefaults();

    }

    @Override
    public void clear() {
        super.clear();
        setDefaults();
    }


    @Override
    public Converter getConverter() {
        return new FJEdgeDetection(computeCheckBox.isSelected(), scaleTextField.getText(),
                suppressCheckBox.isSelected(), lowerTextField.getText(), higherTextField.getText());
    }

    private void setDefaults() {
        computeCheckBox.setSelected(true);
        scaleTextField.setText("1");
        suppressCheckBox.setSelected(true);
        lowerTextField.setText("2");
        higherTextField.setText("1");
    }
}
