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
     * @throws IllegalArgumentException if < 1 value is provided.
     */
    public Matrix(int rows, int columns) {
        this(new double[testIfLessThanOne(rows)][testIfLessThanOne(columns)]);
    }

    /* This method is used only for validating Matrix(int,int) constructor. */
    private static int testIfLessThanOne(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("Matrix::Matrix(int,int) doesn't permit < 1 values!");
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
        testOutOfBounds(row, column, "get(int,int)");
        return data[row][column];
    }

    @Override
    public void set(int row, int column, double value) {
        testOutOfBounds(row, column, "set(int,int,double)");
        data[row][column] = value;
    }

    /* ---------------------------------------------------------------- */
    /* ---------------------- MANIPULATION METHODS -------------------- */

    @Override
    public IMatrix swapRows(int r1, int r2) {
        testOutOfBounds(r1, 0, "swapRows(int,int)");
        testOutOfBounds(r2, 0, "swapRows(int,int)");
        double[] temp = data[r1];
        data[r1] = data[r2];
        data[r2] = temp;
        numOfRowsSwapped++;
        return this;
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
