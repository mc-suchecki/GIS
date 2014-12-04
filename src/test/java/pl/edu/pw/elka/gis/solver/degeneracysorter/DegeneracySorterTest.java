package pl.edu.pw.elka.gis.solver.degeneracysorter;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.elka.gis.domain.Node;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Test class for DegeneracySorter.
 */
public class DegeneracySorterTest {
    private List<Node> nodesMocks;

    @Before
    public void setUp() throws Exception {
        this.nodesMocks = makeNodesMocks();
    }

    private static List<Node> makeNodesMocks() {
        final Node[] nodesMocks = new Node[7];
        for (int i = 0; i < nodesMocks.length; ++i) {
            nodesMocks[i] = new Node(String.valueOf(i));
        }

        nodesMocks[0].addNeighbour(nodesMocks[1]);
        nodesMocks[0].addNeighbour(nodesMocks[2]);
        nodesMocks[0].addNeighbour(nodesMocks[3]);
        nodesMocks[0].addNeighbour(nodesMocks[4]);

        nodesMocks[1].addNeighbour(nodesMocks[0]);

        nodesMocks[2].addNeighbour(nodesMocks[0]);

        nodesMocks[3].addNeighbour(nodesMocks[0]);

        nodesMocks[4].addNeighbour(nodesMocks[0]);
        nodesMocks[4].addNeighbour(nodesMocks[5]);
        nodesMocks[4].addNeighbour(nodesMocks[6]);

        nodesMocks[5].addNeighbour(nodesMocks[4]);
        nodesMocks[5].addNeighbour(nodesMocks[6]);

        nodesMocks[6].addNeighbour(nodesMocks[4]);
        nodesMocks[6].addNeighbour(nodesMocks[5]);

        return Arrays.asList(nodesMocks);
    }

    @Test
    public void testGetNodesSortedByDegeneracy() throws Exception {
        final List<Node> sortedNodesList = DegeneracySorter.getNodesSortedByDegeneracy(nodesMocks);
        final Node[] sortedNodes = sortedNodesList.toArray(new Node[sortedNodesList.size()]);

        //expected
        final Set<String> firstGroupLabels = Sets.newHashSet("1", "2", "3");
        final Set<String> secondGroupLabels = Sets.newHashSet("0");
        final Set<String> thirdGroupLabels = Sets.newHashSet("4", "5", "6");

        boolean result = true;
        for (int i = 0; i < sortedNodes.length; ++i) {
            final String label = sortedNodes[i].getLabel();
            if(i < 3) {
                if (!firstGroupLabels.contains(label)) {
                    result = false;
                    break;
                } else {
                    firstGroupLabels.remove(label);
                }
            }
            else if(i==3)
            {
                if (!secondGroupLabels.contains(label)) {
                    result = false;
                    break;
                } else {
                    secondGroupLabels.remove(label);
                }
            }
            else
            {
                if (!thirdGroupLabels.contains(label)) {
                    result = false;
                    break;
                } else {
                    thirdGroupLabels.remove(label);
                }
            }
        }

        assertTrue(result && firstGroupLabels.isEmpty() && secondGroupLabels.isEmpty() && thirdGroupLabels.isEmpty());
    }
}

