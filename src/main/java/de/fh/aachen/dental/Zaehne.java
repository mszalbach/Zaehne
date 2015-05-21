package de.fh.aachen.dental;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.converter.ImageDuplicator;
import de.fh.aachen.dental.imagej.converter.ImageTo8Bit;
import de.fh.aachen.dental.imagej.converter.ImageResize;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.GUI;
import ij.plugin.frame.PlugInFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Marcel on 16.04.2015.
 */
public class Zaehne extends PlugInFrame {

    private ImagePlus originalImage;

    private LinkedList<Converter> activeConverters = new LinkedList<Converter>();

    private Map<String,Converter> converterMap = new LinkedHashMap<String, Converter>();
    private Map<String,JCheckBox> checkBoxMap = new LinkedHashMap<String, JCheckBox>();

    public Zaehne() {
        super("Dental detection");

        converterMap.put("ImageDuplicator", new ImageDuplicator());
        converterMap.put("ImageResize", new ImageResize(1000, 1000, 2));
        converterMap.put("8-bit", new ImageTo8Bit());
        init();
    }


    public void init() {

        setLayout(new FlowLayout());

        for (String converterName : converterMap.keySet()) {
            JCheckBox checkBox = new JCheckBox(converterName);
            checkBoxMap.put(converterName, checkBox);
            add(checkBox);
        }


        JButton convert = new JButton("Detect");

        convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addConverter();
                convert();
            }
        });


        add(convert);


        pack();
        GUI.center(this);
        setMinimumSize(new Dimension(400, 50));
        setVisible(true);

    }

    private void addConverter() {
        for(Map.Entry<String,JCheckBox> checkBoxEntry : checkBoxMap.entrySet()) {
            if(checkBoxEntry.getValue().isSelected()) {
                activeConverters.add(converterMap.get(checkBoxEntry.getKey()));
            }

        }
    }

    private void convert() {
        originalImage = IJ.getImage();
        for(Converter converter : activeConverters) {
            originalImage=converter.convert(originalImage);
        }
        originalImage.show();
    }
}
