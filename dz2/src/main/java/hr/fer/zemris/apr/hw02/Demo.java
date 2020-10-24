package hr.fer.zemris.apr.hw02;

import hr.fer.zemris.apr.hw02.function.AbstractFunction;
import hr.fer.zemris.apr.hw02.function.IFunction;
import hr.fer.zemris.apr.hw02.optimization.HookeJeeves;
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
        IFunction f = new AbstractFunction() {
            @Override
            public double value(double[] point) {
                testValuePointDimension(point, 1);
                return Math.pow(point[0] - 4, 2);
            }
        };

        IOptAlgorithm goldenRatio = IOptAlgorithmProvider.getInstance("GoldenRatio");
        //goldenRatio.setVerbose(true);
        goldenRatio.configure(RESOURCES_FOLDER + "task1/golden_ratio.properties");
        System.out.println(Arrays.toString(goldenRatio.run(f)));
        System.out.println(goldenRatio.numberOfIterations());

        f.resetNumOfCalls();

        HookeJeeves hj = (HookeJeeves) IOptAlgorithmProvider.getInstance("HookeJeeves");
        hj.configure(RESOURCES_FOLDER + "task1/hooke_jeeves.properties");
        System.out.println(Arrays.toString(hj.run(f)));
        System.out.println(hj.numberOfIterations());
    }

}
