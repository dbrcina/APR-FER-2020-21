package hr.fer.zemris.apr.hw01;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.MatrixUtils;

/**
 * Demo program that runs through all task problems.
 *
 * @author dbrcina
 */
public class Demo {

    private static final String RESOURCES_FOLDER = "src/main/resources/";

    public static void main(String[] args) throws Exception {
        task1();
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasks2To6("02", "02-A.txt", "02-b.txt");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasks2To6("03", "03-A.txt", "03-b.txt");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasks2To6("04", "04-A.txt", "04-b.txt");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasks2To6("05", "05-A.txt", "05-b.txt");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasks2To6("06", "06-A.txt", "06-b.txt");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasks7To10("07", "07-A.txt", true);
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasks7To10("08", "08-A.txt", true);
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasks7To10("09", "09-A.txt", false);
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasks7To10("10", "10-A.txt", false);
    }

    private static void task1() throws Exception {
        System.out.println("Starting task 01!");
        IMatrix A = MatrixUtils.parseFromFile(RESOURCES_FOLDER + "02-A.txt");
        System.out.println("-> \033[1mA\033[0m =");
        MatrixUtils.printMatrix(A, System.out);
        double scalar = 5.6789;
        System.out.println("Multiplying and dividing with " + scalar + ":");
        IMatrix A2 = A.nScalarMul(scalar).scalarMul(1 / scalar);
        System.out.println("-> \033[1mA2\033[0m =");
        MatrixUtils.printMatrix(A2, System.out);
        System.out.println("Comparing with '==':");
        System.out.println("\033[1mA\033[0m == \033[1mA2\033[0m? " + greedyEquals(A, A2));
        System.out.println("Comparing with equals() method:");
        System.out.println("\033[1mA\033[0m == \033[1mA2\033[0m? " + A.equals(A2));
    }

    private static boolean greedyEquals(IMatrix A, IMatrix A2) {
        for (int i = 0; i < A.getRowsCount(); i++) {
            for (int j = 0; j < A.getColumnsCount(); j++) {
                if (A.get(i, j) != A2.get(i, j)) return false;
            }
        }
        return true;
    }

    private static void tasks2To6(String taskNum, String matrixATxt, String matrixBTxt) throws Exception {
        System.out.println("Starting task " + taskNum + "!");
        IMatrix A = MatrixUtils.parseFromFile(RESOURCES_FOLDER + matrixATxt);
        IMatrix b = MatrixUtils.parseFromFile(RESOURCES_FOLDER + matrixBTxt);
        System.out.println("-> \033[1mA\033[0m =");
        MatrixUtils.printMatrix(A, System.out);
        System.out.println("-> \033[1mb\033[0m =");
        MatrixUtils.printMatrix(b, System.out);
        System.out.println();
        try {
            System.out.println("LU Decomposition...");
            IMatrix[] lu = MatrixUtils.luDecomposition(A.copy(), false);
            System.out.println("-> \033[1mL\033[0m =");
            MatrixUtils.printMatrix(lu[0], System.out);
            System.out.println("-> \033[1mU\033[0m =");
            MatrixUtils.printMatrix(lu[1], System.out);
            IMatrix x = MatrixUtils.bs(lu[1], MatrixUtils.fs(lu[0], b.copy()));
            System.out.println("-> \033[1mx\033[0m =");
            MatrixUtils.printMatrix(x, System.out);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Problem cannot be solved with LU!");
        }
        System.out.println();
        try {
            System.out.println("LUP Decomposition...");
            IMatrix[] lup = MatrixUtils.luDecomposition(A.copy(), true);
            System.out.println("-> \033[1mL\033[0m =");
            MatrixUtils.printMatrix(lup[0], System.out);
            System.out.println("-> \033[1mU\033[0m =");
            MatrixUtils.printMatrix(lup[1], System.out);
            IMatrix x = MatrixUtils.bs(lup[1], MatrixUtils.fs(lup[0], lup[2].mul(b.copy())));
            System.out.println("-> \033[1mx\033[0m =");
            MatrixUtils.printMatrix(x, System.out);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Problem cannot be solved with LUP!");
        }
    }

    private static void tasks7To10(String taskNum, String matrixATxt, boolean inv) throws Exception {
        System.out.println("Starting task " + taskNum + "!");
        IMatrix A = MatrixUtils.parseFromFile(RESOURCES_FOLDER + matrixATxt);
        System.out.println("-> \033[1mA\033[0m =");
        MatrixUtils.printMatrix(A, System.out);
        System.out.println();
        if (inv) {
            try {
                System.out.println("LUP Decomposition inverse...");
                IMatrix inverse = MatrixUtils.lupInvert(A);
                MatrixUtils.printMatrix(inverse, System.out);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                System.out.println("LUP Decomposition determinant...");
                IMatrix[] lup = MatrixUtils.luDecomposition(A, true);
                double determinant = MatrixUtils.lupDeterminant(
                        lup[2].getNumOfRowsSwapped(), lup[0], lup[1]);
                System.out.println(determinant);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
