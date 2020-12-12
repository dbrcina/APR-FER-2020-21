package hr.fer.zemris.apr.hw04.ea.crossover;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * An implementation of {@link Crossover} interface which provides <i>Uniform binary crossover</i>.
 *
 * @author dbrcina
 */
public class UniformBinaryCrossover implements Crossover<Solution<Boolean>> {

    private final Random random;

    public UniformBinaryCrossover(Random random) {
        this.random = random;
    }

    @Override
    public Solution<Boolean> crossover(Solution<Boolean> parent1, Solution<Boolean> parent2) {
        int n = parent1.getNumberOfGenes();
        Solution<Boolean> child = parent1.copy();
        for (int i = 0; i < n; i++) {
            boolean g1 = parent1.getGene(i);
            boolean g2 = parent2.getGene(i);
            boolean r = random.nextBoolean();
            boolean gene = g1 & g2 | r & (g1 ^ g2);
            child.setGene(gene, i);
        }
        return child;
    }

}
