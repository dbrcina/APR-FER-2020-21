package hr.fer.zemris.apr.hw02;

import hr.fer.zemris.apr.hw02.function.*;
import hr.fer.zemris.apr.hw02.optimization.IOptAlgorithm;
import hr.fer.zemris.apr.hw02.optimization.IOptAlgorithmProvider;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
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

    public static void main(String[] args) throws Exception {
        task1();
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println();
//        task2();
//        System.out.println();
//        System.out.println("-------------------------------------------");
//        System.out.println();
    }

    private static void task1() throws Exception {
        System.out.println("#### Starting task 1 ####");
        IFunction f = new AbstractFunction() {
            @Override
            public double value(double[] point) {
                testValuePointDimension(point, 1);
                return Math.pow(point[0] - 3, 2);
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
        task2("1", new F1(1, 100));
        task2("2", new F2(-4, 4, -2));
        task2("3", new F3());
        task2("4", new F4());
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

    private static void printAlgResults(IOptAlgorithm alg, IFunction function) {
        System.out.println("Algorithm: " + alg);
        double[] solution = alg.run(function);
        int evaluation = function.numOfCalls();
        System.out.println("Solution: " + Arrays.toString(solution));
        System.out.println("Function evaluated: " + evaluation);
        function.resetNumOfCalls();
    }

}
