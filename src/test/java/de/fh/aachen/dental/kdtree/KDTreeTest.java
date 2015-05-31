package de.fh.aachen.dental.kdtree;

import com.vividsolutions.jts.geom.Coordinate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by foobar on 31.05.15.
 */
public class KDTreeTest {

    @Test
    public void testGetNearestNeighbors() throws Exception {
        KDTree tree = new KDTree();

        tree.insert(new Coordinate(0, 0));
        tree.insert(new Coordinate(1, 1));
        tree.insert(new Coordinate(5, 5));
        tree.insert(new Coordinate(5, 6));
        tree.insert(new Coordinate(6, 6));
        tree.insert(new Coordinate(10, 10));

        assertThat(tree.isEmpty(), is(false));

        assertThat(tree.getNearestNeighbors(new Coordinate(0, 0), 0).get(0), is(new Coordinate(0, 0)));

        assertThat(tree.getNearestNeighbors(new Coordinate(0, 0), 5).get(0), is(new Coordinate(0, 0)));
        assertThat(tree.getNearestNeighbors(new Coordinate(4, 5), 1, 0).get(0), is(new Coordinate(5, 5)));
        assertThat(tree.getNearestNeighbors(new Coordinate(11, 11), 0).isEmpty(), is(true));

    }
}