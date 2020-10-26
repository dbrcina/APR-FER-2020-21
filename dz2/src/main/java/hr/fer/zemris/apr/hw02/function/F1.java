package hr.fer.zemris.apr.hw02.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;

/**
 * An implementation of the <i>Rosenbrock function</i>.
 *
 * @author dbrcina
 */
public class F1 extends AbstractFunction {

    @Override
    public double value(IMatrix point) {
        testValuePointDimension(point, 2);
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return 100 * Math.pow(x2 - x1 * x1, 2) + Math.pow(1 - x1, 2);
    }

}
