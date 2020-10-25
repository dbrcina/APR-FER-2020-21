package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.IFunction;

import java.util.Arrays;
import java.util.Properties;

/**
 * An implementation of <i>Simplex</i> optimization algorithm.
 *
 * @author dbrcina
 * @see IOptAlgorithm
 */
public class Simplex extends AbstractOptAlgorithm {

    private static final double DEFAULT_STEP = 1;
    private static final double DEFAULT_ALPHA = 1;
    private static final double DEFAULT_BETA = 0.5;
    private static final double DEFAULT_GAMMA = 2;
    private static final double DEFAULT_SIGMA = 0.5;

    private double step;
    private double alpha;
    private double beta;
    private double gamma;
    private double sigma;

    @Override
    public void configure(String configFile) throws Exception {
        try {
            super.configure(configFile);
            // Config file is already loaded in super class.
            Properties properties = PropertiesProvider.getProperties();
            Object stepObj = properties.get("step");
            step = stepObj == null ? DEFAULT_STEP : Double.parseDouble((String) stepObj);
            Object alphaObj = properties.get("alpha");
            alpha = alphaObj == null ? DEFAULT_ALPHA : Double.parseDouble((String) alphaObj);
            Object betaObj = properties.get("beta");
            beta = betaObj == null ? DEFAULT_BETA : Double.parseDouble((String) betaObj);
            Object gammaObj = properties.get("gamma");
            gamma = gammaObj == null ? DEFAULT_GAMMA : Double.parseDouble((String) gammaObj);
            Object sigmaObj = properties.get("sigma");
            sigma = sigmaObj == null ? DEFAULT_SIGMA : Double.parseDouble((String) sigmaObj);
        } catch (NumberFormatException | ConfigFileException e) {
            handleConfigureExceptions(configFile, e);
        }
    }

    @Override
    public double[] run(IFunction function) {
        super.run(function);
        double[][] simplexPoints = generateSimplexPoints();
        double[] simplexValues;
        int l;
        int h;
        double[] xC;
        do {
            incrementIterations(1);
            simplexValues = Arrays.stream(simplexPoints)
                    .mapToDouble(function::value)
                    .toArray();
            int[] indexes = findLAndHIndexes(simplexValues);
            l = indexes[0];
            h = indexes[1];
            xC = calculateCentroid(simplexPoints, h);
            double[] xR = reflection(xC, simplexPoints[h]);
            double FXR = function.value(xR);
            if (FXR < simplexValues[l]) {
                double[] xE = expansion(xC, xR);
                double FXE = function.value(xE);
                if (FXE < simplexValues[l]) {
                    simplexPoints[h] = xE;
                    simplexValues[h] = FXE;
                } else {
                    simplexPoints[h] = xR;
                    simplexValues[h] = FXR;
                }
            } else {
                boolean FXRHighest = true;
                for (int i = 0; i < simplexValues.length; i++) {
                    if (i == h) continue;
                    if (FXR < simplexValues[i]) {
                        FXRHighest = false;
                        break;
                    }
                }
                if (FXRHighest) {
                    if (FXR < simplexValues[h]) {
                        simplexPoints[h] = xR;
                        simplexValues[h] = FXR;
                    }
                    double[] xK = contraction(xC, simplexPoints[h]);
                    double FXK = function.value(xK);
                    if (FXK < simplexValues[h]) {
                        simplexPoints[h] = xK;
                        simplexValues[h] = FXK;
                    } else {
                        shrink(simplexPoints, l);
                    }
                } else {
                    simplexPoints[h] = xR;
                    simplexValues[h] = FXR;
                }
            }
        } while (terminate(simplexValues, function.value(xC)));

        return simplexPoints[l];
    }

    /**
     * Generates <code>(n+1)</code> simplex points where first one is initial point.
     *
     * @return simplex points.
     */
    private double[][] generateSimplexPoints() {
        double[] x0 = getInitialPoint();
        double[][] simplexPoints = new double[x0.length + 1][];
        simplexPoints[0] = Arrays.copyOf(x0, x0.length);
        for (int i = 0; i < x0.length; i++) {
            double[] temp = Arrays.copyOf(x0, x0.length);
            temp[i] += step;
            simplexPoints[i + 1] = temp;
        }
        return simplexPoints;
    }

    /**
     * Finds indexes for lowest/highest simplex (function) values.
     *
     * @param simplexValues simplex values.
     * @return indexes l(lowest) and h(highest).
     */
    private int[] findLAndHIndexes(double[] simplexValues) {
        double lValue = simplexValues[0];
        int l = 0;
        double hValue = simplexValues[0];
        int h = 0;
        for (int i = 1; i < simplexValues.length; i++) {
            if (simplexValues[i] < lValue) {
                lValue = simplexValues[i];
                l = i;
            } else if (simplexValues[i] > hValue) {
                hValue = simplexValues[i];
                h = i;
            }
        }
        return new int[]{l, h};
    }

    /**
     * Calculates centroid of provided <code>simplexPoints</code> where point at index <code>h</code> is ignored.
     *
     * @param simplexPoints simplex points.
     * @param h             index for highest function value.
     * @return centroid.
     */
    private double[] calculateCentroid(double[][] simplexPoints, int h) {
        int n = simplexPoints.length;
        int pointDim = simplexPoints[0].length;
        double[] centroid = new double[pointDim];
        for (int i = 0; i < n; i++) {
            if (i == h) continue;
            double[] xi = simplexPoints[i];
            for (int j = 0; j < centroid.length; j++) {
                centroid[j] += xi[j];
            }
        }
        return Arrays.stream(centroid).map(c -> c / (n - 1)).toArray();
    }

    /**
     * Performs reflection operation on provided vectors.
     *
     * @param xC centroid.
     * @param xH the worst solution.
     * @return reflection vector.
     */
    private double[] reflection(double[] xC, double[] xH) {
        double[] xR = new double[xC.length];
        for (int i = 0; i < xR.length; i++) {
            xR[i] += (1 + alpha) * xC[i] - alpha * xH[i];
        }
        return xR;
    }

    /**
     * Performs expansion operation on provided vectors.
     *
     * @param xC centroid.
     * @param xR reflected vector.
     * @return expanded vector.
     */
    private double[] expansion(double[] xC, double[] xR) {
        double[] xE = new double[xC.length];
        for (int i = 0; i < xE.length; i++) {
            xE[i] += (1 - gamma) * xC[i] - gamma * xR[i];
        }
        return xE;
    }

    /**
     * Performs contraction operation on provided vectors.
     *
     * @param xC centroid.
     * @param xH the worst solution.
     * @return contracted vector.
     */
    private double[] contraction(double[] xC, double[] xH) {
        double[] xK = new double[xC.length];
        for (int i = 0; i < xK.length; i++) {
            xK[i] += (1 - beta) * xC[i] + beta * xH[i];
        }
        return xK;
    }

    /**
     * Performs shrink operation on provided points.
     *
     * @param simplexPoints simplex points.
     * @param l             index of the best point.
     */
    private void shrink(double[][] simplexPoints, int l) {
        double[] xL = simplexPoints[l];
        for (double[] points : simplexPoints) {
            for (int i = 0; i < points.length; i++) {
                points[i] = sigma * (points[i] + xL[i]);
            }
        }
    }

    private boolean terminate(double[] simplexValues, double FXC) {
        int n = simplexValues.length;
        double[] vector = new double[n];
        for (int i = 0; i < n; i++) {
            vector[i] += Math.pow(simplexValues[i] - FXC, 2);
        }
        double norm = Math.sqrt(Arrays.stream(vector)
                .sum() / n
        );
        return norm > getEpsilons()[0];
    }

}
