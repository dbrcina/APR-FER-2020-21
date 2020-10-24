package hr.fer.zemris.apr.hw02.function;

/**
 * @author dbrcina
 */
public class F4 extends AbstractFunction {

    @Override
    public double value(double[] point) {
        testValuePointDimension(point, 2);
        double x1 = point[0];
        double x2 = point[1];
        double x1Square = x1 * x1;
        double x2Square = x2 * x2;
        return Math.abs(x1Square - x2Square) + Math.sqrt(x1Square + x2Square);
    }

}
