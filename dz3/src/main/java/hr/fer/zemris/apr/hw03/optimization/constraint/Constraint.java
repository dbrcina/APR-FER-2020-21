package hr.fer.zemris.apr.hw03.optimization.constraint;

import hr.fer.zemris.apr.hw01.math.IMatrix;

import java.util.function.Consumer;

/**
 * @author dbrcina
 */
public interface Constraint {

    default IMatrix fitConstraint(IMatrix point, Consumer<IMatrix> action) {
        while (!testConstraint(point)) {
            action.accept(point);
        }
        return point;
    }

    boolean testConstraint(IMatrix point);

    double applyConstraint(IMatrix point);

}
