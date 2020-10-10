package hr.fer.zemris.apr.math.demo;

import hr.fer.zemris.apr.math.Matrix;

/**
 * @author dbrcina
 */
public class MatrixDemo {

    public static void main(String[] args) throws Exception {
//        Matrix a = Matrix.parseFromFile("a.txt");
//        Matrix b = Matrix.parseFromFile("b.txt");
//        Matrix aAddB = Matrix.parseFromFile("a+b.txt");
//        Matrix aSubB = Matrix.parseFromFile("a-b.txt");
//        Matrix aBT = Matrix.parseFromFile("abT.txt");
//
//        System.out.println("Add: " + a.nAdd(b).equals(aAddB));
//        System.out.println("Subtract: " + a.nSub(b).equals(aSubB));
//        System.out.println("Multiply: " + a.multiply(b.transpose()).equals(aBT));

        Matrix L = new Matrix(new double[][]{
                {1, 0, 0},
                {1.0 / 3, 1, 0},
                {0, 12.0 / 7, 1}
        });
        Matrix U = new Matrix(new double[][]{
                {6, 2, 10},
                {0, 7.0 / 3, -10.0 / 3},
                {0, 0, 54.0 / 7}
        });
        Matrix b = new Matrix(new double[][]{
                {2}, {3}, {4}
        });
        Matrix y = L.forwardSubs(b);
        Matrix.printMatrix(y, System.out);
        System.out.println();
        Matrix x = U.backwardSubs(y);
        Matrix.printMatrix(x, System.out);
    }

}
