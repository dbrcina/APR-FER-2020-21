package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;
import hr.fer.zemris.apr.hw02.function.IFunction;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.function.BiFunction;

/**
 * An implementation of the <i>Golden ratio</i> optimization algorithm.
 *
 * @author dbrcina
 * @see IOptAlgorithm
 */
public class GoldenRatio extends AbstractOptAlgorithm {

    /* Golden ration constant. */
    private static final double K = 0.5 * (Math.sqrt(5) - 1);
    /* BiFunction used for calculating c value in golden ration algorithm. */
    private static final BiFunction<Double, Double, Double> C = (a, b) -> b - K * (b - a);
    /* BiFunction used for calculating d value in golden ration algorithm. */
    private static final BiFunction<Double, Double, Double> D = (a, b) -> a + K * (b - a);

    /* Unimodal interval used in the golden ratio algorithm. */
    private double[] interval;

    /**
     * @see AbstractOptAlgorithm#AbstractOptAlgorithm()
     */
    protected GoldenRatio() {
    }

    @Override
    public void configure(Properties properties) throws Exception {
        try {
            super.configure(properties);
            interval = null;
            Object intervalObj = properties.get("interval");
            if (intervalObj != null) {
                interval = Arrays.stream(((String) intervalObj).split("\\s+"))
                        .mapToDouble(Double::parseDouble)
                        .sorted()
                        .toArray();
            }
        } catch (NumberFormatException | ConfigInvalidException e) {
            handleConfigureExceptions(e);
        }
    }

    @Override
    public IMatrix run(IFunction function) {
        super.run(function);
        if (interval == null) {
            // Initial point is a matrix with 1x1 dimensions.
            interval = UnimodalInterval.create(function, getInitialPoint().get(0, 0));
        }
        // This matrix object will be used to compute operations.
        IMatrix matrix = new Matrix(1, 1);
        double a = interval[0];
        double b = interval[1];
        double c = C.apply(a, b);
        double d = D.apply(a, b);
        double fc = function.value(matrix.set(0, 0, c));
        double fd = function.value(matrix.set(0, 0, d));
        // Epsilons is a matrix with 1x1 dimensions.
        double epsilon = getEpsilons().get(0, 0);
        while ((b - a) > epsilon) {
            incrementIterations(1);
            if (isVerbose()) {
                double fa = function.value(matrix.set(0, 0, a));
                double fb = function.value(matrix.set(0, 0, b));
                printResults(numberOfIterations(), a, b, c, d, fa, fb, fc, fd);
            }
            if (fc < fd) {
                b = d;
                d = c;
                c = C.apply(a, b);
                fd = fc;
                fc = function.value(matrix.set(0, 0, c));
            } else {
                a = c;
                c = d;
                d = D.apply(a, b);
                fc = fd;
                fd = function.value(matrix.set(0, 0, d));
            }
        }
        return matrix.set(0, 0, (a + b) / 2);
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

    /**
     * @return a copy of the interval or <code>null</code> if the interval is not yet calculated.
     */
    public double[] getInterval() {
        if (interval == null) return null;
        return Arrays.copyOf(interval, interval.length);
    }

    /**
     * Setter for the interval. The provided interval is copied rather than saved by the reference.
     *
     * @param interval interval.
     * @throws IllegalArgumentException if the provided interval is not <code>null</code> and is an array with the
     *                                  length of 2.
     */
    public void setInterval(double[] interval) {
        if (interval == null) {
            this.interval = null;
            return;
        }
        if (interval.length != 2) {
            throw new IllegalArgumentException("GoldenRatio::setInterval(double[]): Interval length is not 2!");
        }
        this.interval = Arrays.copyOf(interval, interval.length);
    }

}
