package hr.fer.zemris.apr.hw04.ea.initializer;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Arrays;
import java.util.Random;

/**
 * An implementation of {@link PopulationInitializer} interface that generates solutions whose gene type is modeled
 * through {@link Boolean}.
 *
 * @author dbrcina
 */
public class RandomBinaryPopulationInitializer implements PopulationInitializer<Solution<Boolean>> {

    private final Random random = new Random();
    private final int[] bitsPerVariables;

    public RandomBinaryPopulationInitializer(int[] bitsPerVariables) {
        this.bitsPerVariables = bitsPerVariables;
    }

    @Override
    public Solution<Boolean> generateSolution() {
        int totalBits = Arrays.stream(bitsPerVariables).sum();
        Boolean[] bits = new Boolean[totalBits];
        for (int i = 0; i < totalBits; i++) {
            bits[i] = random.nextBoolean();
        }
        return new Solution<>(bits);
    }

}
