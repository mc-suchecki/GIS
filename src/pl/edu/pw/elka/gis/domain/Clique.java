/**
 * @author <a href="mailto:jacek.witkowski@gmail.com">Jacek Witkowski</a>
 * Created on 2014-11-2014.
 */
package pl.edu.pw.elka.gis.domain;

import java.util.Set;

public class Clique {
    private final Set<Node> nodeSet;

    public Clique(final Set<Node> nodeSet) {
        this.nodeSet = nodeSet;
    }

    public Set<Node> getNodeSet() {
        return nodeSet;
    }
}
