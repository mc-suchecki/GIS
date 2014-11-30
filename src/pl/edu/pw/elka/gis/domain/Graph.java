package pl.edu.pw.elka.gis.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<String, Node> labelsToNodesMap;

    public Graph(final Map<String, Node> labelsToNodesMap) {
        this.labelsToNodesMap = labelsToNodesMap;
    }

    public List<Node> getNodesList() {
        return new ArrayList<>(labelsToNodesMap.values());
    }

    public Node getNode(final String label) {
        return labelsToNodesMap.get(label);
    }
}
