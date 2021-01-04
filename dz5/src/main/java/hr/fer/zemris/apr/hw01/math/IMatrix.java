package hr.fer.zemris.apr.hw01.math;

/**
 * Simple interface that provides some generic methods for matrix manipulation.
 *
 * @author dbrcina
 */
public interface IMatrix {

    /**
     * @return number of rows.
     */
    int getRowsCount();

    /**
     * @return number of columns.
     */
    int getColumnsCount();

    /**
     * Retrieves a value at position <code>[row][column]</code>.
     *
     * @param row    row index.
     * @param column column index.
     * @return a value from provided position.
     * @throws IndexOutOfBoundsException if <code>row</code> or <code>column</code> index is invalid.
     */
    double get(int row, int column);

    /**
     * Sets a value at position <code>[row][column]</code>.
     *
     * @param row    row index.
     * @param column column index.
     * @param value  value.
     * @return <b>this</b> IMatrix object.
     * @throws IndexOutOfBoundsException if <code>row</code> or <code>column</code> index is invalid.
     */
    IMatrix set(int row, int column, double value);

    /**
     * Sets the <code>data</code> at <code>row</code> index.
     *
     * @param row  row index.
     * @param data data.
     * @return <b>this</b> IMatrix object.
     * @throws IndexOutOfBoundsException if <code>row</code> index is invalid.
     * @throws IllegalArgumentException  if provided array is <code>null</code> or array dimensions don'intPeriod fit.
     */
    IMatrix setRow(int row, double[] data);

    /**
     * Sets the <code>data</code> at <code>column</code> index.
     *
     * @param column column index.
     * @param data   data.
     * @return <b>this</b> IMatrix object.
     * @throws IndexOutOfBoundsException if <code>row</code> index is invalid.
     * @throws IllegalArgumentException  if provided array is <code>null</code> or array dimensions don'intPeriod fit.
     */
    IMatrix setColumn(int column, double[] data);

    /**
     * Adds <b>this</b> IMatrix object to <code>other</code> IMatrix object. Method modifies <b>this</b> IMatrix object.
     *
     * @param other other IMatrix object.
     * @return <b>this</b> IMatrix object.
     * @throws IllegalArgumentException if dimensions of both IMatrix objects are not equal.
     */
    IMatrix add(IMatrix other);

    /**
     * Creates a new IMatrix object and then applies {@link #add(IMatrix)} method.
     *
     * @param other other IMatrix object.
     * @return new instance of IMatrix as a result.
     */
    default IMatrix nAdd(IMatrix other) {
        return copy().add(other);
    }

    /**
     * Substitutes <b>this</b> IMatrix object from <code>other</code> IMatrix object. Method modifies <b>this</b>
     * IMatrix object.
     *
     * @param other other IMatrix object.
     * @return <b>this</b> IMatrix object.
     * @throws IllegalArgumentException if dimensions of both IMatrix objects are not equal.
     */
    IMatrix sub(IMatrix other);

    /**
     * Creates a new IMatrix object and then applies {@link #sub(IMatrix)} method.
     *
     * @param other other IMatrix object.
     * @return new instance of IMatrix as a result.
     */
    default IMatrix nSub(IMatrix other) {
        return copy().sub(other);
    }

    /**
     * Multiplies each element of <b>this</b> IMatrix object by provided <code>scalar</code> value.
     *
     * @param scalar scalar value.
     * @return <b>this</b> IMatrix object.
     */
    IMatrix scalarMul(double scalar);

    /**
     * Creates a new IMatrix object and then applies {@link #scalarMul(double)} method.
     *
     * @param scalar scalar value.
     * @return new instance of IMatrix as a result.
     */
    default IMatrix nScalarMul(double scalar) {
        return copy().scalarMul(scalar);
    }

    /**
     * Performs matrix transpose operation.
     *
     * @return new instance of IMatrix as a result.
     */
    IMatrix transpose();

    /**
     * Performs matrix multiplication operation.
     *
     * @param other other IMatrix object.
     * @return new instance of IMatrix as a result.
     * @throws IllegalArgumentException if number of columns and number of rows of <b>this</b> and <code>other</code>
     *                                  IMatrix objects are not equal.
     */
    IMatrix mul(IMatrix other);

    /**
     * Calculates matrix determinant.
     *
     * @return matrix determinant.
     * @throws RuntimeException if matrix is not a square matrix.
     */
    double determinant();

    /**
     * Creates a new IMatrix object without provided <code>row</code> and <code>column</code>.
     *
     * @return new instance of IMatrix as a result.
     * @throws IndexOutOfBoundsException if <code>row</code> or <code>column</code> index is invalid.
     */
    IMatrix subMatrix(int row, int column);

    /**
     * Inverts <b>this</b> IMatrix object if possible.
     *
     * @return inverted <b>this</b> IMatrix object.
     * @throws RuntimeException if matrix is not a square matrix or if matrix is singular.
     */
    IMatrix invert();

    /**
     * Swaps row <code>r1</code> with row <code>r2</code>.
     *
     * @param r1 row 1 index.
     * @param r2 row 2 index.
     * @return <b>this</b> IMatrix object.
     * @throws IndexOutOfBoundsException if row indexes are invalid.
     */
    IMatrix swapRows(int r1, int r2);

    /**
     * @return number of rows swapped by {@link #swapRows(int, int)} method.
     */
    int getNumOfRowsSwapped();

    /**
     * Resets the counter that is used along with {@link #swapRows(int, int)} method.
     */
    void resetNumOfRowsSwappedCounter();

    /**
     * @return <code>true</code> if matrix is square matrix, otherwise <code>false</code>.
     */
    boolean isSquareMatrix();

    /**
     * Makes a copy of <b>this</b> IMatrix object.
     *
     * @return copied IMatrix object.
     */
    IMatrix copy();

    /**
     * Creates a new IMatrix object which dimensions are set to provided <code>rows</code> and <code>columns</code> and
     * with all values set to zero.
     *
     * @return new instance of IMatrix as a result.
     * @throws IllegalArgumentException if value < 1 is provided.
     */
    IMatrix newInstance(int rows, int columns);

    /**
     * @param row row index.
     * @return row data at <code>row</code> index.
     * @throws IndexOutOfBoundsException if <code>row</code> index is invalid.
     */
    double[] rowData(int row);

}
