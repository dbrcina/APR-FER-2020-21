package hr.fer.zemris.apr.hw01.math;

/**
 * An implementation of LU matrix view that is used in <b>LU(P) Decomposition</b>.
 *
 * @author dbrcina
 */
public class LUMatrixView extends AbstractMatrix {

    private final IMatrix matrix;
    private final boolean matrixL;

    public LUMatrixView(IMatrix matrix, boolean matrixL) {
        this.matrix = matrix;
        this.matrixL = matrixL;
    }

    @Override
    public int getRowsCount() {
        return matrix.getRowsCount();
    }

    @Override
    public int getColumnsCount() {
        return matrix.getColumnsCount();
    }

    @Override
    public double get(int row, int column) {
        testOutOfBounds(row, column, "get(int,int)");
        if (matrixL) { // handle get for matrix L
            if (row == column) return 1;
            if (row > column) return matrix.get(row, column);
        } else { // handle get for matrix U
            if (column >= row) return matrix.get(row, column);
        }
        return 0;
    }

    @Override
    public IMatrix set(int row, int column, double value) {
        throw new RuntimeException("LUMatrixView::set(int,int,double) is not supported!");
    }

    @Override
    public IMatrix setRow(int row, double[] data) {
        throw new RuntimeException("LUMatrixView::setRow(int,double[]) is not supported!");
    }

    @Override
    public IMatrix setColumn(int column, double[] data) {
        throw new RuntimeException("LUMatrixView::setColumn(int,double[]) is not supported!");
    }

    /* The determinant of triangular matrices is equal to the product of the elements on the diagonals. */
    @Override
    public double determinant() {
        testSquareMatrix("determinant()");
        if (matrixL) return 1;
        double d = 1;
        for (int i = 0; i < getRowsCount(); i++) {
            d *= matrix.get(i, i);
        }
        return d;
    }

    @Override
    public IMatrix swapRows(int r1, int r2) {
        throw new RuntimeException("LUMatrixView::swapRows(int,int) is not supported!");
    }

    @Override
    public IMatrix copy() {
        return new LUMatrixView(matrix, matrixL);
    }

    @Override
    public IMatrix newInstance(int rows, int columns) {
        throw new RuntimeException("LUMatrixView::newInstance(int,int) is not supported!");
    }

    @Override
    public double[] rowData(int row) {
        return matrix.rowData(row);
    }

}
