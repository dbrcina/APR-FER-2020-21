package hr.fer.zemris.apr.hw04.ea.crossover;

import hr.fer.zemris.apr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.solution.Solution;

/**
 * An implementation of {@link Crossover} interface which provides <i>Uniform binary crossover</i>.
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
        Solution<Boolean> child = initializer.generateSolution();
        for (int i = 0; i < n; i++) {
            Boolean g1 = parent1.getGene(i);
            Boolean g2 = parent2.getGene(i);
            Boolean r = child.getGene(i);
            child.setGene(g1 & g2 | r & (g1 ^ g2), i);
        }
        return child;
    }

}
