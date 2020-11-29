package hr.fer.zemris.apr.hw03.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw03.function.IFunction;

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

    private static final double DEFAULT_ALPHA = 1;
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
        IMatrix x0 = constraints.testExplicitConstraints(getInitialPoint());
        if (!constraints.testImplicitConstraints(x0)) {
            System.out.println("Initial point doesn't satisfy implicit constraints! Exiting...");
            return x0;
        }
        IMatrix[] points = generatePoints(x0, constraints);
        double[] pointsValues = Arrays.stream(points).mapToDouble(function::value).toArray();
        int l, h, h2;
        IMatrix xC;
        double epsilon = getEpsilons().get(0, 0);
        do {

        } while ()
        return points[l];
    }

    private IMatrix[] generatePoints(IMatrix xC, ArgsConstraints constraints) {
        IMatrix[] points = new IMatrix[2 * xC.getRowsCount()];
        points[0] = xC.copy();
        for (int i = 1; i < points.length * 2; i++) {
            IMatrix point = constraints.generatePointUsingExplicitConstraints(RANDOM);
            while (!constraints.testImplicitConstraints(point)) {
                point.add(xC).scalarMul(0.5);
            }
            points[i] = point;
            xC = points[0].copy();
            for (int j = 1; j < points.length; j++) {
                xC.add(points[j]);
            }
            xC.scalarMul(1.0 / points.length);
        }
        return points;
    }

}
