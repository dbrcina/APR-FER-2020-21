package hr.fer.zemris.apr.hw03.function;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;

public class F2 extends AbstractFunction {

    @Override
    public double value(IMatrix point) {
        testPointDimension(point, 2, "value");
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return Math.pow(x1 - 4, 2) + 4 * Math.pow(x2 - 2, 2);
    }

    @Override
    public IMatrix gradient(IMatrix point) {
        testPointDimension(point, 2, "gradient");
        double x1 = point.get(0, 0);
        double x2 = point.get(1, 0);
        return new Matrix(2, 1)
                .set(0, 0, 2 * x1 - 8)
                .set(1, 0, 8 * x2 - 16);
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
                .set(1, 1, 8);
    }

}
