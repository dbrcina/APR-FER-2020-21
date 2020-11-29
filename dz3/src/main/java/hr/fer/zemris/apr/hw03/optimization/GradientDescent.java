package hr.fer.zemris.apr.hw03.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;
import hr.fer.zemris.apr.hw03.function.AbstractFunction;
import hr.fer.zemris.apr.hw03.function.IFunction;

/**
 * An implementation of the <i>Gradient descent</i> optimization algorithm.
 *
 * @author dbrcina
 * @see IOptAlgorithm
 */
public class GradientDescent extends GradientOptAlgorithm {

    /**
     * @see AbstractOptAlgorithm#AbstractOptAlgorithm(int)
     */
    protected GradientDescent() {
        super(0);
    }

    @Override
    public IMatrix run(IFunction function) {
        super.run(function);

        IOptAlgorithm gr = null;
        if (optimized) {
            try {
                gr = OptAlgorithmProvider.getInstance("GoldenRatio");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        IMatrix solution = getInitialPoint();
        double bestValue = function.value(solution);
        IMatrix current = solution.copy();
        double epsilon = getEpsilons().get(0, 0);
        if (gr != null) {
            gr.setEpsilons(new Matrix(1, 1).set(0, 0, epsilon));
        }
        int divergenceCounter = 0;

        while (true) {
            incrementIterations(1);
            IMatrix gradient = function.gradient(current);
            if (l2Norm(gradient) < epsilon) break;
            if (gr != null) {
                IFunction surrogateFun = new AbstractFunction() {
                    @Override
                    public double value(IMatrix point) {
                        return function.value(current.nAdd(gradient.nScalarMul(point.get(0, 0))));
                    }

                    @Override
                    public IMatrix gradient(IMatrix point) {
                        return null;
                    }

                    @Override
                    public IMatrix hesse(IMatrix point) {
                        return null;
                    }
                };
                gr.setInitialPoint(new Matrix(1, 1).set(0, 0, 0));
                IMatrix lambdaMin = gr.run(surrogateFun);
                current.add(gradient.scalarMul(lambdaMin.get(0, 0)));
            } else {
                current.sub(gradient);
            }
            double currentValue = function.value(current);
            if (isDiverging(currentValue, bestValue)) break;
            solution = current.copy();
            bestValue = function.value(solution);
        }

        return solution;
    }

}
