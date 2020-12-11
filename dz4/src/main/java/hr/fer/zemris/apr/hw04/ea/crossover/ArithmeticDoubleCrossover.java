package hr.fer.zemris.apr.hw04.ea.crossover;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

/**
 * An implementation of {@link Crossover} interface which provides <i>Arithmetic double crossover</i>.
 *
 * @author dbrcina
 */
public class ArithmeticDoubleCrossover implements Crossover<Solution<Double>> {

    private final double alpha;

    public ArithmeticDoubleCrossover(double alpha) {
        this.alpha = alpha;
    }


    @Override
    public Solution<Double> crossover(Solution<Double> parent1, Solution<Double> parent2) {
        Solution<Double> child = parent1.copy();
        for (int i = 0; i < child.getNumberOfGenes(); i++) {
            double g1 = parent1.getGene(i);
            double g2 = parent2.getGene(i);
            double gene = alpha * g1 + (1 - alpha) * g2;
            child.setGene(gene, i);
        }
        return child;
    }

}
