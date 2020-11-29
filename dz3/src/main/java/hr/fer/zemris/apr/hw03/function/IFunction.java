package hr.fer.zemris.apr.hw03.function;

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
     *
     * @return a calculated value.
     *
     * @throws IllegalArgumentException if the point's dimension is invalid.
     */
    double value(IMatrix point);

    /**
     * Calculates function's gradient at the provided <code>point</code>, where the <code>point</code> is a
     * column vector.
     *
     * @param point a point.
     *
     * @return a calculated gradient.
     *
     * @throws IllegalArgumentException if the point's dimension is invalid.
     */
    IMatrix gradient(IMatrix point);

    /**
     * Calculates function's hesse matrix at the provided <code>point</code>, where the <code>point</code> is a column
     * vector.
     *
     * @param point a point.
     *
     * @return a calculated hesse matrix.
     *
     * @throws IllegalArgumentException if the point's dimension is invalid.
     */
    IMatrix hesse(IMatrix point);

    /**
     * @return how many times has <b>this</b> function been evaluated.
     *
     * @see #resetEvaluationCounter()
     */
    int evaluatedTimes();

    /**
     * Resets the evaluation counter for <b>this</b> function.
     *
     * @see #evaluatedTimes()
     */
    void resetEvaluationCounter();

    /**
     * @return how many times has <b>this</b> function's gradient been evaluated.
     *
     * @see #resetGradientEvaluationCounter()
     */
    int gradientEvaluatedTimes();

    /**
     * Resets the gradient evaluation counter for <b>this</b> function.
     *
     * @see #gradientEvaluatedTimes()
     */
    void resetGradientEvaluationCounter();

    /**
     * @return how many times has <b>this</b> function's hesse matrix been evaluated.
     *
     * @see #resetHesseEvaluationCounter()
     */
    int hesseEvaluatedTimes();

    /**
     * Resets the hesse matrix evaluation counter for <b>this</b> function.
     *
     * @see #hesseEvaluatedTimes()
     */
    void resetHesseEvaluationCounter();

}
