package pl.edu.pw.elka.gis.io;

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

public class GraphReader {
    private static final String INPUT_FILE_NAME = "input_graph.txt";
    private static final String LINE_FORMAT_MESSAGE = "Each line should have the following format: LABEL LABEL";

    private Map<String, Node> nodeMap;
    private Set<UndirectedEdge> unidirectionalEdgesSet;
    private Set<UndirectedEdge> bidirectionalEdgesSet;

    public Graph read() throws IOException, MultipleInvocationException, InvalidDataFormatException {
        initReader();

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
            String strLine;
            while ((strLine = br.readLine()) != null) {
                processLine(strLine);
            }
        }

        return new Graph(nodeMap, bidirectionalEdgesSet);
    }

    private void initReader() throws MultipleInvocationException {
        if (nodeMap != null || bidirectionalEdgesSet != null || unidirectionalEdgesSet != null) {
            throw new MultipleInvocationException();
        } else {
            nodeMap = new HashMap<>();
            unidirectionalEdgesSet = new HashSet<>();
            bidirectionalEdgesSet = new HashSet<>();
        }
    }

    private void processLine(final String strLine)
            throws InvalidDataFormatException {
        final String[] tokens = strLine.split(" ");
        if (tokens.length != 2) {
            throw new InvalidDataFormatException(LINE_FORMAT_MESSAGE);
        }

        final Node srcNode = getOrCreateNode(tokens[0]);
        final Node destNode = getOrCreateNode(tokens[1]);

        if(unidirectionalEdgesSet.contains(new UndirectedEdge(destNode,srcNode)))
        {
            //TODO
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
