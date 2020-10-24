package hr.fer.zemris.apr.hw02.function;

/**
 * An implementation of <i>Schaffer's function f6</i>.
 *
 * @author dbrcina
 */
public class F6 extends AbstractFunction {

    @Override
    public double value(double[] point) {
        testValuePointDimension(point, 2);
        double x1 = point[0];
        double x2 = point[1];
        double squareSum = x1 * x1 + x2 * x2;
        double numerator = Math.pow(Math.sin(Math.sqrt(squareSum)), 2) - 0.5;
        double denominator = Math.pow(1 + 0.001 * squareSum, 2);
        return 0.5 + numerator / denominator;
    }

}
