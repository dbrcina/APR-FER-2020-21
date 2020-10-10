package hr.fer.zemris.apr.math;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for matrix manipulation.
 *
 * @author dbrcina
 */
public class MatrixUtils {

    /* ---------------------------------------------------------------- */
    /* ------------------------- PARSE METHODS ------------------------ */

    /**
     * Creates an instance of IMatrix whose definition is parsed from provided <code>file</code>. Each line in a file
     * represents one row and in each row values are separated by spaces or tabs and they represents columns.
     *
     * @param file matrix definition.
     * @return IMatrix as a parse result.
     * @throws Exception if something goes wrong or file definition if is invalid.
     */
    public static IMatrix parseFromFile(String file) throws Exception {
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
    public static void printMatrix(IMatrix matrix, PrintStream out) {
        for (int i = 0; i < matrix.getRowsCount(); i++) {
            double[] row = new double[matrix.getColumnsCount()];
            for (int j = 0; j < matrix.getColumnsCount(); j++) {
                row[j] = matrix.get(i, j);
            }
            String rowString = Arrays.stream(row)
                    .mapToObj(Double::toString)
                    .collect(Collectors.joining(" "));
            out.println(rowString);
        }
        out.flush();
    }

    /* ---------------------------------------------------------------- */
    /* --------------------- DECOMPOSITION METHODS -------------------- */

    /**
     * Performs forward substitution operation on provided matrix <code>L</code> and matrix (vector) <code>b</code>,
     * whose number of rows  should be equal to the dimensions of quadratic matrix <code>L</code>. Changes are applied
     * to the <code>b</code>'s real reference without copying.
     *
     * @param L matrix L.
     * @param b vector b (n x 1).
     * @return IMatrix as a result of substitution.
     * @throws RuntimeException         if matrix <code>L</code> isn't quadratic matrix.
     * @throws IllegalArgumentException if dimensions of vector <code>b</code> don't fit.
     */
    public static IMatrix fs(IMatrix L, IMatrix b) {
        if (L.getRowsCount() != L.getColumnsCount()) {
            throw new RuntimeException("MatrixUtils::fs(IMatrix,IMatrix) cannot be performed on non quadratic matrix!");
        }
        if (b.getRowsCount() != L.getRowsCount()) {
            throw new IllegalArgumentException("MatrixUtils::fs(IMatrix,IMatrix) vectors b dimensions are invalid!");
        }
        for (int i = 0; i < L.getRowsCount() - 1; i++) {
            for (int j = i + 1; j < L.getRowsCount(); j++) {
                b.set(j, 0, b.get(j, 0) - L.get(j, i) * b.get(i, 0));
            }
        }
        return b;
    }

    /**
     * Performs backward substitution operation on provided matrix <code>U</code> and matrix (vector) <code>y</code>,
     * whose number of rows should be equal to the dimensions of quadratic matrix <code>U</code>. Changes are applied to
     * the <code>y</code>'s real reference without copying.
     *
     * @param U matrix U.
     * @param y vector y (n x 1).
     * @return IMatrix as a result of substitution.
     * @throws RuntimeException         if matrix <code>U</code> isn't quadratic matrix.
     * @throws IllegalArgumentException if dimensions of vector <code>y</code> don't fit.
     */
    public static IMatrix bs(IMatrix U, IMatrix y) {
        if (U.getRowsCount() != U.getColumnsCount()) {
            throw new RuntimeException("MatrixUtils::bs(IMatrix,IMatrix) cannot be performed on non quadratic matrix!");
        }
        if (y.getRowsCount() != U.getRowsCount()) {
            throw new IllegalArgumentException("MatrixUtils::bs(IMatrix,IMatrix) vectors y dimensions are invalid!");
        }
        for (int i = U.getRowsCount() - 1; i >= 0; i--) {
            for (int j = i + 1; j < U.getRowsCount(); j++) {
                y.set(i, 0, y.get(i, 0) - U.get(i, j) * y.get(j, 0));
            }
            y.set(i, 0, y.get(i, 0) / U.get(i, i));
        }
        return y;
    }

    /**
     * Based on provided boolean flag <code>useLUP</code>, method performs <b>LUP Decomposition</b> if flag is set to
     * <code>true</code>, otherwise it performs <b>LU Decomposition.</b> <b>LUP Decomposition</b> is <b>LU
     * Decomposition</b> + partial pivoting by columns. <br>
     * <b>L</b>, <b>U</b> and <b>P</b> (only if LUP method is used) matrices can be accessed through certain getters
     * and every operation is performed on <b>this</b> matrix.
     *
     * @param useLUP boolean flag.
     */
    public static void luDecomposition(boolean useLUP) {
    }

}
