package hr.fer.zemris.apr.hw03.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * An abstract implementation of the {@link IFunction} interface.
 *
 * @author dbrcina
 */
public abstract class AbstractFunction implements IFunction {

    private int evaluationCounter;
    private int gradientEvaluationCounter;
    private int hesseEvaluationCounter;

    @Override
    public int evaluatedTimes() {
        return evaluationCounter;
    }

    @Override
    public void resetEvaluationCounter() {
        evaluationCounter = 0;
    }

    @Override
    public int gradientEvaluatedTimes() {
        return gradientEvaluationCounter;
    }

    @Override
    public void resetGradientEvaluationCounter() {
        gradientEvaluationCounter = 0;
    }

    @Override
    public int hesseEvaluatedTimes() {
        return hesseEvaluationCounter;
    }

    @Override
    public void resetHesseEvaluationCounter() {
        hesseEvaluationCounter = 0;
    }

    /**
     * Utility method used for testing the <code>point</code>'s dimension. It gets called every time when
     * certain method is invoked and if the test passes, certain evaluation counter gets incremented.
     *
     * @param point a point.
     * @param n     a dimension.
     *
     * @throws IllegalArgumentException if the <code>point</code> is not a <code>nx1</code> dimensional matrix.
     */
    protected void testPointDimension(IMatrix point, int n, String method) {
        if (point.getRowsCount() != n || point.getColumnsCount() != 1) {
            String methodName = getClass().getSimpleName() + ":: " + method + "(IMatrix)";
            throw new IllegalArgumentException(methodName + ": A point with " + n + "x1 dimensions is expected!");
        }
        switch (method) {
            case "value" -> evaluationCounter++;
            case "gradient" -> gradientEvaluationCounter++;
            case "hesse" -> hesseEvaluationCounter++;
        }
    }

}
