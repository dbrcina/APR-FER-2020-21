package hr.fer.zemris.apr.hw04.ea.selection;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * An implementation of {@link Selection} interface which provides <i>K-tournament selection</i>.
 *
 * @author dbrcina
 */
public class KTournamentSelection<S extends Solution<?>> implements Selection<S> {

    private final Random random = new Random();
    private final int k;

    public KTournamentSelection(int k) {
        if (k < 2) {
            throw new IllegalArgumentException("K needs to be >= 2!");
        }
        this.k = k;
    }

    @Override
    public S[] select(List<S> population) {
        List<S> selected = new ArrayList<>(k);
        while (selected.size() != k) {
            int index = random.nextInt(population.size());
            selected.add(population.get(index));
        }
        selected.sort(Collections.reverseOrder());
        return (S[]) selected.toArray(Solution[]::new);
    }

}
