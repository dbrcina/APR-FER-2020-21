package hr.fer.zemris.apr.hw04.ea.initializer;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * An implementation of {@link PopulationInitializer} interface that generates solutions whose gene type is modeled
 * through {@link Double}.
 *
 * @author dbrcina
 */
public class RandomDoublePopulationInitializer implements PopulationInitializer<Solution<Double>> {

    private final Random random;
    private final double[] lbs;
    private final double[] ubs;

    public RandomDoublePopulationInitializer(Random random, double[] lbs, double[] ubs) {
        this.random = random;
        this.lbs = lbs;
        this.ubs = ubs;
    }

    @Override
    public Solution<Double> generateSolution() {
        Double[] genes = new Double[lbs.length];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = lbs[i] + random.nextDouble() * (ubs[i] - lbs[i]);
        }
        return new Solution<>(genes);
    }

}
