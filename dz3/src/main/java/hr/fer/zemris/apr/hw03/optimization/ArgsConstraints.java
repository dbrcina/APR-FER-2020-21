package hr.fer.zemris.apr.hw03.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;

import java.util.Random;
import java.util.function.Predicate;

/**
 * Models constraints for function's arguments.
 *
 * @author dbrcina
 */
public class ArgsConstraints {

    private final double[][] explicitConstraints;
    private final Predicate<double[]>[] implicitConstraints;

    public ArgsConstraints(double[][] explicitConstraints, Predicate<double[]>[] implicitConstraints) {
        this.explicitConstraints = explicitConstraints;
        this.implicitConstraints = implicitConstraints;
    }

    public IMatrix generatePointUsingExplicitConstraints(Random random) {
        IMatrix point = new Matrix(explicitConstraints.length, 1);
        for (int i = 0; i < explicitConstraints.length; i++) {
            double[] explicit = explicitConstraints[i];
            double num = explicit[0] + (explicit[1] - explicit[0]) * random.nextDouble();
            point.set(i, 0, num);
        }
        return point;
    }

    public IMatrix testExplicitConstraints(IMatrix point) {
        for (double[] explicit : explicitConstraints) {
            for (int i = 0; i < point.getRowsCount(); i++) {
                if (point.get(i, 0) < explicit[0]) point.set(i, 0, explicit[0]);
                else if (point.get(i, 1) > explicit[1]) point.set(i, 0, explicit[1]);
            }
        }
        return point;
    }

    public boolean testImplicitConstraints(IMatrix point) {
        for (Predicate<double[]> implicit : implicitConstraints) {
            if (!implicit.test(point.columnData(0))) return false;
        }
        return true;
    }

}
