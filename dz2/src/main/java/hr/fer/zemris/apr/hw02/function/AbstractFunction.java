package hr.fer.zemris.apr.hw02.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * An abstract implementation of the {@link IFunction} interface.
 *
 * @author dbrcina
 */
public abstract class AbstractFunction implements IFunction {

    private int evaluationCounter;

    @Override
    public int evaluatedTimes() {
        return evaluationCounter;
    }

    @Override
    public void resetEvaluationCounter() {
        evaluationCounter = 0;
    }

    /**
     * Utility method used for testing the <code>point</code>'s dimension. It gets called every time when
     * {@link #value(IMatrix)} method is invoked and if the test passes, the <code>evaluationCounter</code> variable
     * gets incremented.
     *
     * @param point a point.
     * @param n     a dimension.
     * @throws IllegalArgumentException if the <code>point</code> is not a <code>nx1</code> dimensional matrix.
     */
    protected void testValuePointDimension(IMatrix point, int n) {
        if (point.getRowsCount() != n || point.getColumnsCount() != 1) {
            String methodName = getClass().getSimpleName() + "::value(IMatrix)";
            throw new IllegalArgumentException(methodName + ": A point with " + n + "x1 dimensions is expected!");
        }
        evaluationCounter++;
    }

}
