package hr.fer.zemris.apr.hw04.ea.selection;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.List;

/**
 * Models a genetic operator <i>selection</i>.
 *
 * @param <S> solution type.
 *
 * @author dbrcina
 */
public interface Selection<S extends Solution<?>> {

    /**
     * Performs selection operation on the provided {@code population}.
     *
     * @param population population.
     *
     * @return selected solution(s).
     */
    S[] select(List<S> population);

}
