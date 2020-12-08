package hr.fer.zemris.apr.hw04.ea.mutation;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

/**
 * Models a genetic operator <i>mutation</i>.
 *
 * @param <S> solution type.
 * @author dbrcina
 */
public interface Mutation<S extends Solution<?>> {

    /**
     * Performs mutation operation on the provided {@code solution}.
     *
     * @param solution solution solution.
     * @return a mutated solution.
     */
    S mutate(S solution);

}
