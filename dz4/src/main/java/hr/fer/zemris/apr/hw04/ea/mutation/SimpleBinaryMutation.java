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

    public SimpleBinaryMutation(Random random) {
        this.random = random;
    }

    @Override
    public Solution<Boolean> mutate(Solution<Boolean> solution) {
        int index = random.nextInt(solution.getNumberOfGenes());
        solution.setGene(!solution.getGene(index), index);
        return solution;
    }

}
