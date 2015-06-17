package de.fh.aachen.dental.imagej.processor;

import de.fh.aachen.dental.imagej.processor.endpointConnection.ConnectToNearestEndpoint;
import de.fh.aachen.dental.imagej.processor.endpointConnection.DoNotConnectEndpoint;
import ij.ImagePlus;
import ij.gui.NewImage;
import org.junit.Before;
import org.junit.Test;
import skeleton_analysis.Point;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by foobar on 04.06.15.
 */
public class FindEndpointsTest {

    private FindEndpoints findEndpoints;
    private ImagePlus image;

    @Before
    public void setup() {
        image = NewImage.createByteImage("B/W Image", 10, 10, 1, NewImage.FILL_BLACK);
        image.getProcessor().setValue(255);
        image.getProcessor().setBackgroundValue(0);
        findEndpoints = new FindEndpoints(new DoNotConnectEndpoint());
    }

    @Test
    public void testStraightLine() {
        image.getProcessor().drawLine(0, 0, 3, 0);


        findEndpoints.process(image);

        assertThat(findEndpoints.result.getNumOfTrees(), is(1));
        assertThat("tree 0 has two endpoints", findEndpoints.result.getEndPoints()[0], is(2));
        assertThat("all endpoints belong to tree 0", findEndpoints.result.getListOfEndPoints().size(), is(2));
        assertThat(findEndpoints.result.getAverageBranchLength()[0], is(3.0));
        assertThat(findEndpoints.result.getListOfEndPoints().get(0), is(new Point(0, 0, 0)));
        assertThat(findEndpoints.result.getListOfEndPoints().get(1), is(new Point(3, 0, 0)));
    }

    @Test
    public void testGraph() throws InterruptedException {

        image.getProcessor().drawLine(0, 0, 3, 0);
        image.getProcessor().drawLine(0, 2, 3, 2);

        findEndpoints.process(image);

        assertThat(findEndpoints.result.getNumOfTrees(), is(2));

        assertThat(findEndpoints.result.getGraph()[0].getEdges().get(0).getV1().getPoints().get(0), is(new Point(0, 0, 0)));
        assertThat(findEndpoints.result.getGraph()[0].getEdges().get(0).getV2().getPoints().get(0), is(new Point(3, 0, 0)));

        assertThat(findEndpoints.result.getGraph()[1].getEdges().get(0).getV1().getPoints().get(0), is(new Point(0, 2, 0)));
        assertThat(findEndpoints.result.getGraph()[1].getEdges().get(0).getV2().getPoints().get(0), is(new Point(3, 2, 0)));

    }

    @Test
    public void testConnectEndpoints() {
        image.getProcessor().drawPixel(0, 0);
        image.getProcessor().drawPixel(2, 0);
        findEndpoints.setConnectionStrategy(new ConnectToNearestEndpoint());

        assertThat(image.getProcessor().get(1,0), is(0));

        findEndpoints.process(image);

        assertThat(image.getProcessor().get(1,0), is(255));
    }

}