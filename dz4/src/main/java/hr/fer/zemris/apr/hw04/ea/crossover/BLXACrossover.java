package hr.fer.zemris.apr.hw04.ea.crossover;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * An implementation of {@link Crossover} interface which provides <i>BLX-alpha crossover</i>.
 *
 * @author dbrcina
 */
public class BLXACrossover implements Crossover<Solution<Double>> {

    private final Random random;
    private final double alpha;
    private final double[] lbs;
    private final double[] ubs;

    public BLXACrossover(Random random, double alpha, double[] lbs, double[] ubs) {
        this.random = random;
        this.alpha = alpha;
        this.lbs = lbs;
        this.ubs = ubs;
    }

    @Override
    public Solution<Double> crossover(Solution<Double> parent1, Solution<Double> parent2) {
        Solution<Double> child = parent1.copy();
        for (int i = 0; i < child.getNumberOfGenes(); i++) {
            double ci1 = parent1.getGene(i);
            double ci2 = parent2.getGene(i);
            double ciMin = Math.min(ci1, ci2);
            double ciMax = Math.max(ci1, ci2);
            double interval = ciMax - ciMin;
            double lb = Math.max(lbs[i], ciMin - interval * alpha);
            double ub = Math.min(ubs[i], ciMax + interval * alpha);
            double gene = lb + random.nextDouble() * (ub - lb);
            child.setGene(gene, i);
        }
        return child;
    }

}
