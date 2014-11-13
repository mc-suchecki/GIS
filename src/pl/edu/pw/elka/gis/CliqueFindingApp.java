package pl.edu.pw.elka.gis;

import pl.edu.pw.elka.gis.domain.Clique;
import pl.edu.pw.elka.gis.domain.Graph;
import pl.edu.pw.elka.gis.io.GraphReader;
import pl.edu.pw.elka.gis.solver.CliqueFinder;

import java.util.List;

public class CliqueFindingApp {

    public static void main(final String[] args) {
        try {
            final GraphReader graphReader = new GraphReader();
            final Graph graph = graphReader.read();

            final CliqueFinder cliqueFinder = new CliqueFinder();
            final List<Clique> cliquesList = cliqueFinder.findCliques(graph);
            //TODO
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
}
