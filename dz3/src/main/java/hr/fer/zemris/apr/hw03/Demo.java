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
import hr.fer.zemris.apr.hw03.optimization.constraint.EqualityConstraint;
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
        task4();
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println();
        task5();
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

    private static void task4() throws Exception {
        System.out.println("\t\t####### Starting task 4 #######");
        System.out.println();
        task4("1", FUNCTIONS[0]);
        System.out.println("-------------------------------------------");
        task4("2", FUNCTIONS[1]);
    }

    private static void task4(String funNum, IFunction function) throws Exception {
        System.out.println("# Simulating function F" + funNum + " #");
        String configFile = RESOURCES_FOLDER + "task4/f" + funNum + "/configuration.properties";
        PROPERTIES.load(Files.newInputStream(Paths.get(configFile)));
        IOptAlgorithm mu = OptAlgorithmProvider.getInstance("MixedUnconstrained");
        mu.configure(PROPERTIES);
        ExplicitConstraint[] explicitConstraints = {
                new ExplicitConstraint(0, -100, 100),
                new ExplicitConstraint(1, -100, 100)
        };
        ImplicitConstraint[] implicitConstraints = {
                new InequalityConstraint(point -> point.get(1, 0) - point.get(0, 0)),
                new InequalityConstraint(point -> 2 - point.get(0, 0))
        };
        ArgsConstraints constraints = new ArgsConstraints(explicitConstraints, implicitConstraints);
        mu.setConstraints(constraints);
        printAlgResults(mu, function);
    }

    private static void task5() throws Exception {
        System.out.println("\t\t####### Starting task 5 #######");
        System.out.println();
        String configFile = RESOURCES_FOLDER + "task5/configuration.properties";
        PROPERTIES.load(Files.newInputStream(Paths.get(configFile)));
        IOptAlgorithm mu = OptAlgorithmProvider.getInstance("MixedUnconstrained");
        mu.configure(PROPERTIES);
        ImplicitConstraint[] implicitConstraints = {
                new InequalityConstraint(point -> 3 - point.get(0, 0) - point.get(1, 0)),
                new InequalityConstraint(point -> 3 + 1.5 * point.get(0, 0) - point.get(1, 0)),
                new EqualityConstraint(point -> point.get(1, 0) - 1)
        };
        ArgsConstraints constraints = new ArgsConstraints(null, implicitConstraints);
        mu.setConstraints(constraints);
        printAlgResults(mu, FUNCTIONS[3]);
    }

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
