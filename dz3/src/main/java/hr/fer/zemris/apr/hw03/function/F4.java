package hr.fer.zemris.apr.hw03.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;

public class F4 extends AbstractFunction {

    @Override
    public double value(IMatrix point) {
        testPointDimension(point, 2, "value");
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return Math.pow(x1 - 3, 2) + x2 * x2;
    }

    @Override
    public IMatrix gradient(IMatrix point) {
        testPointDimension(point, 2, "gradient");
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return new Matrix(2, 1)
                .set(0, 0, 2 * x1 - 6)
                .set(1, 0, 2 * x2);
    }

    @Override
    public IMatrix hesse(IMatrix point) {
        testPointDimension(point, 2, "hesse");
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return new Matrix(2, 2)
                .set(0, 0, 2)
                .set(0, 1, 0)
                .set(1, 0, 0)
                .set(1, 1, 2);
    }

}
