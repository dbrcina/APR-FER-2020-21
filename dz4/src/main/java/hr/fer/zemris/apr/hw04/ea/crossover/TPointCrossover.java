package hr.fer.zemris.apr.hw04.ea.crossover;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * An implementation of {@link Crossover} interface which provides <i>t-point crossover</i>.
 *
 * @author dbrcina
 */
public class TPointCrossover implements Crossover<Solution<Boolean>> {

    private final Random random;
    private final int t;

    public TPointCrossover(Random random, int t) {
        this.random = random;
        this.t = t;
    }

    @Override
    public Solution<Boolean> crossover(Solution<Boolean> parent1, Solution<Boolean> parent2) {
        Boolean[] child = new Boolean[parent1.getNumberOfGenes()];
        Set<Integer> points = new TreeSet<>();
        while (points.size() != t) {
            int point = random.nextInt(child.length);
            if (points.contains(point)) continue;
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
            System.arraycopy(genes, 0, child, fromIndex, toIndex - fromIndex);
            firstParent = !firstParent;
            fromIndex += toIndex;
        }
        if (firstParent) {
            genes = parent1.getSubGenes(fromIndex, child.length);
        } else {
            genes = parent2.getSubGenes(fromIndex, child.length);
        }
        // Apply after the last point.
        System.arraycopy(genes, 0, child, fromIndex, child.length - fromIndex);
        return new Solution<>(child);
    }

}
