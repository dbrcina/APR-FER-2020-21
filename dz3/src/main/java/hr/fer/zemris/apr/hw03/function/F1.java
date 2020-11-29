package hr.fer.zemris.apr.hw03.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;

/**
 * An implementation of the <i>Rosenbrock function</i>.
 *
 * @author dbrcina
 */
public class F1 extends AbstractFunction {

    @Override
    public double value(IMatrix point) {
        testPointDimension(point, 2, "value");
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return 100 * Math.pow(x2 - x1 * x1, 2) + Math.pow(1 - x1, 2);
    }

    @Override
    public IMatrix gradient(IMatrix point) {
        testPointDimension(point, 2, "gradient");
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return new Matrix(2, 1)
                .set(0, 0, 2 * x1 - 400 * x1 * (-x1 * x1 + x2) - 2)
                .set(1, 0, -200 * x1 * x1 + 200 * x2);
    }

    @Override
    public IMatrix hesse(IMatrix point) {
        testPointDimension(point, 2, "hesse");
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return new Matrix(2, 2)
                .set(0, 0, 1200 * x1 * x1 - 400 * x2 + 2)
                .set(0, 1, -400 * x1)
                .set(1, 0, -400 * x1)
                .set(1, 1, 200);
    }

}
