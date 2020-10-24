package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.IFunction;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.function.BiFunction;

/**
 * An implementation of <i>Golden ratio</i> optimization algorithm. It is modeled as a singleton object and its
 * instance can be obtained by {@link #getInstance(boolean)} method.
 *
 * @author dbrcina
 */
public class GoldenRatio extends AbstractOptAlgorithm {

    private static GoldenRatio instance;

    /* Golden ration constant. */
    private static final double K = 0.5 * (Math.sqrt(5) - 1);
    /* BiFunction used for calculating c value in golden ration algorithm. */
    private static final BiFunction<Double, Double, Double> C = (a, b) -> b - K * (b - a);
    /* BiFunction used for calculating d value in golden ration algorithm. */
    private static final BiFunction<Double, Double, Double> D = (a, b) -> a + K * (b - a);

    /* Unimodal interval used in golden ratio algorithm. */
    private double[] interval;
    /* Flag used for printing results in every iteration of algorithm. */
    private boolean verbose;

    /* Constructor is private because of singleton design pattern. */
    private GoldenRatio() {
    }

    /**
     * @param verbose verbose flag used for printing results in every iteration of algorithm.
     * @return an instance of GoldenRatio class.
     */
    public static GoldenRatio getInstance(boolean verbose) {
        if (instance == null) {
            instance = new GoldenRatio();
        }
        instance.verbose = verbose;
        return instance;
    }

    @Override
    public void configure(String configFile) throws Exception {
        try {
            super.configure(configFile);
            interval = null;
            // Config file is already loaded in super class.
            Properties properties = PropertiesProvider.getProperties();
            Object intervalObj = properties.get("interval");
            if (intervalObj != null) {
                interval = Arrays.stream(((String) intervalObj).split("\\s+"))
                        .mapToDouble(Double::parseDouble)
                        .sorted()
                        .toArray();
            }
        } catch (NumberFormatException | ConfigFileException e) {
            handleConfigureExceptions(configFile, e);
        }
    }

    @Override
    public double[] run(IFunction function) {
        System.out.println("Running Golden Ratio optimization algorithm:");
        if (interval == null) {
            // Initial point is an array with the length of 1.
            interval = UnimodalInterval.create(function, getInitialPoint()[0]);
        }
        double a = interval[0];
        double b = interval[1];
        double c = C.apply(a, b);
        double d = D.apply(a, b);
        double fa = function.value(new double[]{a});
        double fb = function.value(new double[]{b});
        double fc = function.value(new double[]{c});
        double fd = function.value(new double[]{d});
        // Epsilons is an array with the length of 1.
        double epsilon = getEpsilons()[0];
        int i = 1;
        while ((b - a) > epsilon) {
            if (verbose) {
                printResults(i++, a, b, c, d, fa, fb, fc, fd);
            }
            if (fc < fd) {
                b = d;
                d = c;
                c = C.apply(a, b);
                fd = fc;
                fc = function.value(new double[]{c});
                fb = function.value(new double[]{b});
            } else {
                a = c;
                c = d;
                d = D.apply(a, b);
                fc = fd;
                fd = function.value(new double[]{d});
                fa = function.value(new double[]{a});
            }
        }
        return new double[]{(a + b) / 2};
    }

    /**
     * Prints results for i-th iteration.
     */
    private void printResults(
            int i, double a, double b, double c, double d, double fa, double fb, double fc, double fd) {
        System.out.println("Iteration " + i + ":");
        System.out.printf(Locale.US, "a = %f, f(a) = %f%n", a, fa);
        System.out.printf(Locale.US, "c = %f, f(c) = %f%n", c, fc);
        System.out.printf(Locale.US, "d = %f, f(d) = %f%n", d, fd);
        System.out.printf(Locale.US, "b = %f, f(b) = %f%n", b, fb);
        System.out.println("------------------------------");
    }

}
