package hr.fer.zemris.apr.hw05;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.MatrixUtils;
import hr.fer.zemris.apr.hw05.numopt.*;

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
            "reversed-euler", new ReversedEuler(),
            "trapezoid", new Trapezoid(),
            "runge-kutta", new RungeKutta(),
            "pece", new PECE(1),
            "pece2", new PECE(2)
    );

    public static void main(String[] args) throws Exception {
        switch (args[0]) {
            case "task1" -> task("1", args[1], List.of(t -> 0.0, t -> 0.0));
            case "task2" -> task("2", args[1], List.of(t -> 0.0, t -> 0.0));
        }
    }

    private static void task(
            String taskNum,
            String algName,
            List<Function<Double, Double>> rFunctions) throws Exception {
        System.out.println("\t#### STARTING TASK " + taskNum + "! ####");
        IMatrix x = MatrixUtils.parseFromFile(RESOURCES_FOLDER + "task" + taskNum + "/x.txt");
        IMatrix A = MatrixUtils.parseFromFile(RESOURCES_FOLDER + "task" + taskNum + "/A.txt");
        IMatrix B = MatrixUtils.parseFromFile(RESOURCES_FOLDER + "task" + taskNum + "/B.txt");
        List<Function<Double, Double>> analyticalFunctions = null;
        if (taskNum.equals("1")) {
            analyticalFunctions = List.of(
                    t -> x.get(0, 0) * Math.cos(t) + x.get(1, 0) * Math.sin(t),
                    t -> x.get(1, 0) * Math.cos(t) - x.get(0, 0) * Math.sin(t)
            );
        }
        CONFIGURATION.load(Files.newInputStream(
                Paths.get(RESOURCES_FOLDER + "task" + taskNum + "/configuration.properties"))
        );
        NumOptAlgorithm alg = ALGORITHMS.get(algName);
        System.out.println("Running " + algName.toUpperCase());
        alg.configure(CONFIGURATION);
        alg.run(x, A, B, rFunctions, analyticalFunctions);
    }

}
