package hr.fer.zemris.apr.hw02.function;

/**
 * Simulates a function with one or more variables.
 *
 * @author dbrcina
 */
public interface IFunction {

    /**
     * Calculates value at provided <code>point</code>.
     *
     * @param point point.
     * @return calculated value.
     * @throws IllegalArgumentException if the point's dimension is invalid.
     */
    double value(double[] point);

    /**
     * @return the number of calls for <b>this</b> IFunction object.
     */
    int numOfCalls();

    /**
     * Resets the number of calls counter for <b>this</b> IFunction object.
     */
    void resetNumOfCalls();

}
