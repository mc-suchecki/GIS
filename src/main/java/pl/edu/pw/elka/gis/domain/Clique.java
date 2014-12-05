/**
 * @author <a href="mailto:jacek.witkowski@gmail.com">Jacek Witkowski</a>
 * Created on 2014-11-2014.
 */
package pl.edu.pw.elka.gis.domain;

import org.jetbrains.annotations.TestOnly;

import java.util.Collection;
import java.util.Set;

public class Clique {
    private final Set<Node> nodes;

    public Clique(final Set<Node> nodes) {
        this.nodes = nodes;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return "{" + nodes + '}';
    }

    @Override
    public int hashCode() {
        return nodes.hashCode();
    }

    @Override
    @TestOnly
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Clique other = (Clique) obj;
        return nodes.equals(other.nodes);
    }
}
