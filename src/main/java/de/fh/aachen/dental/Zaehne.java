package de.fh.aachen.dental;

import de.fh.aachen.dental.imagej.converter.*;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.GUI;
import ij.plugin.frame.PlugInFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Marcel on 16.04.2015.
 */
public class Zaehne extends PlugInFrame {

    private List<Converter> activeConverters = new LinkedList<Converter>();

    private Map<String, Converter> availableConverterMap = new LinkedHashMap<String, Converter>();
    private List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();

    public Zaehne() {
        super("Dental detection");

        availableConverterMap.put("Image Duplicator", new ImageDuplicator());
        availableConverterMap.put("Image Resize", new ImageResize(1000, 1000, 2));
        availableConverterMap.put("8-bit", new ImageTo8Bit());
        availableConverterMap.put("Find Connected Regions",new ConnectedRegions());
        init();
    }


    public void init() {

        JPanel panel = new JPanel(new MigLayout());
        add(panel);

        for (String converterName : availableConverterMap.keySet()) {
            JCheckBox checkBox = new JCheckBox(converterName);
            checkBox.setName(converterName);
            checkBoxList.add(checkBox);
            panel.add(checkBox,"wrap");
        }

        JButton okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addConverter();
                convert();
            }
        });

        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           close();
            }
        });


        panel.add(okButton,"tag ok");
        panel.add(cancelButton,"tag cancel");


        pack();
        GUI.center(this);
        setVisible(true);
    }

    private void addConverter() {
        activeConverters.clear();
        for (JCheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                activeConverters.add(availableConverterMap.get(checkBox.getName()));
            }

        }
    }

    private void convert() {
        ImagePlus originalImage = IJ.getImage();
        for (Converter converter : activeConverters) {
            originalImage = converter.convert(originalImage);
        }
        originalImage.show();
    }
}
