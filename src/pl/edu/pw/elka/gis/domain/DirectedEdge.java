/**
 * @author <a href="mailto:jacek.witkowski@gmail.com">Jacek Witkowski</a>
 * Created on 2014-11-2014.
 */
package pl.edu.pw.elka.gis.domain;

import java.util.Objects;

public class DirectedEdge {
    private final Node source;
    private final Node destination;

    public DirectedEdge(final Node source, final Node destination) {

        this.source = source;
        this.destination = destination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final DirectedEdge other = (DirectedEdge) obj;
        return Objects.equals(this.source, other.source) && Objects.equals(this.destination, other.destination);
    }
}
