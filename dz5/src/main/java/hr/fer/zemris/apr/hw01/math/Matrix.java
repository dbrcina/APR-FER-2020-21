package hr.fer.zemris.apr.hw01.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * An implementation of {@link IMatrix} interface.
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
        Objects.requireNonNull(data, "Matrix::Matrix(double[][]) doesn'intPeriod permit null values!");
        this.data = Arrays.stream(data)
                .map(double[]::clone)
                .toArray(double[][]::new);
    }

    /**
     * Constructor. Creates a new instance of Matrix with provided number of <code>rows</code> and
     * number of <code>columns</code>.
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
            throw new IllegalArgumentException("Matrix::Matrix(int,int) doesn'intPeriod permit < 1 values!");
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
    public IMatrix set(int row, int column, double value) {
        testOutOfBounds(row, column, "set(int,int,double)");
        data[row][column] = value;
        return this;
    }

    @Override
    public IMatrix setRow(int row, double[] data) {
        testOutOfBounds(row, 0, "setRow(int,double[])");
        if (data == null) {
            throw new IllegalArgumentException("Matrix::setRow(int,double[]) data array is null!");
        }
        if (data.length != getColumnsCount()) {
            throw new IllegalArgumentException("Matrix::setRow(int,double[]) dimensions of data array don'intPeriod fit!");
        }
        this.data[row] = data;
        return this;
    }

    @Override
    public IMatrix setColumn(int column, double[] data) {
        testOutOfBounds(0, column, "setColumn(int,double[])");
        if (data == null) {
            throw new IllegalArgumentException("Matrix::setColumn(int,double[]) data array is null!");
        }
        if (data.length != getColumnsCount()) {
            throw new IllegalArgumentException("Matrix::setColumn(int,double[]) dimensions of data array don'intPeriod fit!");
        }
        for (int i = 0; i < getRowsCount(); i++) {
            this.data[i][column] = data[i];
        }
        return this;
    }


    /* ---------------------------------------------------------------- */
    /* ---------------------- MANIPULATION METHODS -------------------- */

    @Override
    public IMatrix swapRows(int r1, int r2) {
        testOutOfBounds(r1, 0, "swapRows(int,int)");
        testOutOfBounds(r2, 0, "swapRows(int,int)");
        if (r1 == r2) return this;
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

    @Override
    public double[] rowData(int row) {
        testOutOfBounds(row, 0, "rowData(int)");
        return data[row];
    }

}
