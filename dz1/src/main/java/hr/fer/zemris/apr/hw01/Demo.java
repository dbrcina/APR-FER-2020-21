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
        tasksZeroToSix("02", "02-A.txt", "02-b.txt");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasksZeroToSix("03", "03-A.txt", "03-b.txt");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasksZeroToSix("04", "04-A.txt", "04-b.txt");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasksZeroToSix("05", "05-A.txt", "05-b.txt");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasksZeroToSix("06", "06-A.txt", "06-b.txt");
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasksSevenToTen("07", "07-A.txt", true);
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasksSevenToTen("08", "08-A.txt", true);
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasksSevenToTen("09", "09-A.txt", false);
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
        tasksSevenToTen("10", "10-A.txt", false);
    }

    private static void tasksZeroToSix(String taskNum, String matrixATxt, String matrixBTxt) throws Exception {
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

    private static void tasksSevenToTen(String taskNum, String matrixATxt, boolean inv) throws Exception {
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
