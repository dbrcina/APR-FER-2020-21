package hr.fer.zemris.apr.math.demo;

import hr.fer.zemris.apr.math.IMatrix;
import hr.fer.zemris.apr.math.Matrix;
import hr.fer.zemris.apr.math.MatrixUtils;

/**
 * @author dbrcina
 */
public class MatrixDemo {

    public static void main(String[] args) throws Exception {
        IMatrix A = new Matrix(new double[][]{
                {1, 1, 1},
                {1, 1, 3},
                {1, 3, 3}
        });
        IMatrix[] results = MatrixUtils.luDecomposition(A, true);
        MatrixUtils.printMatrix(results[0], System.out);
        System.out.println();
        MatrixUtils.printMatrix(results[1], System.out);
        System.out.println();
        System.out.println(results[2].getNumOfRowsSwapped());
    }

}
