/**
 * @author <a href="mailto:jacek.witkowski@gmail.com">Jacek Witkowski</a>
 * Created on 2014-11-2014.
 */
package pl.edu.pw.elka.gis.solver;

import pl.edu.pw.elka.gis.domain.Clique;
import pl.edu.pw.elka.gis.domain.Graph;
import pl.edu.pw.elka.gis.domain.Node;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CliqueFinder {
    public List<Clique> findCliques(final Graph graph) {
        final List<Clique> cliques = new LinkedList<>();
        final List<Node> potentialClique = new LinkedList<>();
        final List<Node> candidates = new LinkedList<>();
        final List<Node> alreadyFound = new LinkedList<>();
        candidates.addAll(graph.getNodesList());

        findCliques(candidates, potentialClique, alreadyFound, cliques);
        return cliques;
    }

    //TODO właściwie, to jest gotowe. Trzeba potestować.
    //TODO zaimplementować metodę bardziej skomplikowaną (z pivotem)
    private void findCliques(final List<Node> candidates, final List<Node> partialClique,
                             final List<Node> skipped, final List<Clique> cliques) {
        if (candidates.isEmpty() && skipped.isEmpty()) {
            cliques.add(new Clique(partialClique));
            return;
        }

        final Iterator<Node> iterator = candidates.iterator();
        while (iterator.hasNext()) {
            final Node candidate = iterator.next();

            final List<Node> newPartialClique = new LinkedList<>();
            newPartialClique.addAll(partialClique);
            newPartialClique.add(candidate);

            final Set<Node> neighbours = candidate.getNeighbours();
            final List<Node> newCandidates = getIntersection(candidates, neighbours);
            final List<Node> newSkipped = getIntersection(skipped, neighbours);
            findCliques(newCandidates, newPartialClique, newSkipped, cliques);

            iterator.remove();
            partialClique.add(candidate);
        }
    }

    private List<Node> getIntersection(final List<Node> nodesList, final Set<Node> nodeSet) {
        final List<Node> intersection = new LinkedList<>();

        for (final Node node : nodesList) {
            if (nodeSet.contains(node)) {
                intersection.add(node);
            }
        }

        return intersection;
    }
}
