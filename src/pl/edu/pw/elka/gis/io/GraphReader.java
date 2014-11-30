package pl.edu.pw.elka.gis.io;

import pl.edu.pw.elka.gis.domain.DirectedEdge;
import pl.edu.pw.elka.gis.domain.Graph;
import pl.edu.pw.elka.gis.domain.Node;
import pl.edu.pw.elka.gis.domain.UndirectedEdge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Reads directed graph and converts it to undirected graph by substituting
 * each pair of edges (i,j) and (j,i) with a single undirected edge (unpaired
 * directed edges are removed from the result graph).
 * <p/>
 * Source file contains information about directed graph which is defined
 * in the following manner: each line contains a single directed edge
 * in format LABEL LABEL where the former label is the label of a source node
 * and the latter is the label of a destination node.
 */
public class GraphReader {
    private static final String INPUT_FILE_NAME = "input_graph.txt";
    private static final String LINE_FORMAT_MESSAGE =
            "Each line should have the following format: \"LABEL LABEL\n\"" +
                    "where former is the label of source node and the latter is the label of destination node.";

    private Map<String, Node> nodeMap;
    private Set<DirectedEdge> directedEdgesSet;
    private Set<UndirectedEdge> undirectedEdgesSet;

    public Graph read() throws IOException, MultipleInvocationException, InvalidDataFormatException {
        initReader();

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
            String strLine;
            while ((strLine = br.readLine()) != null) {
                processLine(strLine);
            }
        }
        updateNodesNeighbourhood();

        return new Graph(nodeMap, undirectedEdgesSet);
    }

    private void updateNodesNeighbourhood() {
        for (final UndirectedEdge edge : undirectedEdgesSet) {
            final Node firstNode = edge.getFirst();
            final Node secondNode = edge.getSecond();
            firstNode.addNeighbour(secondNode);
            secondNode.addNeighbour(firstNode);
        }
    }

    private void initReader() throws MultipleInvocationException {
        if (nodeMap != null || undirectedEdgesSet != null || directedEdgesSet != null) {
            throw new MultipleInvocationException();
        } else {
            nodeMap = new HashMap<>();
            directedEdgesSet = new HashSet<>();
            undirectedEdgesSet = new HashSet<>();
        }
    }

    private void processLine(final String strLine)
            throws InvalidDataFormatException {
        final String[] tokens = strLine.split(" ");
        if (tokens.length != 2) {
            throw new InvalidDataFormatException(LINE_FORMAT_MESSAGE);
        }

        final Node srcNode = getOrCreateNode(tokens[0]);
        final Node dstNode = getOrCreateNode(tokens[1]);
        final DirectedEdge readEdge = new DirectedEdge(dstNode, srcNode);
        final DirectedEdge reverseEdge = new DirectedEdge(srcNode, dstNode);

        final boolean added = directedEdgesSet.add(readEdge);
        if (!added) {
            throw new InvalidDataFormatException("Multigraphs are not allowed");
        }
        if (directedEdgesSet.contains(reverseEdge)) {
            undirectedEdgesSet.add(new UndirectedEdge(srcNode, dstNode));
        }
    }

    private Node getOrCreateNode(final String startNodeLabel) {
        Node startNode = nodeMap.get(startNodeLabel);
        if (startNode == null) {
            startNode = new Node(startNodeLabel);
            nodeMap.put(startNodeLabel, startNode);
        }

        return startNode;
    }
}
