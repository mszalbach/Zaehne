package de.fh.aachen.dental.gui;

import de.fh.aachen.dental.imagej.converter.Converter;
import de.fh.aachen.dental.imagej.converter.FindEndpoints;
import de.fh.aachen.dental.imagej.converter.endpointConnection.ConnectToNearestEndpoint;
import de.fh.aachen.dental.imagej.converter.endpointConnection.DoNotConnectEndpoint;
import de.fh.aachen.dental.imagej.converter.endpointConnection.EndpointConnectionStrategy;

import javax.swing.*;

/**
 * Created by foobar on 04.06.15.
 */
public class FindEndpointsComponent extends AbstractConverterComponent {

    private JComboBox<JComboBoxEntry> strategyComboBox;

    public FindEndpointsComponent() {
        super("Find Endpoints");

        addIndent();

        JComboBoxEntry[] jComboBoxEntries = {
                new JComboBoxEntry<EndpointConnectionStrategy>("Do not Connect Endpoints.", new DoNotConnectEndpoint()),
                new JComboBoxEntry<EndpointConnectionStrategy>("Connect to nearest Endpoint.", new ConnectToNearestEndpoint()),
        };
        strategyComboBox = addComboBox(jComboBoxEntries);

        setDefaults();
    }


    @Override
    public void clear() {
        super.clear();
        setDefaults();
    }

    private void setDefaults() {
        strategyComboBox.setSelectedIndex(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Converter getConverter() {
        JComboBoxEntry<EndpointConnectionStrategy> strategy = (JComboBoxEntry<EndpointConnectionStrategy>) strategyComboBox.getSelectedItem();
        return new FindEndpoints(strategy.getObject());
    }
}
