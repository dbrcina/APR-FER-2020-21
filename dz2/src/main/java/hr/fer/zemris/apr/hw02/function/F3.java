package hr.fer.zemris.apr.hw02.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * @author dbrcina
 */
public class F3 extends AbstractFunction {

    @Override
    public double value(IMatrix point) {
        testValuePointDimension(point, point.getRowsCount());
        double sum = 0.0;
        for (int i = 0; i < point.getRowsCount(); i++) {
            sum += Math.pow(point.get(i, 0) - (i + 1), 2);
        }
        return sum;
    }

}
