package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;
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

    private double step = DEFAULT_STEP;
    private double alpha = DEFAULT_ALPHA;
    private double beta = DEFAULT_BETA;
    private double gamma = DEFAULT_GAMMA;
    private double sigma = DEFAULT_SIGMA;

    /**
     * @see AbstractOptAlgorithm#AbstractOptAlgorithm()
     */
    protected Simplex() {
    }

    @Override
    public void configure(Properties properties) throws Exception {
        try {
            super.configure(properties);
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
        } catch (NumberFormatException | ConfigInvalidException e) {
            handleConfigureExceptions(e);
        }
    }

    @Override
    public IMatrix run(IFunction function) {
        super.run(function);
        IMatrix[] simplexPoints = generateSimplexPoints();
        double[] simplexValues;
        int l;
        int h;
        IMatrix xC;
        // Used for stop condition.
        IMatrix epsilons = getEpsilons();
        double epsilonsNorm = l2Norm(epsilons);
        do {
            incrementIterations(1);
            simplexValues = Arrays.stream(simplexPoints)
                    .mapToDouble(function::value)
                    .toArray();
            int[] indexes = findLAndHIndexes(simplexValues);
            l = indexes[0];
            h = indexes[1];
            xC = calculateCentroid(simplexPoints, h);
            IMatrix xR = reflection(xC, simplexPoints[h]);
            double FXR = function.value(xR);
            if (FXR < simplexValues[l]) {
                IMatrix xE = expansion(xC, xR);
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
                    IMatrix xK = contraction(xC, simplexPoints[h]);
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
        } while (terminate(simplexValues, function.value(xC), epsilonsNorm));

        return simplexPoints[l];
    }

    /**
     * Generates <code>(n+1)</code> simplex points where first one is initial point.
     *
     * @return simplex points.
     */
    private IMatrix[] generateSimplexPoints() {
        IMatrix x0 = getInitialPoint();
        IMatrix[] simplexPoints = new IMatrix[x0.getRowsCount() + 1];
        simplexPoints[0] = x0;
        for (int i = 0; i < simplexPoints.length - 1; i++) {
            IMatrix temp = x0.copy();
            simplexPoints[i + 1] = temp.set(i, 0, temp.get(i, 0) + step);
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
    private IMatrix calculateCentroid(IMatrix[] simplexPoints, int h) {
        int n = simplexPoints.length;
        int pointDim = simplexPoints[0].getRowsCount();
        IMatrix xC = new Matrix(pointDim, 1);
        for (int i = 0; i < n; i++) {
            if (i == h) continue;
            IMatrix xi = simplexPoints[i];
            xC.add(xi);
        }
        return xC.scalarMul(1.0 / (n - 1));
    }

    /**
     * Performs reflection operation on provided vectors.
     *
     * @param xC centroid.
     * @param xH the worst solution.
     * @return reflection vector.
     */
    private IMatrix reflection(IMatrix xC, IMatrix xH) {
        return xC.nScalarMul(1 + alpha).sub(xH.nScalarMul(alpha));
    }

    /**
     * Performs expansion operation on provided vectors.
     *
     * @param xC centroid.
     * @param xR reflected vector.
     * @return expanded vector.
     */
    private IMatrix expansion(IMatrix xC, IMatrix xR) {
        return xC.nScalarMul(1 - gamma).add(xR.nScalarMul(gamma));
    }

    /**
     * Performs contraction operation on provided vectors.
     *
     * @param xC centroid.
     * @param xH the worst solution.
     * @return contracted vector.
     */
    private IMatrix contraction(IMatrix xC, IMatrix xH) {
        return xC.nScalarMul(1 - beta).add(xH.nScalarMul(beta));
    }

    /**
     * Performs shrink operation on provided points.
     *
     * @param simplexPoints simplex points.
     * @param l             index of the best point.
     */
    private void shrink(IMatrix[] simplexPoints, int l) {
        IMatrix xL = simplexPoints[l];
        for (IMatrix point : simplexPoints) {
            point.add(xL).scalarMul(sigma);
        }
    }

    private boolean terminate(double[] simplexValues, double FXC, double epsilonsNorm) {
        int n = simplexValues.length;
        double[] vector = new double[n];
        for (int i = 0; i < n; i++) {
            vector[i] += Math.pow(simplexValues[i] - FXC, 2);
        }
        double norm = Math.sqrt(Arrays.stream(vector)
                .sum() / n
        );
        return norm > epsilonsNorm;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

}
