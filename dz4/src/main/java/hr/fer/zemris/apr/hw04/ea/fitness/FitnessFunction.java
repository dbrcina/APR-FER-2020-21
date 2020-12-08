package hr.fer.zemris.apr.hw04.ea.fitness;

/**
 * Models a fitness function.
 *
 * @param <S> solution type.
 *
 * @author dbrcina
 */
public abstract class FitnessFunction {

    private int evaluationCounter;

    public int numberOfEvaluations() {
        return evaluationCounter;
    }

    public void resetEvaluationsCounter() {
        evaluationCounter = 0;
    }

    /**
     * @return a number of variables.
     */
    public abstract int numberOfVariables();

    /**
     * Calculates fitness for the provided {@code solution}.
     *
     * @param solution solution.
     * @param decoder  decoder.
     *
     * @return calculated fitness.
     */
    public double calculateFitness(double[] solution) {
        evaluationCounter++;
        return calculateFitnessInternal(solution);
    }

    /**
     * Every sub class needs to provide an implementation.
     *
     * @see #calculateFitness(double[])
     */
    protected abstract double calculateFitnessInternal(double[] solution);

}
