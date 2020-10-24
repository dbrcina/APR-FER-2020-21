package hr.fer.zemris.apr.hw02.function;

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
    public double value(double[] point) {
        testValuePointDimension(point, 2);
        double x1 = point[0];
        double x2 = point[1];
        return Math.pow(a - x1, 2) + b * Math.pow(x2 - x1 * x1, 2);
    }

}
