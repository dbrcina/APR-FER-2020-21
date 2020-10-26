package hr.fer.zemris.apr.hw02.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * Simulates a function with one or more variables.
 *
 * @author dbrcina
 */
public interface IFunction {

    /**
     * Calculates function value at the provided <code>point</code>, where the <code>point</code> is a column vector.
     *
     * @param point a point.
     * @return a calculated value.
     * @throws IllegalArgumentException if the point's dimension is invalid.
     */
    double value(IMatrix point);

    /**
     * @return how many times has <b>this</b> function been evaluated.
     * @see #resetEvaluationCounter()
     */
    int evaluatedTimes();

    /**
     * Resets the evaluation counter for <b>this</b> function.
     *
     * @see #evaluatedTimes()
     */
    void resetEvaluationCounter();

}
