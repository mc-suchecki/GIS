/**
 * @author <a href="mailto:jacek.witkowski@gmail.com">Jacek Witkowski</a>
 * Created on 2014-12-2014.
 */
package pl.edu.pw.elka.gis.solver.degeneracysorter;

import pl.edu.pw.elka.gis.domain.Graph;
import pl.edu.pw.elka.gis.domain.Node;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class sorting a given graph's nodes by degeneracy.
 */
public class DegeneracySorter {
    //TODO Code cleaning
    public static List<Node> getNodesSortedByDegeneracy(final Graph graph) {
        final List<Node> nodesList = graph.getNodesList();
        final int nodesCount = nodesList.size();

        final int maxDegree = getMaxNodesDegree(nodesList);
        final Map<Node,Integer> degrees = new HashMap<>();
        for (final Node node : nodesList) {
            degrees.put(node, node.getNeighbours().size());
        }

        final int[] degreeBucketsStarts = new int[maxDegree];
        for (final Node node : nodesList) {
            final int nodesDegree = degrees.get(node);
            ++degreeBucketsStarts[nodesDegree];
        }

        int start = 0;
        for (int d = 0; d < maxDegree; ++d) {
            final int numOfNodes = degreeBucketsStarts[d];
            degreeBucketsStarts[d] = start;
            start += numOfNodes;
        }

        final Node[] nodeArray = new Node[nodesCount];
        final Map<Node, Integer> positions = new HashMap<>();

        for (final Node node : nodesList) {
            final int nodesDegree = degrees.get(node);
            final int position = degreeBucketsStarts[nodesDegree];
            positions.put(node, position);
            nodeArray[position] = node;
            ++degreeBucketsStarts[position];
        }

        //correction of degreeBucketsStarts (now each starts where actually the next one starts)
        for (int d = maxDegree; d > 0; --d) {
            degreeBucketsStarts[d] = degreeBucketsStarts[d-1];
        }
        degreeBucketsStarts[0] = 0;

        //sorting
        performSorting(degrees, degreeBucketsStarts, nodeArray, positions);

        return Arrays.asList(nodeArray);
    }

    private static void performSorting(final Map<Node, Integer> degrees, final int[] degreeBucketsStarts,
                                       final Node[] nodeArray, final Map<Node, Integer> positions) {
        for (int i = 0; i < nodeArray.length; ++i) {
            final Node node = nodeArray[i];
            for (final Node neighbour : node.getNeighbours()) {
                final Integer nodeDegree = degrees.get(node);
                Integer neighbourDegree = degrees.get(neighbour);
                if (nodeDegree <= neighbourDegree) {
                    continue;
                }

                final Integer neighbourPosition = positions.get(neighbour);
                final Integer bucketsStartPosition = degreeBucketsStarts[neighbourDegree];
                final Node firstInTheBucket = nodeArray[bucketsStartPosition];
                if (!neighbour.equals(firstInTheBucket)) {
                    positions.put(neighbour, bucketsStartPosition);
                    positions.put(firstInTheBucket, neighbourPosition);
                    nodeArray[neighbourPosition] = firstInTheBucket;
                    nodeArray[bucketsStartPosition] = neighbour;
                }
                ++degreeBucketsStarts[neighbourDegree];
                --neighbourDegree;
            }
        }
    }

    private static int getMaxNodesDegree(final List<Node> nodesList) {
        int maxDegree = -1;
        for (final Node node : nodesList) {
            final int nodesDegree = node.getNeighbours().size();
            if (nodesDegree > maxDegree) {
                maxDegree = nodesDegree;
            }
        }
        return maxDegree;
    }
}
