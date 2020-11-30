package hr.fer.zemris.apr.hw03.optimization.constraint;

import hr.fer.zemris.apr.hw01.math.IMatrix;

import java.util.function.Consumer;

/**
 * @author dbrcina
 */
public class ExplicitConstraint implements Constraint {

    private final int index;
    private final double xMin;
    private final double xMax;

    public ExplicitConstraint(int index, double xMin, double xMax) {
        this.index = index;
        this.xMin = xMin;
        this.xMax = xMax;
    }

    @Override
    public IMatrix fitConstraint(IMatrix point, Consumer<IMatrix> action) {
        double xi = point.get(index, 0);
        if (xi < xMin) xi = xMin;
        else if (xi > xMax) xi = xMax;
        point.set(index, 0, xi);
        return point;
    }

    @Override
    public boolean testConstraint(IMatrix point) {
        double xi = point.get(index, 0);
        return xi >= xMin && xi <= xMax;
    }

    @Override
    public double applyConstraint(IMatrix point) {
        throw new RuntimeException("Not implemented!");
    }

    public double getXMin() {
        return xMin;
    }

    public double getXMax() {
        return xMax;
    }

}
