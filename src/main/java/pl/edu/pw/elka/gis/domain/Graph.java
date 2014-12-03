package pl.edu.pw.elka.gis.domain;

import java.util.*;

public class Graph {
    private final Map<String, Node> labelsToNodesMap;
    private final Set<UndirectedEdge> edges;

    public Graph(final Map<String, Node> labelsToNodesMap, final Set<UndirectedEdge> edges) {
        this.labelsToNodesMap = labelsToNodesMap;
        this.edges = edges;
    }

    public List<Node> getNodesList() {
        return new ArrayList<>(labelsToNodesMap.values());
    }

    @SuppressWarnings("unused")
    public Set<UndirectedEdge> getEdges() {
        return Collections.unmodifiableSet(edges);
    }
}
