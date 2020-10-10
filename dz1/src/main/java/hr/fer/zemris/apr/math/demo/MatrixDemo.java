package hr.fer.zemris.apr.math.demo;

import hr.fer.zemris.apr.math.IMatrix;
import hr.fer.zemris.apr.math.LUMatrixView;
import hr.fer.zemris.apr.math.Matrix;
import hr.fer.zemris.apr.math.MatrixUtils;

/**
 * @author dbrcina
 */
public class MatrixDemo {

    public static void main(String[] args) throws Exception {
//        IMatrix a = MatrixUtils.parseFromFile("a.txt");
//        IMatrix b = MatrixUtils.parseFromFile("b.txt");
//        IMatrix aAddB = MatrixUtils.parseFromFile("a+b.txt");
//        IMatrix aSubB = MatrixUtils.parseFromFile("a-b.txt");
//        IMatrix aBT = MatrixUtils.parseFromFile("abT.txt");
//
//        System.out.println("Add: " + a.nAdd(b).equals(aAddB));
//        System.out.println("Subtract: " + a.nSub(b).equals(aSubB));
//        System.out.println("Multiply: " + a.mul(b.transpose()).equals(aBT));

//        IMatrix L = new Matrix(new double[][]{
//                {1, 0, 0},
//                {1.0 / 3, 1, 0},
//                {0, 12.0 / 7, 1}
//        });
//        IMatrix U = new Matrix(new double[][]{
//                {6, 2, 10},
//                {0, 7.0 / 3, -10.0 / 3},
//                {0, 0, 54.0 / 7}
//        });
//        IMatrix b = new Matrix(new double[][]{
//                {2}, {3}, {4}
//        });
//        IMatrix y = MatrixUtils.fs(L, b);
//        MatrixUtils.printMatrix(y, System.out);
//        System.out.println();
//        IMatrix x = MatrixUtils.bs(U, y);
//        MatrixUtils.printMatrix(x, System.out);

        IMatrix LU = new Matrix(new double[][]{
                {6, 2, 10},
                {1.0 / 3, 7.0 / 3, -10.0 / 3},
                {0, 12.0 / 7, 54.0 / 7}
        });

        IMatrix L = new LUMatrixView(LU, true);
        IMatrix U = new LUMatrixView(LU, false);
        MatrixUtils.printMatrix(L, System.out);
        System.out.println();
        MatrixUtils.printMatrix(U, System.out);
    }

}
