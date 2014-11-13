package pl.edu.pw.elka.gis.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<String, Node> labelsToNodesMap;
    private final Set<UndirectedEdge> undirectedEdges;

    public Graph(final Map<String, Node> labelsToNodesMap, final Set<UndirectedEdge> undirectedEdges) {
        this.labelsToNodesMap = labelsToNodesMap;
        this.undirectedEdges = undirectedEdges;
    }

    public List<Node> getNodesList() {
        final List<Node> nodesList = new ArrayList<Node>();
        for (final Node node : labelsToNodesMap.values()) {
            nodesList.add(node);
        }
        return nodesList;
    }
}
