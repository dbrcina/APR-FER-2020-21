package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.AbstractFunction;
import hr.fer.zemris.apr.hw02.function.IFunction;

import java.util.Arrays;
import java.util.Properties;

/**
 * An implementation of <i>Coordinate search</i> optimization algorithm.
 *
 * @author dbrcina
 * @see IOptAlgorithm
 */
public class CoordinateSearch extends AbstractOptAlgorithm {

    /**
     * @see AbstractOptAlgorithm#AbstractOptAlgorithm()
     */
    protected CoordinateSearch() {
    }

    @Override
    public void configure(Properties properties) throws Exception {
        try {
            super.configure(properties);
        } catch (NumberFormatException | ConfigInvalidException e) {
            handleConfigureExceptions(e);
        }
    }

    @Override
    public double[] run(IFunction function) {
        super.run(function);
        double[] x0 = getInitialPoint();
        double[] x = Arrays.copyOf(x0, x0.length);
        double[] subXXS = new double[x.length];
        GoldenRatio gr = null;
        try {
            gr = (GoldenRatio) IOptAlgorithmProvider.getInstance("GoldenRatio");
            if (!gr.isConfigured()) {
                gr.setInitialPoint(getInitialPoint());
                gr.setEpsilons(getEpsilons());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exiting...");
            System.exit(-1);
        }

        do {
            incrementIterations(1);
            double[] xS = Arrays.copyOf(x, x.length);
            for (int i = 0; i < x.length; i++) {
                int ithId = i;
                IFunction f = new AbstractFunction() {
                    @Override // Here point is an array with the length of 1, which represent lambda
                    public double value(double[] point) {
                        testValuePointDimension(point, 1);
                        double[] xTemp = Arrays.copyOf(x, x.length);
                        xTemp[ithId] += point[0] * 1; // vec_x + lambda * vec_ith_id
                        return function.value(xTemp);
                    }
                };
                double lambdaMin = gr.run(f)[0];
                x[ithId] += lambdaMin * 1;
                incrementIterations(gr.numberOfIterations());
            }
            for (int i = 0; i < x.length; i++) {
                subXXS[i] = x[i] - xS[i];
            }
        } while (!vectorLEQEpsilons(subXXS));

        return x;
    }

}
