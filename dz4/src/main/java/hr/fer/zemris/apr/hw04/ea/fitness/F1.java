package hr.fer.zemris.apr.hw04.ea.fitness;

/**
 * @author dbrcina
 */
public class F1 extends FitnessFunction {

    @Override
    public int numberOfVariables() {
        return 2;
    }

    @Override
    protected double calculateFitnessInternal(double[] solution) {
        double x1 = solution[0];
        double x2 = solution[1];
        double value = 100 * Math.pow(x2 - x1 * x1, 2) + Math.pow(1 - x1, 2);
        return -value;
    }

}
