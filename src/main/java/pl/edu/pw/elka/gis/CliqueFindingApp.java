package pl.edu.pw.elka.gis;

import pl.edu.pw.elka.gis.domain.Clique;
import pl.edu.pw.elka.gis.domain.Graph;
import pl.edu.pw.elka.gis.io.CliquesPrinter;
import pl.edu.pw.elka.gis.io.ConsoleCliquePrinter;
import pl.edu.pw.elka.gis.io.GraphReader;
import pl.edu.pw.elka.gis.solver.CliqueFinder;
import pl.edu.pw.elka.gis.solver.degeneracysorter.DegeneracySorter;

import java.util.List;

public class CliqueFindingApp {

    public static void main(final String[] args) {
        final String fileName = extractInputFilename(args);
        if (fileName == null) {
            return;
        }

        try {
            final GraphReader graphReader = new GraphReader();
            final Graph graph = graphReader.read(fileName);

            final DegeneracySorter degeneracySorter = new DegeneracySorter();
            final CliqueFinder cliqueFinder = new CliqueFinder(degeneracySorter);
            final List<Clique> cliquesList = cliqueFinder.findCliques(graph);

            final CliquesPrinter printer = new ConsoleCliquePrinter();
            printer.print(cliquesList);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractInputFilename(final String[] args) {
        if(args.length == 2 && args[0].equals("-i") && !args[1].equals(""))
        {
            return args[1];
        }
        else
        {
            System.err.println("Usage: bron_kerbosch -i input_file_name");
            return null;
        }
    }
}
