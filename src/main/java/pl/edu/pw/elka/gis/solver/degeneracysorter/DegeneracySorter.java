/**
 * @author <a href="mailto:jacek.witkowski@gmail.com">Jacek Witkowski</a>
 * Created on 2014-12-2014.
 */
package pl.edu.pw.elka.gis.solver.degeneracysorter;

import pl.edu.pw.elka.gis.domain.Node;

import java.util.*;

/**
 * Class sorting a given graph's nodes by degeneracy.
 */
public class DegeneracySorter {
    //TODO Code cleaning
    public static List<Node> getNodesSortedByDegeneracy(final Collection<Node> nodes) {
        final int nodesCount = nodes.size();

        final int maxDegree = getMaxNodesDegree(nodes);
        final Map<Node,Integer> degrees = new HashMap<>();
        for (final Node node : nodes) {
            degrees.put(node, node.getNeighbours().size());
        }

        final Integer[] degreeBucketsStarts = new Integer[maxDegree+1];//extra bucket for 0 degree
        for (int i = 0; i < degreeBucketsStarts.length; ++i) {
            degreeBucketsStarts[i] = 0;
        }

        //after this loop degreeBucketsStarts[degree] should give the number of nodes of this degree
        for (final Node node : nodes) {
            final int nodesDegree = degrees.get(node);
            ++degreeBucketsStarts[nodesDegree];
        }


        //after this loop degreeBucketsStarts[i] should give the index of nodeArray where nodes of degree==i starts
        int start = 0;
        for (int d = 0; d <= maxDegree; ++d) {
            final Integer numOfNodes = degreeBucketsStarts[d];
            degreeBucketsStarts[d] = start;
            start += numOfNodes;
        }

        final Map<Node, Integer> positions = new HashMap<>();
        final Node[] nodeArray = new Node[nodesCount];

        //after this loop nodeArray should contain sorted nodes by their degree (ascending order)
        for (final Node node : nodes) {
            final int nodesDegree = degrees.get(node);
            final int position = degreeBucketsStarts[nodesDegree];
            positions.put(node, position);
            nodeArray[position] = node;
            ++degreeBucketsStarts[nodesDegree];
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

    private static void performSorting(final Map<Node, Integer> degrees, final Integer[] degreeBucketsStarts,
                                       final Node[] nodeArray, final Map<Node, Integer> positions) {
        for (int i = 0; i < nodeArray.length; ++i) {
            final Node node = nodeArray[i];
            for (final Node neighbour : node.getNeighbours()) {
                final Integer nodeDegree = degrees.get(node);
                final Integer neighbourDegree = degrees.get(neighbour);
                if (neighbourDegree > nodeDegree) {
                    final Integer neighbourPosition = positions.get(neighbour);
                    final Integer bucketsStartPosition = degreeBucketsStarts[neighbourDegree];
                    final Node firstInTheBucket = nodeArray[bucketsStartPosition];
                    if (!neighbour.equals(firstInTheBucket)) {
                        swap(nodeArray, positions, neighbour, neighbourPosition, firstInTheBucket, bucketsStartPosition);
                    }
                    ++degreeBucketsStarts[neighbourDegree];
                    degrees.put(neighbour,neighbourDegree);
                }
            }
        }
    }

    private static void swap(final Node[] nodeArray, final Map<Node, Integer> positions,
                             final Node neighbour, final Integer neighbourPosition,
                             final Node firstInTheBucket, final Integer bucketsStartPosition) {
        positions.put(neighbour, bucketsStartPosition);
        positions.put(firstInTheBucket, neighbourPosition);
        nodeArray[neighbourPosition] = firstInTheBucket;
        nodeArray[bucketsStartPosition] = neighbour;
    }

    private static int getMaxNodesDegree(final Collection<Node> nodes) {
        int maxDegree = -1;
        for (final Node node : nodes) {
            final int nodesDegree = node.getNeighbours().size();
            if (nodesDegree > maxDegree) {
                maxDegree = nodesDegree;
            }
        }
        return maxDegree;
    }
}
