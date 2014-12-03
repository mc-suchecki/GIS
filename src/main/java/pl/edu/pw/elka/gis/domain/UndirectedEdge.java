package pl.edu.pw.elka.gis.domain;

import java.util.Objects;

public class UndirectedEdge {
    private final Node first;
    private final Node second;

    public UndirectedEdge(final Node first, final Node second) {
        this.first = first;
        this.second = second;
    }

    public Node getFirst() {
        return first;
    }

    public Node getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        //edges (A,B) and (B,A) are equal so they should have the same hash code
        return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        //edges (A,B) and (B,A) are equal (non-directed graph)
        final UndirectedEdge other = (UndirectedEdge) obj;
        return (Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second))
                || (Objects.equals(this.first, other.second) && Objects.equals(this.second, other.first));
    }
}
