package hr.fer.zemris.apr.hw02.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * @author dbrcina
 */
public class F4 extends AbstractFunction {

    @Override
    public double value(IMatrix point) {
        testValuePointDimension(point, 2);
        double x1 = point.get(0,0);
        double x2 = point.get(1,0);
        double x1Square = x1 * x1;
        double x2Square = x2 * x2;
        return Math.abs(x1Square - x2Square) + Math.sqrt(x1Square + x2Square);
    }

}
