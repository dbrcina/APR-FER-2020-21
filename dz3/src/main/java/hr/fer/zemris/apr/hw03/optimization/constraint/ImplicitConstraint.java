package hr.fer.zemris.apr.hw03.optimization.constraint;

import hr.fer.zemris.apr.hw01.math.IMatrix;

import java.util.function.Function;

/**
 * @author dbrcina
 */
public abstract class ImplicitConstraint implements Constraint {

    private final Function<IMatrix, Double> constraint;

    protected ImplicitConstraint(Function<IMatrix, Double> constraint) {
        this.constraint = constraint;
    }

    @Override
    public double applyConstraint(IMatrix point) {
        return constraint.apply(point);
    }

}
