package hr.fer.zemris.apr.hw04.ea.crossover;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * An implementation of {@link Crossover} interface which provides <i>Uniform crossover</i>.
 *
 * @author dbrcina
 */
public class UniformDoubleCrossover implements Crossover<Solution<Double>> {

    private final Random random;

    public UniformDoubleCrossover(Random random) {
        this.random = random;
    }

    @Override
    public Solution<Double> crossover(Solution<Double> parent1, Solution<Double> parent2) {
        Double[] child = new Double[parent1.getNumberOfGenes()];
        for (int i = 0; i < child.length; i++) {
            child[i] = random.nextDouble() < 0.5
                    ? parent1.getGene(i)
                    : parent2.getGene(i);
        }
        return new Solution<>(child);
    }

}
