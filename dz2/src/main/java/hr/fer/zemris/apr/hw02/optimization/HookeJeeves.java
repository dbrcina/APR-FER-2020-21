package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;
import hr.fer.zemris.apr.hw02.function.IFunction;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

/**
 * An implementation of <i>Hooke-Jeeves</i> optimization algorithm.
 *
 * @author dbrcina
 * @see IOptAlgorithm
 */
public class HookeJeeves extends AbstractOptAlgorithm {

    private static final double DEFAULT_DELTA = 0.5;

    /* Vector of deltas. Default values are set to DEFAULT_DELTA. */
    private IMatrix deltas;

    /**
     * @see AbstractOptAlgorithm#AbstractOptAlgorithm()
     */
    protected HookeJeeves() {
    }

    @Override
    public void configure(Properties properties) throws Exception {
        try {
            super.configure(properties);
            /* Configure the deltas vector. Find 'deltas' in the properties map. */
            deltas = null;
            Object deltasObj = properties.get("deltas");
            double[] deltasColumnData;
            if (deltasObj != null) {
                deltasColumnData = Arrays.stream(((String) deltasObj).split("\\s+"))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                if (deltasColumnData.length != getInitialPoint().getRowsCount()) {
                    throw new ConfigInvalidException(
                            "Dimension of initial point and deltas vectors need to be the same!");
                }
            } else {
                deltasColumnData = new double[getInitialPoint().getRowsCount()];
                Arrays.fill(deltasColumnData, DEFAULT_DELTA);
            }
            deltas = new Matrix(deltasColumnData.length, 1);
            deltas.setColumn(0, deltasColumnData);
        } catch (NumberFormatException | ConfigInvalidException e) {
            handleConfigureExceptions(e);
        }
    }

    @Override
    public IMatrix run(IFunction function) {
        super.run(function);
        IMatrix x0 = getInitialPoint();
        IMatrix xB = x0.copy();
        IMatrix xP = x0.copy();

        // Used for stop condition.
        IMatrix epsilons = getEpsilons();
        double epsilonsNorm = l2Norm(epsilons);

        while (l2Norm(deltas) > epsilonsNorm) {
            incrementIterations(1);
            IMatrix xN = search(function, xP);
            double FN = function.value(xN);
            double FB = function.value(xB);
            if (isVerbose()) {
                printResults(numberOfIterations(),
                        xB.columnData(0), xP.columnData(0), xN.columnData(0),
                        FB, function.value(xP), FN);
            }
            if (FN < FB) {                     // Base point is accepted.
                xP = xN.nScalarMul(2).sub(xB); // Define new search point. xP = 2 * xN - xB (lin reflection).
                xB = xN;
            } else {
                deltas.scalarMul(0.5); // Decrease deltas vector by half.
                xP = xB;               // Return on the last base point.
            }
        }
        return xB;
    }

    /**
     * Searches for the minimizer of the provided <code>function</code> in <code>+/-</code> deltas directions around the
     * point <code>xP</code>.
     *
     * @param function evaluation function.
     * @param xP       xP point.
     * @return point.
     */
    private IMatrix search(IFunction function, IMatrix xP) {
        IMatrix x = xP.copy();
        for (int i = 0; i < x.getRowsCount(); i++) {
            double FP = function.value(x);
            x.set(i, 0, x.get(i, 0) + deltas.get(i, 0));         // Increase by DX.
            double FN = function.value(x);
            if (FN > FP) {                                                               // Increase didn't work.
                x.set(i, 0, x.get(i, 0) - 2 * deltas.get(i, 0)); // Decrease by DX instead.
                FN = function.value(x);
                if (FN > FP) {                                                           // Decrease also didn't work.
                    x.set(i, 0, x.get(i, 0) + deltas.get(i, 0)); // Return the initial state.
                }
            }
        }
        return x;
    }

    /**
     * Prints results for i-th iteration.
     */
    private void printResults(int i, double[] xB, double[] xP, double[] xN, double B, double P, double N) {
        System.out.println("Iteration " + i + ":");
        System.out.printf(Locale.US, "xB = %s, f(a) = %f%n", Arrays.toString(xB), B);
        System.out.printf(Locale.US, "xP = %s, f(c) = %f%n", Arrays.toString(xP), P);
        System.out.printf(Locale.US, "xN = %s, f(d) = %f%n", Arrays.toString(xN), N);
        System.out.println("------------------------------");
    }

    public void setDeltas(IMatrix deltas) {
        this.deltas = deltas.copy();
    }

}
