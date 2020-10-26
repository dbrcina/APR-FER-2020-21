package hr.fer.zemris.apr.hw02.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * @author dbrcina
 */
public class F2 extends AbstractFunction {

    @Override
    public double value(IMatrix point) {
        testValuePointDimension(point, 2);
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return Math.pow(x1 - 4, 2) + 4 * Math.pow(x2 - 2, 2);
    }

}
