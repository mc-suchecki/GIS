package pl.edu.pw.elka.gis.solver;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import pl.edu.pw.elka.gis.domain.Clique;
import pl.edu.pw.elka.gis.domain.Graph;
import pl.edu.pw.elka.gis.domain.Node;
import pl.edu.pw.elka.gis.solver.degeneracysorter.DegeneracySorter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CliqueFinderTest {
    @Test()
    public void testFindCliques() throws Exception {
        final DegeneracySorter degeneracySorter = mock(DegeneracySorter.class);
        final CliqueFinder cliqueFinder = new CliqueFinder(degeneracySorter);
        final Node[] nodes = makeConnectedGraphsNodes();

        final Graph graph = new Graph(Arrays.asList(nodes));
        final List<Node> sortedNodes = Lists.newArrayList(
                nodes[1], nodes[2], nodes[3], nodes[0], nodes[6], nodes[5], nodes[4]);

        when(degeneracySorter.getNodesSortedByDegeneracy(anyCollection())).thenReturn(sortedNodes);

        final Set<Clique> expectedCliques = Sets.newHashSet(
                new Clique(Sets.newHashSet(nodes[4], nodes[6], nodes[5])),
                new Clique(Sets.newHashSet(nodes[0], nodes[1])),
                new Clique(Sets.newHashSet(nodes[0], nodes[2])),
                new Clique(Sets.newHashSet(nodes[0], nodes[3])),
                new Clique(Sets.newHashSet(nodes[4], nodes[0]))
        );

        final List<Clique> cliquesList = cliqueFinder.findCliques(graph);
        final Set<Clique> actualCliques = new HashSet<>(cliquesList);
        assertEquals(expectedCliques, actualCliques);
    }

    private static Node[] makeConnectedGraphsNodes() {
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
}
