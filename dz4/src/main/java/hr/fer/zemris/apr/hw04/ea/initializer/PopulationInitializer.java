package hr.fer.zemris.apr.hw04.ea.initializer;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Models a population initializer for evolutionary algorithms.
 *
 * @param <S> solution type.
 *
 * @author dbrcina
 */
public interface PopulationInitializer<S extends Solution<?>> {

    /**
     * Generates one solution.
     *
     * @return a solution.
     */
    S generateSolution();

    /**
     * Generates a population with the provided {@code size}.
     *
     * @param size population size.
     *
     * @return a generated population.
     *
     * @throws IllegalArgumentException if the provided {@code size} is < 1.
     */
    default List<S> generatePopulation(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size needs to be >= 1!");
        }
        List<S> population = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            population.add(generateSolution());
        }
        return population;
    }

}
