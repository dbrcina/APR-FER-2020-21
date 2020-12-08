package hr.fer.zemris.apr.hw04.ea.crossover;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

/**
 * Models a genetic operator <i>crossover</i>.
 *
 * @param <S> solution type.
 *
 * @author dbrcina
 */
public interface Crossover<S extends Solution<?>> {

    /**
     * Performs crossover operation on the provided parents.
     *
     * @param parent1 first parent.
     * @param parent2 second parent.
     *
     * @return child as a result.
     */
    S crossover(S parent1, S parent2);

}
