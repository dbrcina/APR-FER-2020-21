package hr.fer.zemris.apr.hw02.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * An implementation of <i>Rosenbrock function</i>.
 *
 * @author dbrcina
 */
public class F1 extends AbstractFunction {

    private final double a;
    private final double b;

    public F1(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double value(IMatrix point) {
        testValuePointDimension(point, 2);
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return Math.pow(a - x1, 2) + b * Math.pow(x2 - x1 * x1, 2);
    }

}
