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

    public Node getNode(final String label) {
        return labelsToNodesMap.get(label);
    }

    public boolean hasNodes() {
        return !labelsToNodesMap.isEmpty();
    }

    public Graph deepCopy() {
        final Set<UndirectedEdge> clonedEdges = new HashSet<>(edges.size());
        final Map<String, Node> clonedLabelToNodeMap = new HashMap<>(labelsToNodesMap.size());

        for (final UndirectedEdge edge : edges) {
            final String firstLabel = edge.getFirst().getLabel();
            final String secondLabel = edge.getSecond().getLabel();

            final Node clonedFirstNode = getOrCreate(firstLabel,clonedLabelToNodeMap);
            final Node clonedSecondNode = getOrCreate(secondLabel,clonedLabelToNodeMap);

            final UndirectedEdge clonedEdge = new UndirectedEdge(clonedFirstNode, clonedSecondNode);
            clonedEdges.add(clonedEdge);

            clonedFirstNode.addNeighbour(clonedSecondNode);
            clonedSecondNode.addNeighbour(clonedFirstNode);
        }

        return new Graph(clonedLabelToNodeMap, clonedEdges);
    }

    private Node getOrCreate(final String label, final Map<String,Node> labelToNodeMap) {
        Node node = labelToNodeMap.get(label);
        if (node == null) {
            node = new Node(label);
            labelToNodeMap.put(label, node);
        }

        return node;
    }
}
