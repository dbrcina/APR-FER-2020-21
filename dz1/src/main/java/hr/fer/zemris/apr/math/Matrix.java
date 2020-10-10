package hr.fer.zemris.apr.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * An implementation of {@link AbstractMatrix} class.
 *
 * @author dbrcina
 */
public class Matrix extends AbstractMatrix {

    private final double[][] data;

    /* ---------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTORS ------------------------- */

    /**
     * Constructor. Creates a new instance of Matrix and copies provided <code>data</code>.
     *
     * @param data data.
     * @throws NullPointerException if provided <code>data</code> is <code>null</code>.
     */
    public Matrix(double[][] data) {
        Objects.requireNonNull(data, "Matrix::Matrix(double[][]) doesn't permit null values!");
        this.data = Arrays.stream(data)
                .map(double[]::clone)
                .toArray(double[][]::new);
    }

    /**
     * Constructor. Creates a new instance of Matrix with provided number of <code>rows</code> and
     * number of <code>columns</code> parameters.
     *
     * @param rows    number of rows.
     * @param columns number of columns.
     * @throws IllegalArgumentException if < 0 value is provided.
     */
    public Matrix(int rows, int columns) {
        this(new double[testIfLessThanZero(rows)][testIfLessThanZero(columns)]);
    }

    /* This method is used only for validating Matrix(int,int) constructor. */
    private static int testIfLessThanZero(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Matrix::Matrix(int,int) doesn't permit negative values!");
        }
        return value;
    }

    /* ---------------------------------------------------------------- */
    /* ---------------------- GET AND SET METHODS --------------------- */

    @Override
    public int getRowsCount() {
        return data.length;
    }

    @Override
    public int getColumnsCount() {
        return data[0].length;
    }

    @Override
    public double get(int row, int column) {
        testOutOfBounds(row, column, "Matrix::get(int,int)");
        return data[row][column];
    }

    @Override
    public void set(int row, int column, double value) {
        testOutOfBounds(row, column, "Matrix::set(int,int,double)");
        data[row][column] = value;
    }

    /* Method used for validating row and column indexes. */
    private void testOutOfBounds(int row, int column, String method) {
        if (row < 0 || row > getRowsCount() - 1) {
            throw new IndexOutOfBoundsException(String.format(
                    "%s row index is invalid! It should be from [0, %d)!",
                    method, getRowsCount())
            );
        }
        if (column < 0 || column > getColumnsCount() - 1) {
            throw new IndexOutOfBoundsException(String.format(
                    "%s column index is invalid! It should be from [0, %d)!",
                    method, getColumnsCount())
            );
        }
    }

    /* ---------------------------------------------------------------- */
    /* --------------------- CONSTRUCTION METHODS --------------------- */

    @Override
    public Matrix copy() {
        return new Matrix(data);
    }

    @Override
    public IMatrix newInstance(int rows, int columns) {
        return new Matrix(rows, columns);
    }

}
