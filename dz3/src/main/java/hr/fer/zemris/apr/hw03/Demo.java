package hr.fer.zemris.apr.hw03;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw03.function.F1;
import hr.fer.zemris.apr.hw03.function.F2;
import hr.fer.zemris.apr.hw03.function.F3;
import hr.fer.zemris.apr.hw03.function.F4;
import hr.fer.zemris.apr.hw03.function.IFunction;
import hr.fer.zemris.apr.hw03.optimization.IOptAlgorithm;
import hr.fer.zemris.apr.hw03.optimization.OptAlgorithmProvider;
import hr.fer.zemris.apr.hw03.optimization.constraint.ArgsConstraints;
import hr.fer.zemris.apr.hw03.optimization.constraint.ExplicitConstraint;
import hr.fer.zemris.apr.hw03.optimization.constraint.ImplicitConstraint;
import hr.fer.zemris.apr.hw03.optimization.constraint.InequalityConstraint;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

/**
 * Demo program that runs through all task problems.
 *
 * @author dbrcina
 */
public class Demo {

    private static final Properties PROPERTIES = new Properties();
    private static final String RESOURCES_FOLDER = "src/main/resources/";
    private static final IFunction[] FUNCTIONS = {new F1(), new F2(), new F3(), new F4()};

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
//        task4();
//        System.out.println();
//        System.out.println("-------------------------------------------");
//        System.out.println();
//        task5();
    }

    private static void task1() throws Exception {
        System.out.println("\t\t####### Starting task 1 #######");
        System.out.println();
        String configFile = RESOURCES_FOLDER + "task1/configuration.properties";
        PROPERTIES.load(Files.newInputStream(Paths.get(configFile)));
        IOptAlgorithm gd = OptAlgorithmProvider.getInstance("GradientDescent");
        gd.configure(PROPERTIES);
        printAlgResults(gd, FUNCTIONS[2]);
    }

    private static void task2() throws Exception {
        System.out.println("\t\t####### Starting task 2 #######");
        System.out.println();
        task2("1", FUNCTIONS[0]);
        System.out.println("-------------------------------------------");
        task2("2", FUNCTIONS[1]);
    }

    private static void task2(String funNum, IFunction function) throws Exception {
        System.out.println("# Simulating function F" + funNum + " #");
        String configFile = RESOURCES_FOLDER + "task2/f" + funNum + "/configuration.properties";
        PROPERTIES.load(Files.newInputStream(Paths.get(configFile)));
        IOptAlgorithm gd = OptAlgorithmProvider.getInstance("GradientDescent");
        gd.configure(PROPERTIES);
        printAlgResults(gd, function);
        System.out.println();
        IOptAlgorithm nr = OptAlgorithmProvider.getInstance("NewtonRaphson");
        nr.configure(PROPERTIES);
        printAlgResults(nr, function);
    }

    private static void task3() throws Exception {
        System.out.println("\t\t####### Starting task 3 #######");
        System.out.println();
        task3("1", FUNCTIONS[0]);
        System.out.println("-------------------------------------------");
        task3("2", FUNCTIONS[1]);
    }

    private static void task3(String funNum, IFunction function) throws Exception {
        System.out.println("# Simulating function F" + funNum + " #");
        String configFile = RESOURCES_FOLDER + "task3/f" + funNum + "/configuration.properties";
        PROPERTIES.load(Files.newInputStream(Paths.get(configFile)));
        IOptAlgorithm box = OptAlgorithmProvider.getInstance("Box");
        box.configure(PROPERTIES);
        ExplicitConstraint[] explicitConstraints = {
                new ExplicitConstraint(0, -100, 100),
                new ExplicitConstraint(1, -100, 100)
        };
        ImplicitConstraint[] implicitConstraints = {
                new InequalityConstraint(point -> point.get(1, 0) - point.get(0, 0)),
                new InequalityConstraint(point -> 2 - point.get(0, 0))
        };
        ArgsConstraints constraints = new ArgsConstraints(explicitConstraints, implicitConstraints);
        box.setConstraints(constraints);
        printAlgResults(box, function);
    }

    //    private static void task4() throws Exception {
//        System.out.println("#### Starting task 4 ####");
//        task4("1");
//        System.out.println();
//        task4("2");
//    }
//
//    private static void task4(String configNum) throws Exception {
//        String configFile = RESOURCES_FOLDER + "task4/configuration" + configNum + ".properties";
//        PROPERTIES.load(Files.newInputStream(Paths.get(configFile)));
//        Simplex alg = (Simplex) OptAlgorithmProvider.getInstance("Simplex");
//        alg.configure(PROPERTIES);
//        double[] initialPoint = alg.getInitialPoint().columnData(0);
//        System.out.println("Initial point: " + Arrays.toString(initialPoint));
//        System.out.println("Step  Evaluations    Solution");
//        for (int i = 1; i <= 20; i++) {
//            alg.setStep(i);
//            double[] solution = alg.run(FUNCTIONS[0]).columnData(0);
//            int evaluation = FUNCTIONS[0].evaluatedTimes();
//            FUNCTIONS[0].resetEvaluationCounter();
//            System.out.printf("%2d%s%4d%s%s%n", i, " ".repeat(5), evaluation, " ".repeat(5), Arrays.toString(solution));
//        }
//    }
//
//    private static void task5() throws Exception {
//        System.out.println("#### Starting task 5 ####");
//        IOptAlgorithm alg = OptAlgorithmProvider.getInstance("Simplex");
//        double[] epsilons = {1e-6, 1e-6};
//        IMatrix matrix = new Matrix(2, 1);
//        alg.setEpsilons(matrix.set(0, 0, epsilons[0]).set(1, 0, epsilons[1]));
//        if (alg instanceof HookeJeeves) {
//            double[] deltas = {0.5, 0.5};
//            ((HookeJeeves) alg).setDeltas(matrix.set(0, 0, deltas[0]).set(1, 0, deltas[1]).copy());
//        }
//        int counter = 0;
//        for (int i = 0; i < 100_000; i++) {
//            double[] initialPoint = {-50 + RAND.nextDouble() * 100, -50 + RAND.nextDouble() * 100};
//            alg.setInitialPoint(matrix.set(0, 0, initialPoint[0]).set(1, 0, initialPoint[1]));
//            IMatrix solution = alg.run(FUNCTIONS[4]);
//            double value = FUNCTIONS[4].value(solution);
//            if (value < 1e-4) {
//                counter++;
//                System.out.println("Initial point: " + Arrays.toString(initialPoint));
//                System.out.println("Solution: " + Arrays.toString(solution.columnData(0)));
//                System.out.println("Function evaluated: " + FUNCTIONS[4].evaluatedTimes());
//                System.out.println("------------------------");
//            }
//            FUNCTIONS[4].resetEvaluationCounter();
//        }
//        System.out.println("p = " + counter / 100_000.0 * 100);
//    }
//
    private static void printAlgResults(IOptAlgorithm alg, IFunction function) {
        System.out.println("Algorithm: " + alg);
        IMatrix solution = alg.run(function);
        int evaluatedTimes = function.evaluatedTimes();
        int gradientEvaluatedTimes = function.gradientEvaluatedTimes();
        int hesseEvaluatedTimes = function.hesseEvaluatedTimes();
        System.out.println("Minimizer                         = " + Arrays.toString(solution.columnData(0)));
        System.out.println("Minimum                           = " + function.value(solution));
        System.out.println("Function evaluated                = " + evaluatedTimes);
        if (gradientEvaluatedTimes != 0) {
            System.out.println("Function's gradient evaluated     = " + gradientEvaluatedTimes);
            function.resetGradientEvaluationCounter();
        }
        if (hesseEvaluatedTimes != 0) {
            System.out.println("Function's Hesse matrix evaluated = " + hesseEvaluatedTimes);
            function.resetHesseEvaluationCounter();
        }
        function.resetEvaluationCounter();
    }

}
