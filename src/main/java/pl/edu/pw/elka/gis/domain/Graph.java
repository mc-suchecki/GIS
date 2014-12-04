package pl.edu.pw.elka.gis.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<String, Node> labelsToNodesMap;
    private final Set<UndirectedEdge> edges;

    public Graph(final Map<String, Node> labelsToNodesMap, final Set<UndirectedEdge> edges) {
        this.labelsToNodesMap = labelsToNodesMap;
        this.edges = edges;
    }

    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(labelsToNodesMap.values());
    }

    @SuppressWarnings("unused")
    public Set<UndirectedEdge> getEdges() {
        return Collections.unmodifiableSet(edges);
    }
}
