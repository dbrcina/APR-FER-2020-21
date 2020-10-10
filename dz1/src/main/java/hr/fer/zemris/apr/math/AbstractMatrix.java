package hr.fer.zemris.apr.math;

/**
 * An abstract implementation of {@link IMatrix} interface.
 *
 * @author dbrcina
 */
public abstract class AbstractMatrix implements IMatrix {

    private final static double DELTA = 1E-9;

    @Override
    public IMatrix add(IMatrix other) {
        if (getRowsCount() != other.getRowsCount() || getColumnsCount() != other.getColumnsCount()) {
            String methodName = getClass().getSimpleName() + "::add(IMatrix)";
            throw new IllegalArgumentException(methodName + " dimensions of both matrices aren't equal!");
        }
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                set(i, j, get(i, j) + other.get(i, j));
            }
        }
        return this;
    }

    @Override
    public IMatrix sub(IMatrix other) {
        if (getRowsCount() != other.getRowsCount() || getColumnsCount() != other.getColumnsCount()) {
            String methodName = getClass().getSimpleName() + "::sub(IMatrix)";
            throw new IllegalArgumentException(methodName + " dimensions of both matrices aren't equal!");
        }
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                set(i, j, get(i, j) - other.get(i, j));
            }
        }
        return this;
    }

    @Override
    public IMatrix scalar(double scalar) {
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                set(i, j, get(i, j) * scalar);
            }
        }
        return this;
    }

    @Override
    public IMatrix transpose() {
        IMatrix transposed = newInstance(getColumnsCount(), getRowsCount());
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                transposed.set(j, i, get(i, j));
            }
        }
        return transposed;
    }

    @Override
    public IMatrix mul(IMatrix other) {
        IMatrix result = newInstance(getRowsCount(), other.getColumnsCount());
        if (getColumnsCount() != other.getRowsCount()) {
            String methodName = getClass().getSimpleName() + "::mul(IMatrix)";
            throw new IllegalArgumentException(methodName + " matrices dimensions are invalid!");
        }
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < other.getColumnsCount(); j++) {
                for (int k = 0; k < getColumnsCount(); k++) {
                    result.set(i, j, result.get(i, j) + get(i, k) * other.get(k, j));
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix)) return false;
        Matrix other = (Matrix) o;
        if (getRowsCount() != other.getRowsCount() || getColumnsCount() != other.getColumnsCount()) {
            return false;
        }
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                if (Math.abs(get(i, j) - other.get(i, j)) > DELTA) {
                    return false;
                }
            }
        }
        return true;
    }

}
