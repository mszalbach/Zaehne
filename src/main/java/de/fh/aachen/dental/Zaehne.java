package de.fh.aachen.dental;

import de.fh.aachen.dental.gui.*;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.GUI;
import ij.plugin.frame.PlugInFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marcel on 16.04.2015.
 */
public class Zaehne extends PlugInFrame {

    private List<IConverterComponent> converterPanels = new LinkedList<IConverterComponent>();

    public Zaehne() {
        super("Dental detection");

        converterPanels.add(new ImageDuplicatorComponent());
        converterPanels.add(new ImageResizeComponent());
        converterPanels.add(new ImageTo8BitComponent());
        converterPanels.add(new FJEdgeDetectionComponent());
        converterPanels.add(new ConnectedRegionsComponent());

        init();
    }


    public void init() {

        JPanel panel = new JPanel(new MigLayout());
        add(panel);

        for (IConverterComponent converterPanel : converterPanels) {
            panel.add(converterPanel.getComponent(), "wrap");
        }


        JButton okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convert();
            }
        });

        JButton clearButton = new JButton("Clear");

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

        panel.add(okButton, "tag ok");
        panel.add(clearButton);
        panel.add(cancelButton, "tag cancel");


        pack();
        GUI.center(this);
        setVisible(true);
    }

    private void clear() {
        for (IConverterComponent converterPanel : converterPanels) {
            converterPanel.setActive(false);
        }
    }


    private void convert() {
        ImagePlus originalImage = IJ.getImage();
        for (IConverterComponent converterPanel : converterPanels) {
            if (converterPanel.isActive()) {
                originalImage = converterPanel.getConverter().convert(originalImage);
            }
        }
        originalImage.show();
    }
}
