package hr.fer.zemris.apr.hw04.ea.mutation;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * An implementation of {@link Mutation} interface which implements <i>Random binary mutation</i>.
 *
 * @author dbrcina
 */
public class RandomBinaryMutation implements Mutation<Solution<Boolean>> {

    private final Random random;
    private final double p;

    public RandomBinaryMutation(Random random, double p) {
        this.random = random;
        this.p = p;
    }

    @Override
    public Solution<Boolean> mutate(Solution<Boolean> solution) {
        for (int i = 0; i < solution.getNumberOfGenes(); i++) {
            if (random.nextDouble() <= p) {
                solution.setGene(!solution.getGene(i), i);
            }
        }
        return solution;
    }

}
