package hr.fer.zemris.apr.hw04.ea.fitness;

/**
 * @author dbrcina
 */
public class F3 extends FitnessFunction {

    private final int n;

    public F3(int n) {
        this.n = n;
    }

    @Override
    public int numberOfVariables() {
        return n;
    }

    @Override
    protected double calculateFitnessInternal(double[] solution) {
        double value = 0.0;
        for (int i = 0; i < solution.length; i++) {
            value += Math.pow(solution[i] - (i + 1), 2);
        }
        return -value;
    }

}
