package hr.fer.zemris.apr.hw03.optimization.constraint;

import hr.fer.zemris.apr.hw01.math.IMatrix;

import java.util.function.Function;

/**
 * @author dbrcina
 */
public class EqualityConstraint extends ImplicitConstraint {

    public EqualityConstraint(Function<IMatrix, Double> constraint) {
        super(constraint);
    }

    @Override
    public boolean testConstraint(IMatrix point) {
        return applyConstraint(point) == 0;
    }

}
