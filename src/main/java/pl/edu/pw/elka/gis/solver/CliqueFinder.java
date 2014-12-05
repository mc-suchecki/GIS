/**
 * @author <a href="mailto:jacek.witkowski@gmail.com">Jacek Witkowski</a>
 * Created on 2014-11-2014.
 */
package pl.edu.pw.elka.gis.solver;

import pl.edu.pw.elka.gis.domain.Clique;
import pl.edu.pw.elka.gis.domain.Graph;
import pl.edu.pw.elka.gis.domain.Node;
import pl.edu.pw.elka.gis.solver.degeneracysorter.DegeneracySorter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CliqueFinder {
    private final DegeneracySorter degeneracySorter;

    public CliqueFinder(final DegeneracySorter degeneracySorter) {
        this.degeneracySorter = degeneracySorter;
    }

    public List<Clique> findCliques(final Graph graph) {
        final List<Clique> cliques = new LinkedList<>();

        final List<Node> candidates = new LinkedList<>();
        candidates.addAll(graph.getNodes());
        final List<Node> alreadyFound = new LinkedList<>();

        final List<Node> nodesInDegeneracyOrder = degeneracySorter.getNodesSortedByDegeneracy(graph.getNodes());
        for (final Node node : nodesInDegeneracyOrder) {
            final List<Node> newPartialClique = new LinkedList<>();
            newPartialClique.add(node);

            final Set<Node> neighbours = node.getNeighbours();
            final List<Node> newCandidates = getIntersection(candidates, neighbours);
            final List<Node> newAlreadyFound = getIntersection(alreadyFound, neighbours);
            findCliquesWithPivoting(newPartialClique, newCandidates, newAlreadyFound, cliques);

            candidates.remove(node);
            alreadyFound.add(node);
        }

        return cliques;
    }

    //TODO test it
    private void findCliquesWithPivoting(final List<Node> partialClique, final List<Node> candidates,
                                         final List<Node> alreadyFound, final List<Clique> cliques) {
        if (candidates.isEmpty() && alreadyFound.isEmpty()) {
            final Clique newClique = new Clique(new HashSet<>(partialClique));
            cliques.add(newClique);
            return;
        }

        final Node pivot = findPivot(candidates, alreadyFound);
        final List<Node> candidatesWithoutPivotNeighbours = getDifference(candidates, pivot.getNeighbours());

        for (final Node candidate : candidatesWithoutPivotNeighbours) {
            final List<Node> newPartialClique = new LinkedList<>();
            newPartialClique.addAll(partialClique);
            newPartialClique.add(candidate);
            final Set<Node> neighbours = candidate.getNeighbours();
            final List<Node> newCandidates = getIntersection(candidates, neighbours);
            final List<Node> newAlreadyFound = getIntersection(alreadyFound, neighbours);

            findCliquesWithPivoting(newPartialClique, newCandidates, newAlreadyFound, cliques);

            candidates.remove(candidate);
            alreadyFound.add(candidate);
        }
    }

    /**
     * Finds nodes which are in the candidates list and are not in the neighbours set.
     *
     * @return nodes in candidates list which are not in neighbours set.
     */
    private List<Node> getDifference(final List<Node> candidates, final Set<Node> neighbours) {
        final List<Node> difference = new LinkedList<>();
        for (final Node node : candidates) {
            if (!neighbours.contains(node)) {
                difference.add(node);
            }
        }

        return difference;
    }

    /**
     * Find a node with biggest number of neighbours in both lists.
     *
     * @param candidates list of candidates for being a clique member
     * @param skipped    list of skipped nodes
     * @return node with biggest number of neighbours (pivot) or null if both lists are empty
     */
    private Node findPivot(final List<Node> candidates, final List<Node> skipped) {
        Node pivot = null;
        int maxNeighboursCount = -1;

        for (final Node node : candidates) {
            final int neighboursCount = node.getNeighbours().size();
            if (neighboursCount > maxNeighboursCount) {
                maxNeighboursCount = neighboursCount;
                pivot = node;
            }
        }

        for (final Node node : skipped) {
            final int neighboursCount = node.getNeighbours().size();
            if (neighboursCount > maxNeighboursCount) {
                maxNeighboursCount = neighboursCount;
                pivot = node;
            }
        }

        return pivot;
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
