package hr.fer.zemris.apr.hw02;

import hr.fer.zemris.apr.hw02.function.AbstractFunction;
import hr.fer.zemris.apr.hw02.function.IFunction;
import hr.fer.zemris.apr.hw02.optimization.IOptAlgorithm;
import hr.fer.zemris.apr.hw02.optimization.IOptAlgorithmProvider;

import java.util.Arrays;

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
        System.out.println("-------------------------------------------");
        System.out.println();
    }

    private static void task1() throws Exception {
        System.out.println("Starting task 1...");
        IFunction f = new AbstractFunction() {
            @Override
            public double value(double[] point) {
                testValuePointDimension(point, 1);
                return Math.pow(point[0] - 3, 2);
            }
        };
        IOptAlgorithm gr = IOptAlgorithmProvider.getInstance("GoldenRatio");
        gr.configure(RESOURCES_FOLDER + "task1/golden_ratio.properties");
        double[] solution = gr.run(f);
        System.out.println("Solution: " + Arrays.toString(solution));
        System.out.println("Function evaluated " + f.numOfCalls() + " times!");
        System.out.println("------------------------");

        f.resetNumOfCalls();

        IOptAlgorithm cs = IOptAlgorithmProvider.getInstance("CoordinateSearch");
        cs.configure(RESOURCES_FOLDER + "task1/coordinate_search.properties");
        solution = cs.run(f);
        System.out.println("Solution: " + Arrays.toString(solution));
        System.out.println("Function evaluated " + f.numOfCalls() + " times!");
        System.out.println("------------------------");

        f.resetNumOfCalls();

        IOptAlgorithm hj = IOptAlgorithmProvider.getInstance("HookeJeeves");
        hj.configure(RESOURCES_FOLDER + "task1/hooke_jeeves.properties");
        solution = hj.run(f);
        System.out.println("Solution: " + Arrays.toString(solution));
        System.out.println("Function evaluated " + f.numOfCalls() + " times!");
        System.out.println("------------------------");

        f.resetNumOfCalls();
    }

}
