package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;
import hr.fer.zemris.apr.hw02.function.AbstractFunction;
import hr.fer.zemris.apr.hw02.function.IFunction;

import java.util.Properties;

/**
 * An implementation of the <i>Coordinate search</i> optimization algorithm.
 *
 * @author dbrcina
 * @see IOptAlgorithm
 */
public class CoordinateSearch extends AbstractOptAlgorithm {

    /**
     * @see AbstractOptAlgorithm#AbstractOptAlgorithm()
     */
    protected CoordinateSearch() {
    }

    @Override
    public void configure(Properties properties) throws Exception {
        try {
            super.configure(properties);
        } catch (NumberFormatException | ConfigInvalidException e) {
            handleConfigureExceptions(e);
        }
    }

    @Override
    public IMatrix run(IFunction function) {
        super.run(function);

        IMatrix initialPoint = getInitialPoint();
        IMatrix epsilons = getEpsilons();

        /* Retrieve GoldenRatio optimization algorithm. */
        IOptAlgorithm gr = null;
        try {
            gr = IOptAlgorithmProvider.getInstance("GoldenRatio");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exiting...");
            System.exit(-1);
        }

        // Current iteration point.
        IMatrix x = initialPoint;
        // Point from previous iteration.
        IMatrix xS;
        // x - xS
        IMatrix subXXS;

        // Used for stop condition.
        double epsilonsNorm = l2Norm(epsilons);

        do {
            incrementIterations(1);
            xS = x.copy();
            for (int i = 0; i < x.getRowsCount(); i++) {
                final int ithId = i; // Index of the identity vector. Direction of a coordinate axis.
                IFunction surrogateFun = new AbstractFunction() {
                    @Override // Here point is a 1x1 matrix, which represents lambda.
                    public double value(IMatrix point) {
                        testValuePointDimension(point, 1);
                        IMatrix xTemp = x.copy();
                        // x_vec = x_vec + lambda*ei, where ei is identity vector with index i.
                        // Only value at index i of x_vec is updated because algorithm moves in direction i!
                        xTemp.set(ithId, 0, xTemp.get(ithId, 0) + point.get(0, 0));
                        return function.value(xTemp);
                    }
                };
                // Matrix object used to pass initial point and epsilon for ith point component.
                IMatrix matrixConfig = new Matrix(1, 1);
                gr.setInitialPoint(matrixConfig.set(0, 0, initialPoint.get(i, 0)));
                gr.setEpsilons(matrixConfig.set(0, 0, epsilons.get(i, 0)));
                // Retrieve lambda minimizer, which is a 1x1 matrix.
                IMatrix lambdaMin = gr.run(surrogateFun);
                // x_vec = x_vec + lambda*ei, where ei is identity vector with index i.
                x.set(i, 0, x.get(i, 0) + lambdaMin.get(0, 0));
                // Reset the calculated unimodal interval for golden ratio
                ((GoldenRatio) gr).setInterval(null);
            }
            subXXS = x.nSub(xS);
        } while (l2Norm(subXXS) > epsilonsNorm);

        return x;
    }

}
