package hr.fer.zemris.apr.hw02.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * Simulates a function with one or more variables.
 *
 * @author dbrcina
 */
public interface IFunction {

    /**
     * Calculates value at provided <code>point</code>.
     *
     * @param point IMatrix point.
     * @return calculated value.
     * @throws IllegalArgumentException if the point's dimension is invalid.
     */
    double value(IMatrix point);

    /**
     * @return the number of calls for <b>this</b> IFunction.
     */
    int numOfCalls();

    /**
     * Resets the number of calls counter.
     */
    void resetNumOfCalls();

}
