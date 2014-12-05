package pl.edu.pw.elka.gis.solver.degeneracysorter;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.elka.gis.domain.Node;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Test class for DegeneracySorter.
 */
public class DegeneracySorterTest {
    private Node[] nodesMocks;
    private DegeneracySorter degeneracySorter;

    @Before
    public void setUp() throws Exception {
        this.nodesMocks = makeNodesMocks();
        this.degeneracySorter = new DegeneracySorter();
    }

    private static Node[] makeNodesMocks() {
        final Node[] nodesMocks = new Node[7];
        for (int i = 0; i < nodesMocks.length; ++i) {
            nodesMocks[i] = new Node(String.valueOf(i));
        }

        nodesMocks[0].addNeighbours(nodesMocks[1], nodesMocks[2], nodesMocks[3], nodesMocks[4]);
        nodesMocks[1].addNeighbours(nodesMocks[0]);
        nodesMocks[2].addNeighbours(nodesMocks[0]);
        nodesMocks[3].addNeighbours(nodesMocks[0]);
        nodesMocks[4].addNeighbours(nodesMocks[0], nodesMocks[5], nodesMocks[6]);
        nodesMocks[5].addNeighbours(nodesMocks[4], nodesMocks[6]);
        nodesMocks[6].addNeighbours(nodesMocks[4], nodesMocks[5]);

        return nodesMocks;
    }

    @Test
    public void testGetNodesSortedByDegeneracy() throws Exception {
        final List<Node> sortedNodesList = degeneracySorter.getNodesSortedByDegeneracy(Arrays.asList(nodesMocks));
        final Node[] sortedNodes = sortedNodesList.toArray(new Node[sortedNodesList.size()]);

        //expected
        final Set<Node> firstGroup = Sets.newHashSet(nodesMocks[1], nodesMocks[2], nodesMocks[3]);
        final Set<Node> secondGroup = Sets.newHashSet(nodesMocks[0]);
        final Set<Node> thirdGroup = Sets.newHashSet(nodesMocks[4], nodesMocks[5], nodesMocks[6]);

        final boolean testPassed = Objects.equals(nodesMocks.length, sortedNodes.length)
                             && firstGroup.containsAll(sortedNodesList.subList(0,3))
                             && secondGroup.containsAll(sortedNodesList.subList(3, 4))
                             && thirdGroup.containsAll(sortedNodesList.subList(4,nodesMocks.length));

        assertTrue("Expected: (1 2 3) 0 (4 5 6), got: " + sortedNodesList, testPassed);
    }
}

