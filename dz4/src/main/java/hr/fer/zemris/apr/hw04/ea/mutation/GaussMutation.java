package hr.fer.zemris.apr.hw04.ea.mutation;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * An implementation of {@link Mutation} interface which implements <i>Gauss mutation</i>.
 *
 * @author dbrcina
 */
public class GaussMutation implements Mutation<Solution<Double>> {

    private final Random random;
    private final double sigma;
    private final double[] lbs;
    private final double[] ubs;

    public GaussMutation(Random random, double sigma, double[] lbs, double[] ubs) {
        this.random = random;
        this.sigma = sigma;
        this.lbs = lbs;
        this.ubs = ubs;
    }

    @Override
    public Solution<Double> mutate(Solution<Double> solution) {
        for (int i = 0; i < solution.getNumberOfGenes(); i++) {
            double newValue = solution.getGene(i) + random.nextGaussian() * sigma;
            if (newValue < lbs[i]) newValue = lbs[i];
            else if (newValue > ubs[i]) newValue = ubs[i];
            solution.setGene(newValue, i);
        }
        return solution;
    }

}
