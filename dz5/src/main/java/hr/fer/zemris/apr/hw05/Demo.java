package hr.fer.zemris.apr.hw05;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.MatrixUtils;
import hr.fer.zemris.apr.hw05.numopt.Euler;
import hr.fer.zemris.apr.hw05.numopt.NumOptAlgorithm;
import hr.fer.zemris.apr.hw05.numopt.ReversedEuler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

/**
 * @author dbrcina
 */
public class Demo {

    private static final String RESOURCES_FOLDER = "src/main/resources/";
    private static final Properties CONFIGURATION = new Properties();
    private static final Map<String, NumOptAlgorithm> ALGORITHMS = Map.of(
            "euler", new Euler(),
            "reversed-euler", new ReversedEuler()
    );

    public static void main(String[] args) throws Exception {
        switch (args[0]) {
            case "task1" -> task1(args[1]);
        }
    }

    private static void task1(String algName) throws Exception {
        System.out.println("\t#### STARTING TASK 1! ####");
        IMatrix x = MatrixUtils.parseFromFile(RESOURCES_FOLDER + "task1/x.txt");
        IMatrix A = MatrixUtils.parseFromFile(RESOURCES_FOLDER + "task1/A.txt");
        IMatrix B = MatrixUtils.parseFromFile(RESOURCES_FOLDER + "task1/B.txt");
        List<Function<Double, Double>> rFunctions = List.of(t -> 0.0, t -> 0.0);
        List<Function<Double, Double>> analyticalFunctions = List.of(
                t -> x.get(0, 0) * Math.cos(t) + x.get(1, 0) * Math.sin(t),
                t -> x.get(1, 0) * Math.cos(t) - x.get(0, 0) * Math.sin(t)
        );
        CONFIGURATION.load(Files.newInputStream(Paths.get(RESOURCES_FOLDER + "task1/configuration.properties")));
        NumOptAlgorithm alg = ALGORITHMS.get(algName);
        System.out.println("Running " + algName.toUpperCase());
        alg.configure(CONFIGURATION);
        alg.run(x, A, B, rFunctions, analyticalFunctions);
    }
}
