package pl.edu.pw.elka.gis.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node implements Comparable<Node>{
    private final String label;
    private final Set<Node> neighbours;

    public Node(final String label) {
        this.label = label;
        this.neighbours = new HashSet<>();
    }

    public String getLabel() {
        return label;
    }

    public Set<Node> getNeighbours() {
        return Collections.unmodifiableSet(neighbours);
    }

    /**
     * @return true if node wasn't already a neighbour for this node.
     */
    public boolean addNeighbour(final Node node) {
        return neighbours.add(node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        return Objects.equals(this.label, other.label);
    }

    @Override
    public int compareTo(final Node n) {
        return label.compareTo(n.label);
    }
}
