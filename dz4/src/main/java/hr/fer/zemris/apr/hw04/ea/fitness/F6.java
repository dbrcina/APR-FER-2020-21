package hr.fer.zemris.apr.hw04.ea.fitness;

import java.util.Arrays;

/**
 * @author dbrcina
 */
public class F6 extends FitnessFunction {

    private final int n;

    public F6(int n) {
        this.n = n;
    }

    @Override
    public int numberOfVariables() {
        return n;
    }

    @Override
    protected double calculateFitnessInternal(double[] solution) {
        double squaredSum = Arrays.stream(solution)
                .map(x -> x * x)
                .sum();
        double nominator = Math.pow(Math.sin(Math.sqrt(squaredSum)), 2) - 0.5;
        double denominator = Math.pow(1 + 0.001 * squaredSum, 2);
        double value = 0.5 + nominator / denominator;
        return -value;
    }

}
