package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.IFunction;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

/**
 * An implementation of <i>Hooke-Jeeves</i> optimization algorithm.
 *
 * @author dbrcina
 */
public class HookeJeeves extends AbstractOptAlgorithm {

    private static final double DEFAULT_DELTA = 0.5;

    /* Vector of deltas. Default values are set to DEFAULT_DELTA. */
    private double[] deltas;

    @Override
    public void configure(String configFile) throws Exception {
        try {
            super.configure(configFile);
            deltas = null;
            // Config file is already loaded in super class.
            Properties properties = PropertiesProvider.getProperties();
            Object deltasObj = properties.get("deltas");
            if (deltasObj != null) {
                deltas = Arrays.stream(((String) deltasObj).split("\\s+"))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                if (deltas.length != getInitialPoint().length) {
                    throw new ConfigFileException("Dimension of initial point and deltas vectors need to be the same!");
                }
            }
            if (deltas == null) {
                deltas = new double[getInitialPoint().length];
                Arrays.fill(deltas, DEFAULT_DELTA);
            }
        } catch (NumberFormatException | ConfigFileException e) {
            handleConfigureExceptions(configFile, e);
        }
    }

    @Override
    public double[] run(IFunction function) {
        super.run(function);
        double[] x0 = getInitialPoint();
        double[] xB = Arrays.copyOf(x0, x0.length);
        double[] xP = Arrays.copyOf(x0, x0.length);
        while (!deltasLEQEpsilons()) {
            incrementIterations();
            double[] xN = search(function, xP);
            double N = function.value(xN);
            double B = function.value(xB);
            if (isVerbose()) {
                printResults(numberOfIterations(), xB, xP, xN, B, function.value(xP), N);
            }
            if (N < B) {                                // Base point is accepted.
                for (int i = 0; i < xP.length; i++) {   // Define new search point.
                    xP[i] = 2 * xN[i] - xB[i];          // 2 * xN - xB (lin reflection).
                }
                xB = xN;
            } else {
                for (int i = 0; i < deltas.length; i++) {
                    deltas[i] *= 0.5;                   // Decrease deltas vector by half.
                }
                xP = xB;                                // Return on the last base point.
            }
        }
        return xB;
    }

    /**
     * @return <code>true</code> if deltas vector is lower than or equal to epsilons vector.
     */
    private boolean deltasLEQEpsilons() {
        double[] epsilons = getEpsilons();
        // L2 norm.
        return Arrays.stream(deltas).map(d -> d * d).sum() <= Arrays.stream(epsilons).map(e -> e * e).sum();
    }

    /**
     * Searches for a point that minimizes the provided <code>function</code> the most around the provided
     * point <code>xP</code> in <code>+/-</code> deltas directions.
     *
     * @param function evaluation function.
     * @param xP       xP point.
     * @return point.
     */
    private double[] search(IFunction function, double[] xP) {
        double[] x = Arrays.copyOf(xP, xP.length);
        for (int i = 0; i < x.length; i++) {
            double P = function.value(x);
            x[i] += deltas[i];              // Increase by DX.
            double N = function.value(x);
            if (N > P) {                    // Increase didn't work.
                x[i] -= 2 * deltas[i];      // Decrease by DX instead.
                N = function.value(x);
                if (N > P) {                // Decrease also didn't work.
                    x[i] += deltas[i];      // Return the initial state.
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

}
