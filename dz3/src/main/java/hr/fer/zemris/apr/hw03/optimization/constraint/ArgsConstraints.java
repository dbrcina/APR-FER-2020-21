package hr.fer.zemris.apr.hw03.optimization.constraint;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Models constraints for function's arguments.
 *
 * @author dbrcina
 */
public class ArgsConstraints {

    private final ExplicitConstraint[] explicitConstraints;
    private final ImplicitConstraint[] implicitConstraints;

    public ArgsConstraints(ExplicitConstraint[] explicitConstraints, ImplicitConstraint[] implicitConstraints) {
        this.explicitConstraints = explicitConstraints;
        this.implicitConstraints = implicitConstraints;
    }

    public IMatrix generatePointUsingExplicitConstraints(Random random) {
        IMatrix point = new Matrix(explicitConstraints.length, 1);
        for (int i = 0; i < explicitConstraints.length; i++) {
            ExplicitConstraint explicit = explicitConstraints[i];
            double num = explicit.getXMin() + (explicit.getXMax() - explicit.getXMin()) * random.nextDouble();
            point.set(i, 0, num);
        }
        return point;
    }

    public IMatrix fitExplicitConstraints(IMatrix point) {
        Arrays.stream(explicitConstraints).forEach(e -> e.fitConstraint(point, null));
        return point;
    }

    public IMatrix fitImplicitConstraints(IMatrix point, Consumer<IMatrix> action) {
        Arrays.stream(implicitConstraints).forEach(i -> i.fitConstraint(point, action));
        return point;
    }

    public boolean testImplicitConstraints(IMatrix point) {
        return Arrays.stream(implicitConstraints).allMatch(i -> i.testConstraint(point));
    }

    public double[] applyEqualityConstraints(IMatrix point) {
        return applyImplicitConstraints(point, i -> i instanceof EqualityConstraint);
    }

    public double[] applyInequalityConstraints(IMatrix point) {
        return applyImplicitConstraints(point, i -> i instanceof InequalityConstraint);
    }

    private double[] applyImplicitConstraints(IMatrix point, Predicate<ImplicitConstraint> predicate) {
        return Arrays.stream(implicitConstraints)
                .filter(predicate)
                .mapToDouble(i -> i.applyConstraint(point))
                .toArray();
    }

}
