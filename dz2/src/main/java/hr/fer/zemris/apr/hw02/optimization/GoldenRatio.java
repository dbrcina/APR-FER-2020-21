package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.IFunction;

import java.util.Arrays;
import java.util.Properties;
import java.util.function.BiFunction;

/**
 * An implementation of <i>Golden ratio</i> optimization algorithm. It is modeled as a singleton object and its
 * instance can be obtained by {@link #getInstance()} method.
 *
 * @author dbrcina
 */
public class GoldenRatio extends AbstractOptAlgorithm {

    /* Golden ration constant. */
    private static final double K = 0.5 * (Math.sqrt(5) - 1);
    private static GoldenRatio instance;
    /* BiFunction used for calculating c value in golden ration algorithm. */
    private final BiFunction<Double, Double, Double> C = (a, b) -> b - K * (b - a);
    /* BiFunction used for calculating d value in golden ration algorithm. */
    private final BiFunction<Double, Double, Double> D = (a, b) -> a + K * (b - a);
    /* Unimodal interval used in golden ratio algorithm. */
    private double[] interval;

    /* Constructor is private because of singleton design pattern. */
    private GoldenRatio() {
    }

    /**
     * @return an instance of GoldenRatio class.
     */
    public static GoldenRatio getInstance() {
        if (instance == null) {
            instance = new GoldenRatio();
        }
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
        } catch (NumberFormatException numEx) {
            throw new RuntimeException(
                    "GoldenRatio::configure(String) configuration file " + configFile + " is invalid!"
            );
        }
    }

    @Override
    public double[] run(IFunction function) {
        if (interval == null) {
            // Initial point is an array with the length of 1.
            interval = UnimodalInterval.create(function, getInitialPoint()[0]);
        }
        double a = interval[0];
        double b = interval[1];
        double c = C.apply(a, b);
        double d = D.apply(a, b);
        double fc = function.value(new double[]{c});
        double fd = function.value(new double[]{d});
        // Epsilons is an array with the length of 1.
        double epsilon = getEpsilons()[0];
        while ((b - a) > epsilon) {
            if (fc < fd) {
                b = d;
                d = c;
                c = C.apply(a, b);
                fd = fc;
                fc = function.value(new double[]{c});
            } else {
                a = c;
                c = d;
                d = D.apply(a, b);
                fc = fd;
                fd = function.value(new double[]{d});
            }
        }
        return new double[]{(a + b) / 2};
    }

}
