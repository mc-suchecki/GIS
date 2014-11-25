package pl.edu.pw.elka.gis.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class Node {
    private final String label;
    private final Collection<Node> neighbours;

    public Node(final String label) {
        this.label = label;
        this.neighbours = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public Collection<Node> getNeighbours() {
        return Collections.unmodifiableCollection(neighbours);
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
}
