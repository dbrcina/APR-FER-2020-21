package hr.fer.zemris.apr.hw02.function;

/**
 * @author dbrcina
 */
public class F3 extends AbstractFunction {

    @Override
    public double value(double[] point) {
        testValuePointDimension(point, point.length);
        double sum = 0.0;
        for (int i = 0; i < point.length; i++) {
            sum += Math.pow(point[i] - (i + 1), 2);
        }
        return sum;
    }

}
