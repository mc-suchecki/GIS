package pl.edu.pw.elka.gis.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<String, Node> labelsToNodesMap;
    private final Set<UndirectedEdge> edges;

    public Graph(final Map<String, Node> labelsToNodesMap, final Set<UndirectedEdge> edges) {
        this.labelsToNodesMap = labelsToNodesMap;
        this.edges = edges;
    }

    public List<Node> getNodesList() {
        List<Node> nodesList = new ArrayList<Node>();
        for (Node node : labelsToNodesMap.values()) {
            nodesList.add(node);
        }
        return nodesList;
    }
}
