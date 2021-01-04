package hr.fer.zemris.apr.hw01.math;

/**
 * An abstract implementation of {@link IMatrix} interface.
 *
 * @author dbrcina
 */
public abstract class AbstractMatrix implements IMatrix {

    protected int numOfRowsSwapped;

    @Override
    public IMatrix add(IMatrix other) {
        testEqualDimensions(other, "add(IMatrix)");
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                set(i, j, get(i, j) + other.get(i, j));
            }
        }
        return this;
    }

    @Override
    public IMatrix sub(IMatrix other) {
        testEqualDimensions(other, "sub(IMatrix)");
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                set(i, j, get(i, j) - other.get(i, j));
            }
        }
        return this;
    }

    @Override
    public IMatrix scalarMul(double scalar) {
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
        for (int i = 0; i < result.getRowsCount(); i++) {
            for (int j = 0; j < result.getColumnsCount(); j++) {
                for (int k = 0; k < getColumnsCount(); k++) {
                    result.set(i, j, result.get(i, j) + get(i, k) * other.get(k, j));
                }
            }
        }
        return result;
    }

    @Override
    public double determinant() {
        testSquareMatrix("determinant()");
        if (getRowsCount() == 1) return get(0, 0);
        double result = 0.0;
        for (int i = 0; i < getRowsCount(); i++) {
            result += get(0, i) * calculateCofactor(0, i);
        }
        return result;
    }

    @Override
    public IMatrix subMatrix(int row, int column) {
        IMatrix result = newInstance(getRowsCount() - 1, getColumnsCount() - 1);
        for (int i = 0, r = 0; i < getRowsCount(); i++) {
            if (i == row) continue;
            for (int j = 0, c = 0; j < getColumnsCount(); j++) {
                if (j == column) continue;
                result.set(r, c++, get(i, j));
            }
            r++;
        }
        return result;
    }

    @Override
    public IMatrix invert() {
        double determinant = determinant();
        if (determinant == 0) {
            throw new RuntimeException(getClass().getSimpleName() + "::invert() matrix is singular!");
        }
        IMatrix adJoint = findAdJointMatrix();
        return adJoint.scalarMul(1 / determinant);
    }

    /* Method is self-explanatory... */
    private IMatrix findAdJointMatrix() {
        if (getRowsCount() == 1) {
            return this;
        }
        IMatrix result = newInstance(getRowsCount(), getColumnsCount());
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                result.set(i, j, calculateCofactor(i, j));
            }
        }
        return result.transpose();
    }

    /* Calculates Cij... */
    private double calculateCofactor(int i, int j) {
        double minor = subMatrix(i, j).determinant();
        int sign = (i + j) % 2 == 0 ? 1 : -1;
        return sign * minor;
    }

    @Override
    public int getNumOfRowsSwapped() {
        return numOfRowsSwapped;
    }

    @Override
    public void resetNumOfRowsSwappedCounter() {
        numOfRowsSwapped = 0;
    }

    @Override
    public boolean isSquareMatrix() {
        return getRowsCount() == getColumnsCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IMatrix)) return false;
        IMatrix other = (IMatrix) o;
        try {
            testEqualDimensions(other, "equals(Object)");
        } catch (IllegalArgumentException e) {
            return false;
        }
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                if (Math.abs(get(i, j) - other.get(i, j)) > MatrixUtils.DELTA) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method used for validating row and column indexes.
     *
     * @param row    row index.
     * @param column column index.
     * @param method method's declaration which invoked this method.
     */
    protected void testOutOfBounds(int row, int column, String method) {
        String className = getClass().getSimpleName();
        if (row < 0 || row > getRowsCount() - 1) {
            throw new IndexOutOfBoundsException(String.format(
                    "%s row index is invalid! It should be from [0, %d)!",
                    className + "::" + method, getRowsCount())
            );
        }
        if (column < 0 || column > getColumnsCount() - 1) {
            throw new IndexOutOfBoundsException(String.format(
                    "%s column index is invalid! It should be from [0, %d)!",
                    className + "::" + method, getColumnsCount())
            );
        }
    }

    /**
     * Checks whether current matrix is square matrix.
     *
     * @param method method's declaration which invoked this method.
     *
     * @throws RuntimeException if matrix is not square matrix.
     */
    protected void testSquareMatrix(String method) {
        if (!isSquareMatrix()) {
            String className = getClass().getSimpleName();
            String methodName = className + "::" + method;
            throw new RuntimeException(methodName + " matrix is not a square matrix!");
        }
    }

    /**
     * Checks whether dimension of current matrix are equal to provided <code>other</code> matrix.
     *
     * @param other  other matrix.
     * @param method method's declaration which invoked this method.
     *
     * @throws IllegalArgumentException if dimensions are not equal.
     */
    protected void testEqualDimensions(IMatrix other, String method) {
        if (getRowsCount() != other.getRowsCount() || getColumnsCount() != other.getColumnsCount()) {
            String className = getClass().getSimpleName();
            String methodName = className + "::" + method;
            throw new IllegalArgumentException(methodName + " dimensions of both matrices aren't equal!");
        }
    }

}
