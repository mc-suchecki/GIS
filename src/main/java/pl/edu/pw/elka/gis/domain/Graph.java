package pl.edu.pw.elka.gis.domain;

import java.util.Collection;
import java.util.Collections;

public class Graph {
    private final Collection<Node> nodes;

    public Graph(final Collection<Node> nodes) {
        this.nodes = nodes;
    }

    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes);
    }
}
