package hr.fer.zemris.apr.hw03.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw03.function.IFunction;
import hr.fer.zemris.apr.hw03.optimization.constraint.ArgsConstraints;

import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

/**
 * An implementation of the <i>Box</i> optimization algorithm.
 *
 * @author dbrcina
 * @see IOptAlgorithm
 */
public class Box extends AbstractOptAlgorithm {

    private static final double DEFAULT_ALPHA = 1.3;
    private static final Random RANDOM = new Random();

    private double alpha = DEFAULT_ALPHA;

    /**
     * @see AbstractOptAlgorithm#AbstractOptAlgorithm(int)
     */
    protected Box() {
        super(0);
    }

    @Override
    public void configure(Properties properties) throws Exception {
        try {
            super.configure(properties);
            Object alphaObj = properties.get("alpha");
            if (alphaObj != null) {
                alpha = Double.parseDouble((String) alphaObj);
            }
        } catch (NumberFormatException | ConfigInvalidException e) {
            handleConfigureExceptions(e);
        }
    }

    @Override
    public IMatrix run(IFunction function) {
        super.run(function);
        ArgsConstraints constraints = getConstraints();
        IMatrix x0 = constraints.fitExplicitConstraints(getInitialPoint());
        if (!constraints.testImplicitConstraints(x0)) {
            System.out.println("Initial point doesn't satisfy implicit constraints! Exiting...");
            return x0;
        }
        IMatrix[] points = generatePoints(x0, constraints);
        double[] pointsValues = Arrays.stream(points).mapToDouble(function::value).toArray();
        int l, h, h2;
        double bestValue = Double.MAX_VALUE;
        IMatrix xC;
        double epsilon = getEpsilons().get(0, 0);
        do {
            int[] indexes = findIndexes(pointsValues);
            l = indexes[0];
            h = indexes[1];
            h2 = indexes[2];
            if (isDiverging(pointsValues[l], bestValue)) break;
            bestValue = pointsValues[l];
            xC = calculateCentroid(points, l, h);
            IMatrix xR = reflection(xC, points[h]);
            IMatrix finalXC = xC;
            constraints.fitExplicitConstraints(xR);
            constraints.fitImplicitConstraints(xR, p -> moveTowardsXc(p, finalXC));
            double fXr = function.value(xR);
            double fXh2 = pointsValues[h2];
            if (fXr > fXh2) {
                moveTowardsXc(xR, xC);
                fXr = function.value(xR);
            }
            points[h] = xR;
            pointsValues[h] = fXr;
        } while (!exitCondition(pointsValues, function.value(xC), epsilon));
        return points[l];
    }

    /* Generates 2n points array. */
    private IMatrix[] generatePoints(IMatrix xC, ArgsConstraints constraints) {
        IMatrix[] points = new IMatrix[2 * xC.getRowsCount()];
        points[0] = xC.copy();
        int nPointsAdded = 1;
        for (int i = 1; i < points.length; i++) {
            IMatrix point = constraints.generatePointUsingExplicitConstraints(RANDOM);
            IMatrix finalXC = xC;
            points[i] = constraints.fitImplicitConstraints(point, p -> moveTowardsXc(p, finalXC));
            nPointsAdded++;
            xC = points[0].copy();
            for (int j = 1; j < nPointsAdded; j++) {
                xC.add(points[j]);
            }
            xC.scalarMul(1.0 / points.length);
        }
        return points;
    }

    private void moveTowardsXc(IMatrix point, IMatrix xC) {
        point.add(xC).scalarMul(0.5);
    }

    /* Returns l, h and h2 indexes. */
    private int[] findIndexes(double[] pointsValues) {
        double lValue = pointsValues[0];
        double hValue = pointsValues[0];
        double h2Value = hValue;
        int l = 0, h = 0, h2 = 0;
        for (int i = 1; i < pointsValues.length; i++) {
            double ithValue = pointsValues[i];
            if (ithValue < lValue) {
                l = i;
                lValue = ithValue;
            } else if (ithValue > hValue) {
                h2 = h;
                h2Value = hValue;
                h = i;
                hValue = ithValue;
            } else if (ithValue > h2Value) {
                h2 = i;
                h2Value = ithValue;
            }
        }
        return new int[]{l, h, h2};
    }

    /* Calculates centroid without point at index h. */
    private IMatrix calculateCentroid(IMatrix[] points, int l, int h) {
        IMatrix xC = points[l].copy();
        for (int i = 0; i < points.length; i++) {
            if (i == l || i == h) continue;
            xC.add(points[i]);
        }
        return xC.scalarMul(1.0 / (points.length - 1));
    }

    /* xR = (1+alpha)*xC - alpha*xH. */
    private IMatrix reflection(IMatrix xC, IMatrix xH) {
        return xC.nScalarMul(alpha + 1).sub(xH.nScalarMul(alpha));
    }

    private boolean exitCondition(double[] pointsValues, double fXc, double epsilon) {
        double stdDeviation = Math.sqrt(Arrays.stream(pointsValues)
                .map(fXi -> Math.pow(fXi - fXc, 2))
                .sum() / pointsValues.length);
        return stdDeviation < epsilon;
    }

}
