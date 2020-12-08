package hr.fer.zemris.apr.hw04.ea.crossover;

import hr.fer.zemris.apr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.solution.Solution;

/**
 * An implementation of {@link Crossover} interface which provides <i>uniform binary crossover</i>.
 *
 * @author dbrcina
 */
public class UniformBinaryCrossover implements Crossover<Solution<Boolean>> {

    private final PopulationInitializer<Solution<Boolean>> initializer;

    public UniformBinaryCrossover(PopulationInitializer<Solution<Boolean>> initializer) {
        this.initializer = initializer;
    }

    @Override
    public Solution<Boolean> crossover(Solution<Boolean> parent1, Solution<Boolean> parent2) {
        int n = parent1.getNumberOfGenes();
        Boolean[] child = initializer.generateSolution().getSubGenes(0, n);
        Boolean[] p1 = parent1.getSubGenes(0, n);
        Boolean[] p2 = parent2.getSubGenes(0, n);
        for (int i = 0; i < n; i++) {
            child[i] = p1[i] & p2[i] | child[i] & (p1[i] ^ p2[i]);
        }
        return new Solution<>(child);
    }

}
