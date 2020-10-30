package hr.fer.zemris.apr.hw02;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;
import hr.fer.zemris.apr.hw02.function.*;
import hr.fer.zemris.apr.hw02.optimization.HookeJeeves;
import hr.fer.zemris.apr.hw02.optimization.IOptAlgorithm;
import hr.fer.zemris.apr.hw02.optimization.IOptAlgorithmProvider;
import hr.fer.zemris.apr.hw02.optimization.Simplex;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

/**
 * Demo program that runs through all task problems.
 *
 * @author dbrcina
 */
public class Demo {

    private static final Properties PROPERTIES = new Properties();
    private static final String RESOURCES_FOLDER = "src/main/resources/";
    private static final Set<String> ALGORITHMS = Set.of("GoldenRatio", "CoordinateSearch", "Simplex", "HookeJeeves");
    private static final IFunction[] FUNCTIONS = {
            new F1(), new F2(), new F3(), new F4(), new F6()
    };
    private static final Random RAND = new Random();

    public static void main(String[] args) throws Exception {
        task1();
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println();
        task2();
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println();
        task3();
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println();
        task4();
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println();
        task5();
    }

    private static void task1() throws Exception {
        System.out.println("#### Starting task 1 ####");
        IFunction f = new AbstractFunction() {
            @Override
            public double value(IMatrix point) {
                testValuePointDimension(point, 1);
                return Math.pow(point.get(0, 0) - 3, 2);
            }
        };
        String configFile = RESOURCES_FOLDER + "task1/configuration.properties";
        PROPERTIES.load(Files.newInputStream(Paths.get(configFile)));
        for (String algorithm : ALGORITHMS) {
            IOptAlgorithm alg = IOptAlgorithmProvider.getInstance(algorithm);
            alg.configure(PROPERTIES);
            printAlgResults(alg, f);
            System.out.println("------------------------");
        }
    }


    private static void task2() throws Exception {
        System.out.println("#### Starting task 2 ####");
        task2("1", FUNCTIONS[0]);
        task2("2", FUNCTIONS[1]);
        task2("3", FUNCTIONS[2]);
        task2("4", FUNCTIONS[3]);
    }

    private static void task2(String funNum, IFunction function) throws Exception {
        System.out.println("Simulating function F" + funNum + ":");
        String configFile = RESOURCES_FOLDER + "task2/f" + funNum + "/configuration.properties";
        PROPERTIES.load(Files.newInputStream(Paths.get(configFile)));
        for (String algorithm : ALGORITHMS) {
            if (algorithm.equals("GoldenRatio")) continue;
            IOptAlgorithm alg = IOptAlgorithmProvider.getInstance(algorithm);
            alg.configure(PROPERTIES);
            printAlgResults(alg, function);
            System.out.println("------------------------");
        }
        if (!funNum.equals("4")) {
            System.out.println();
            System.out.println("####################################");
            System.out.println();
        }
    }

    private static void task3() throws Exception {
        System.out.println("#### Starting task 3 ####");
        String configFile = RESOURCES_FOLDER + "task3/configuration.properties";
        PROPERTIES.load(Files.newInputStream(Paths.get(configFile)));
        IOptAlgorithm alg = IOptAlgorithmProvider.getInstance("HookeJeeves");
        alg.configure(PROPERTIES);
        printAlgResults(alg, FUNCTIONS[3]);
        System.out.println("------------------------");
        alg = IOptAlgorithmProvider.getInstance("Simplex");
        alg.configure(PROPERTIES);
        printAlgResults(alg, FUNCTIONS[3]);
    }

    private static void task4() throws Exception {
        System.out.println("#### Starting task 4 ####");
        for (int i = 1; i <= 5; i++) {
            String configFile = RESOURCES_FOLDER + "task4/configuration" + i + ".properties";
            PROPERTIES.load(Files.newInputStream(Paths.get(configFile)));
            Simplex alg = (Simplex) IOptAlgorithmProvider.getInstance("Simplex");
            alg.configure(PROPERTIES);
            System.out.println("Initial point: " + Arrays.toString(alg.getInitialPoint().columnData(0)));
            System.out.println("Step: " + alg.getStep());
            printAlgResults(alg, FUNCTIONS[0]);
            if (i != 5) {
                System.out.println();
                System.out.println("####################################");
                System.out.println();
            }
        }
    }

    private static void task5() throws Exception {
        System.out.println("#### Starting task 5 ####");
        IOptAlgorithm alg = IOptAlgorithmProvider.getInstance("CoordinateSearch");
        double[] epsilons = {1e-6, 1e-6};
        IMatrix matrix = new Matrix(2, 1);
        alg.setEpsilons(matrix.set(0, 0, epsilons[0]).set(1, 0, epsilons[1]));
        if (alg instanceof HookeJeeves) {
            double[] deltas = {0.5, 0.5};
            ((HookeJeeves) alg).setDeltas(matrix.set(0, 0, deltas[0]).set(1, 0, deltas[1]));
        }
        int counter = 0;
        for (int i = 0; i < 100_000; i++) {
            double[] initialPoint = {-50 + RAND.nextDouble() * 100, -50 + RAND.nextDouble() * 100};
            alg.setInitialPoint(matrix.set(0, 0, initialPoint[0]).set(1, 0, initialPoint[1]));
            IMatrix solution = alg.run(FUNCTIONS[4]);
            double value = FUNCTIONS[4].value(solution);
            if (value < 1e-4) {
                counter++;
//                System.out.println("Iteracija " + (i + 1));
//                System.out.println("Solution: " + Arrays.toString(solution.columnData(0)));
//                System.out.println("Function evaluated: " + FUNCTIONS[4].evaluatedTimes());
            }
            FUNCTIONS[4].resetEvaluationCounter();
        }
        System.out.println(counter / 100_000.0 * 100);
    }

    private static void printAlgResults(IOptAlgorithm alg, IFunction function) {
        System.out.println("Algorithm: " + alg);
        double[] solution = alg.run(function).columnData(0);
        int evaluation = function.evaluatedTimes();
        System.out.println("Solution: " + Arrays.toString(solution));
        System.out.println("Function evaluated: " + evaluation);
        function.resetEvaluationCounter();
    }

}
