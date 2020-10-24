package hr.fer.zemris.apr.hw02;

import hr.fer.zemris.apr.hw02.optimization.GoldenRatio;
import hr.fer.zemris.apr.hw02.optimization.OptimizationAlgorithm;

/**
 * Demo program that runs through all task problems.
 *
 * @author dbrcina
 */
public class Demo {

    private static final String RESOURCES_FOLDER = "src/main/resources/";

    public static void main(String[] args) throws Exception {
        OptimizationAlgorithm goldenRatio = new GoldenRatio();
        goldenRatio.configure(RESOURCES_FOLDER + "task1/golden_ratio.properties");
    }

}
