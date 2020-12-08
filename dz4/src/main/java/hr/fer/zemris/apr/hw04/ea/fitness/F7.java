package hr.fer.zemris.apr.hw04.ea.fitness;

import java.util.Arrays;

/**
 * @author dbrcina
 */
public class F7 extends FitnessFunction {

    private final int n;

    public F7(int n) {
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
        double value = Math.pow(squaredSum, 0.25)
                * (1 + Math.pow(Math.sin(50 * Math.pow(squaredSum, 0.1)), 2));
        return -value;
    }

}
