package hr.fer.zemris.apr.hw02.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * An abstract implementation of {@link IFunction} interface.
 *
 * @author dbrcina
 */
public abstract class AbstractFunction implements IFunction {

    private int numOfCalls;

    @Override
    public int numOfCalls() {
        return numOfCalls;
    }

    @Override
    public void resetNumOfCalls() {
        numOfCalls = 0;
    }

    /**
     * Utility method used for testing the <code>point</code>'s dimension. It gets called every time when
     * {@link #value(IMatrix)} is invoked and if test passes, the number of calls variable gets updated.
     *
     * @param point IMatrix point.
     * @param n     number of rows expected.
     * @throws IllegalArgumentException if the point is not <code>nx1</code> matrix.
     */
    protected void testValuePointDimension(IMatrix point, int n) {
        if (point.getRowsCount() != n || point.getColumnsCount() != 1) {
            String methodName = getClass().getSimpleName() + "::value(IMatrix)";
            throw new IllegalArgumentException(String.format(
                    "%s point with dimensions %dx1 is expected!",
                    methodName, n)
            );
        }
        numOfCalls++;
    }

}
