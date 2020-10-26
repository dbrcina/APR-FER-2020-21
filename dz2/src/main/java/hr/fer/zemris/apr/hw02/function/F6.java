package hr.fer.zemris.apr.hw02.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * An implementation of <i>Schaffer's function f6</i>.
 *
 * @author dbrcina
 */
public class F6 extends AbstractFunction {

    @Override
    public double value(IMatrix point) {
        testValuePointDimension(point, 2);
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        double squareSum = x1 * x1 + x2 * x2;
        double numerator = Math.pow(Math.sin(Math.sqrt(squareSum)), 2) - 0.5;
        double denominator = Math.pow(1 + 0.001 * squareSum, 2);
        return 0.5 + numerator / denominator;
    }

}
