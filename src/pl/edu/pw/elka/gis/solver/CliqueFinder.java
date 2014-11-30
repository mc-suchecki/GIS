/**
 * @author <a href="mailto:jacek.witkowski@gmail.com">Jacek Witkowski</a>
 * Created on 2014-11-2014.
 */
package pl.edu.pw.elka.gis.solver;

import pl.edu.pw.elka.gis.domain.Clique;
import pl.edu.pw.elka.gis.domain.Graph;
import pl.edu.pw.elka.gis.domain.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CliqueFinder {
    public List<Clique> findCliques(final Graph graph) {
        final List<Clique> cliques = new ArrayList<>();
        final List<Node> potentialClique = new ArrayList<>();
        final List<Node> candidates = new ArrayList<>();
        final List<Node> alreadyFound = new ArrayList<>();
        candidates.addAll(graph.getNodesList());

        findCliques(candidates, potentialClique, alreadyFound, cliques);
        return cliques;
    }

    //TODO właściwie, to jest gotowe. Trzeba potestować.
    //TODO zaimplementować metodę bardziej skomplikowaną (z pivotem)
    private void findCliques(final List<Node> candidates, final List<Node> potentialClique,
                             final List<Node> alreadyFound, final List<Clique> cliques) {
        if (candidates.isEmpty() && alreadyFound.isEmpty()) {
            cliques.add(new Clique(potentialClique));
            return;
        }

        final Iterator<Node> iterator = candidates.iterator();
        while (iterator.hasNext()) {
            final Node node = iterator.next();

            final List<Node> newR = new ArrayList<>(potentialClique.size() + 1);
            newR.addAll(potentialClique);
            newR.add(node);

            final Set<Node> neighbours = node.getNeighbours();
            final List<Node> newCandidates = getIntersection(candidates, neighbours);
            final List<Node> newAlreadyFound = getIntersection(alreadyFound, neighbours);
            findCliques(newCandidates, newR, newAlreadyFound, cliques);

            iterator.remove();
            potentialClique.add(node);
        }
    }

    private List<Node> getIntersection(final List<Node> nodesList, final Set<Node> nodeSet) {
        final List<Node> intersection = new ArrayList<>();

        for (final Node node : nodesList) {
            if (nodeSet.contains(node)) {
                intersection.add(node);
            }
        }

        return intersection;
    }
}
