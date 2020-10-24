package hr.fer.zemris.apr.hw02.function;

/**
 * @author dbrcina
 */
public class F2 extends AbstractFunction {

    private final double a;
    private final double b;
    private final double c;

    public F2(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double value(double[] point) {
        testValuePointDimension(point, 2);
        double x1 = point[0];
        double x2 = point[1];
        return Math.pow(x1 - a, 2) + b * Math.pow(x2 - c, 2);
    }

}
