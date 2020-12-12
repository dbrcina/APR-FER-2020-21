package hr.fer.zemris.apr.hw04.ea.crossover;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * An implementation of {@link Crossover} interface which provides <i>Multi-point crossover</i>.
 *
 * @author dbrcina
 */
public class MultiPointCrossover implements Crossover<Solution<Boolean>> {

    private final Random random;
    private final int t;

    public MultiPointCrossover(Random random, int t) {
        this.random = random;
        this.t = t;
    }

    @Override
    public Solution<Boolean> crossover(Solution<Boolean> parent1, Solution<Boolean> parent2) {
        Solution<Boolean> child = parent1.copy();
        Set<Integer> points = new TreeSet<>();
        while (points.size() != t) {
            int point = random.nextInt(child.getNumberOfGenes());
            if (point == 0 || point == child.getNumberOfGenes() - 1) continue;
            points.add(point);
        }
        int fromIndex = 0;
        boolean firstParent = true;
        Boolean[] genes;
        for (int toIndex : points) {
            if (firstParent) {
                genes = parent1.getSubGenes(fromIndex, toIndex);
            } else {
                genes = parent2.getSubGenes(fromIndex, toIndex);
            }
            child.setSubGenes(genes, fromIndex, toIndex);
            firstParent = !firstParent;
            fromIndex = toIndex;
        }
        if (firstParent) {
            genes = parent1.getSubGenes(fromIndex, child.getNumberOfGenes());
        } else {
            genes = parent2.getSubGenes(fromIndex, child.getNumberOfGenes());
        }
        // Apply after the last point.
        child.setSubGenes(genes, fromIndex, child.getNumberOfGenes());
        return child;
    }

}
