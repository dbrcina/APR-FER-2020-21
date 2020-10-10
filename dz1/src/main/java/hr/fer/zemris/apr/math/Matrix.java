package hr.fer.zemris.apr.math;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author dbrcina
 */
public class Matrix {

    private final static double DELTA = 1E-9;

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

    /**
     * @return number of rows.
     */
    public int getRowsCount() {
        return data.length;
    }

    /**
     * @return number of columns.
     */
    public int getColumnsCount() {
        return data[0].length;
    }

    /**
     * Retrieves an element at position <code>[row][column]</code>.
     *
     * @param row    row index.
     * @param column column index.
     * @return an element from provided position.
     * @throws IndexOutOfBoundsException if <code>row</code> or <code>column</code> is invalid.
     */
    public double get(int row, int column) {
        testOutOfBounds(row, column, "Matrix::get(int,int)");
        return data[row][column];
    }

    public void set(int row, int column, double value) {
        testOutOfBounds(row, column, "Matrix::set(int,int,double)");
        data[row][column] = value;
    }

    /* Method used for validating row and column indexes */
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
    /* ----------------------- MATRIX OPERATIONS ---------------------- */

    /**
     * Adds <b>this</b> matrix with <code>other</code> matrix. Method modifies <b>this</b> matrix.
     *
     * @param other other matrix.
     * @return <b>this</b> matrix.
     * @throws IllegalArgumentException if dimensions of both matrix aren't equal.
     */
    public Matrix add(Matrix other) {
        if (getRowsCount() != other.getRowsCount() || getColumnsCount() != other.getColumnsCount()) {
            throw new IllegalArgumentException("Matrix::add(Matrix) dimensions of both matrices aren't equal!");
        }
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                data[i][j] += other.data[i][j];
            }
        }
        return this;
    }

    /**
     * Creates a new Matrix object and then applies {@link #add(Matrix)} method.
     *
     * @param other other matrix.
     * @return new instance of Matrix as a result.
     */
    public Matrix nAdd(Matrix other) {
        return copy().add(other);
    }

    /**
     * Substitutes <b>this</b> matrix with <code>other</code> matrix. Method modifies <b>this</b> matrix.
     *
     * @param other other matrix.
     * @return <b>this</b> matrix.
     * @throws IllegalArgumentException if dimensions of both matrix aren't equal.
     */
    public Matrix sub(Matrix other) {
        if (getRowsCount() != other.getRowsCount() || getColumnsCount() != other.getColumnsCount()) {
            throw new IllegalArgumentException("Matrix::sub(Matrix) dimensions of both matrices aren't equal!");
        }
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                data[i][j] -= other.data[i][j];
            }
        }
        return this;
    }

    /**
     * Creates a new Matrix object and then applies {@link #sub(Matrix)} method.
     *
     * @param other other matrix.
     * @return new instance of Matrix as a result.
     */
    public Matrix nSub(Matrix other) {
        return copy().sub(other);
    }

    /**
     * Multiplies each element of <b>this</b> matrix by provided <code>scalar</code> value.
     *
     * @param scalar scalar value.
     * @return <b>this</b> matrix.
     */
    public Matrix scalar(double scalar) {
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                data[i][j] *= scalar;
            }
        }
        return this;
    }

    /**
     * Creates a new Matrix object and then applies {@link #scalar(double)} method.
     *
     * @param scalar scalar value.
     * @return new instance of Matrix as a result.
     */
    public Matrix nScalar(double scalar) {
        return copy().scalar(scalar);
    }

    /**
     * Performs matrix multiplication operation.
     *
     * @param other other matrix.
     * @return new instance of Matrix as a result.
     * @throws IllegalArgumentException if number of columns and number of rows of <b>this</b> and <code>other</code>
     *                                  matrices aren't equal.
     */
    public Matrix multiply(Matrix other) {
        if (getColumnsCount() != other.getRowsCount()) {
            throw new IllegalArgumentException("Matrix::multiply(Matrix) matrices dimensions are invalid!");
        }
        double[][] resultData = new double[getRowsCount()][other.getColumnsCount()];
        for (int i = 0; i < getRowsCount(); i++) {
            for (int j = 0; j < other.getColumnsCount(); j++) {
                for (int k = 0; k < getColumnsCount(); k++) {
                    resultData[i][j] += data[i][k] * other.data[k][j];
                }
            }
        }
        return new Matrix(resultData);
    }

    /**
     * Transposes <b>this</b> matrix.
     *
     * @return <b>this</b> matrix.
     */
    public Matrix transpose() {
        double[][] transposedData = new double[getColumnsCount()][getRowsCount()];
        for (int j = 0; j < getColumnsCount(); j++) {
            for (int i = 0; i < getRowsCount(); i++) {
                transposedData[j][i] = data[i][j];
            }
        }
        return new Matrix(transposedData);
    }

    /**
     * Creates a new Matrix object and then applies {@link #transpose()} method.
     *
     * @return new instance of Matrix as a result.
     */
    public Matrix nTranspose() {
        return copy().transpose();
    }

    /**
     * Makes a copy of <b>this</b> matrix.
     *
     * @return copied matrix.
     */
    public Matrix copy() {
        return new Matrix(data);
    }

    /**
     * Performs forward substitution operation on provided matrix (vector) <code>b</code> whose number of rows should
     * be equal to dimensions of <b>this</b> quadratic matrix. Changes are applied to the <code>b</code>'s real
     * reference without copying.
     *
     * @param b vector b (n x 1).
     * @return result of substitution.
     * @throws RuntimeException         if <b>this</b> matrix isn't quadratic.
     * @throws IllegalArgumentException if dimensions of vector <code>b</code> don't fit.
     */
    public Matrix forwardSubs(Matrix b) {
        if (getRowsCount() != getColumnsCount()) {
            throw new RuntimeException("Matrix::forwardSubs(Matrix) cannot be performed on non quadratic matrix!");
        }
        if (b.getRowsCount() != getRowsCount()) {
            throw new IllegalArgumentException("Matrix::forwardSubs(Matrix) vectors b dimensions are invalid!");
        }
        for (int i = 0; i < getRowsCount() - 1; i++) {
            for (int j = i + 1; j < getRowsCount(); j++) {
                b.data[j][0] -= data[j][i] * b.data[i][0];
            }
        }
        return b;
    }

    /**
     * Performs backward substitution operation on provided matrix (vector) <code>y</code> whose number of rows should
     * be equal to dimensions of <b>this</b> quadratic matrix. Changes are applied to the <code>y</code>'s real
     * reference without copying.
     *
     * @param y vector y (n x 1).
     * @return result of substitution.
     * @throws RuntimeException         if <b>this</b> matrix isn't quadratic.
     * @throws IllegalArgumentException if dimensions of vector <code>y</code> don't fit.
     */
    public Matrix backwardSubs(Matrix y) {
        if (getRowsCount() != getColumnsCount()) {
            throw new RuntimeException("Matrix::backwardSubs(Matrix) cannot be performed on non quadratic matrix!");
        }
        if (y.getRowsCount() != getRowsCount()) {
            throw new IllegalArgumentException("Matrix::backwardSubs(Matrix) vectors y dimensions are invalid!");
        }
        for (int i = getRowsCount() - 1; i >= 0; i--) {
            for (int j = i + 1; j < getRowsCount(); j++) {
                y.data[i][0] -= data[i][j] * y.data[j][0];
            }
            y.data[i][0] /= data[i][i];
        }
        return y;
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
                if (Math.abs(data[i][j] - other.data[i][j]) > DELTA) {
                    return false;
                }
            }
        }
        return true;
    }

    /* ---------------------------------------------------------------- */
    /* ------------------------- PARSE METHODS ------------------------ */

    /**
     * Creates an instance of Matrix whose definition is parsed from provided <code>file</code>. Each line in a file
     * represents one row and in each row values are separated by spaces or tabs and they represents columns.
     *
     * @param file matrix definition.
     * @return Matrix as a parse result.
     * @throws Exception if something goes wrong or file if is invalid.
     */
    public static Matrix parseFromFile(String file) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(file));
        int rows = lines.size();
        double[][] data = new double[rows][];
        try {
            for (int i = 0; i < rows; i++) {
                // this will break if string value is not parsable into double value
                double[] column = Arrays.stream(lines.get(i).split("\\s+"))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                data[i] = column;
            }
            // check if column numbers are consistent
            if (Arrays.stream(data).map(d -> d.length).distinct().count() != 1) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException("Parsing '" + file + "' failed! Matrix definition is invalid!");
        }
        return new Matrix(data);
    }

    /* ---------------------------------------------------------------- */
    /* ------------------------- PRINT METHODS ------------------------ */

    /**
     * Prints provided <code>matrix</code> to <code>out</code> stream.
     *
     * @param matrix matrix.
     * @param out    outputs stream.
     */
    public static void printMatrix(Matrix matrix, PrintStream out) {
        for (double[] row : matrix.data) {
            String rowString = Arrays.stream(row)
                    .mapToObj(Double::toString)
                    .collect(Collectors.joining(" "));
            out.println(rowString);
        }
        out.flush();
    }

}
