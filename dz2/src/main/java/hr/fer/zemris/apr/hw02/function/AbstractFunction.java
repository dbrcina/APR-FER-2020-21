package hr.fer.zemris.apr.hw02.function;

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
     * {@link #value(double[])} is invoked and if test passes, the number of calls variable gets updated.
     *
     * @param point point.
     * @param n     dimension.
     * @throws IllegalArgumentException if the point is not a <code>n</code> dimensional array.
     */
    protected void testValuePointDimension(double[] point, int n) {
        if (point.length != n) {
            String methodName = getClass().getSimpleName() + "::value(IMatrix)";
            throw new IllegalArgumentException(String.format(
                    "%s %d dimensional array is expected!",
                    methodName, n)
            );
        }
        numOfCalls++;
    }

}
