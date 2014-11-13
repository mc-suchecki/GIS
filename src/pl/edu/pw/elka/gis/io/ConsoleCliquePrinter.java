/**
 * @author <a href="mailto:jacek.witkowski@gmail.com">Jacek Witkowski</a>
 * Created on 2014-11-2014.
 */
package pl.edu.pw.elka.gis.io;

import pl.edu.pw.elka.gis.domain.Clique;
import pl.edu.pw.elka.gis.domain.Node;

import java.util.Collection;

public class ConsoleCliquePrinter implements CliquesPrinter{
    @Override
    public void print(final Collection<Clique> cliquesColl) {
        for (final Clique clique : cliquesColl)
        {
            print(clique);
        }
    }

    private void print(final Clique clique) {
        final StringBuilder sb = new StringBuilder();
        for (final Node node : clique.getNodeSet()) {
            sb.append(node.getLabel());
            sb.append(" ");
        }

        sb.deleteCharAt(clique.getNodeSet().size() - 1);
        System.out.println(sb);
    }
}
