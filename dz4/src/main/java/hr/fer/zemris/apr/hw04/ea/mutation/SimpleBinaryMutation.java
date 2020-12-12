package hr.fer.zemris.apr.hw04.ea.mutation;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * An implementation of {@link Mutation} interface which implements <i>Simple binary mutation</i>.
 *
 * @author dbrcina
 */
public class SimpleBinaryMutation implements Mutation<Solution<Boolean>> {

    private final Random random;
    private final double pm;

    public SimpleBinaryMutation(Random random, double pm) {
        this.random = random;
        this.pm = pm;
    }

    @Override
    public Solution<Boolean> mutate(Solution<Boolean> solution) {
        double p = 1 - Math.pow(1 - pm, solution.getNumberOfGenes());
        if (random.nextDouble() >= p) return solution;
        for (int i = 0; i < solution.getNumberOfGenes(); i++) {
            if (random.nextDouble() < pm) {
                solution.setGene(!solution.getGene(i), i);
            }
        }
        return solution;
    }

}
