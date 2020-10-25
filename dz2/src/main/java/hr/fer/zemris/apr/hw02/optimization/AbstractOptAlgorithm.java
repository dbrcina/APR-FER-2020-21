package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.IFunction;

import java.util.Arrays;
import java.util.Properties;

/**
 * An abstract implementation of {@link IOptAlgorithm} interface.
 *
 * @author dbrcina
 */
public abstract class AbstractOptAlgorithm implements IOptAlgorithm {

    private final static double DEFAULT_EPSILON = 1e-6;

    private double[] initialPoint;
    private double[] epsilons;
    private boolean verbose;
    private int iterations;
    private boolean configured;

    /**
     * Constructor is protected because of the singleton/factory design pattern.
     */
    protected AbstractOptAlgorithm() {
    }

    @Override
    public void configure(Properties properties) throws Exception {
        if (properties == null) {
            throw new ConfigInvalidException("Properties object is null!");
        }
        if (properties.size() == 0) {
            throw new ConfigInvalidException("Properties object contains no configuration!");
        }
        initialPoint = null;
        Object initialPointObj = properties.get("initial.point");
        if (initialPointObj != null) {
            initialPoint = Arrays.stream(((String) initialPointObj).split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
        }
        if (initialPoint == null) {
            throw new ConfigInvalidException("Initial point must be provided!");
        }
        Object epsilonObj = properties.get("epsilons");
        if (epsilonObj != null) {
            epsilons = Arrays.stream(((String) epsilonObj).split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            if (epsilons.length != initialPoint.length) {
                throw new ConfigInvalidException("Dimension of initial point and epsilons vectors need to be the same!");
            }
        } else {
            epsilons = new double[initialPoint.length];
            Arrays.fill(epsilons, DEFAULT_EPSILON);
        }
        configured = true;
    }

    @Override
    public double[] run(IFunction function) {
        if (verbose) {
            System.out.println("Running " + this + " optimization algorithm:");
        }
        iterations = 0;
        return null;
    }

    @Override
    public double[] getInitialPoint() {
        return Arrays.copyOf(initialPoint, initialPoint.length);
    }

    @Override
    public void setInitialPoint(double[] initialPoint) {
        this.initialPoint = Arrays.copyOf(initialPoint, initialPoint.length);
    }

    @Override
    public double[] getEpsilons() {
        return Arrays.copyOf(epsilons, epsilons.length);
    }

    @Override
    public void setEpsilons(double[] epsilons) {
        this.epsilons = Arrays.copyOf(epsilons, epsilons.length);
    }

    @Override
    public boolean isVerbose() {
        return verbose;
    }

    @Override
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public int numberOfIterations() {
        return iterations;
    }

    @Override
    public boolean isConfigured() {
        return configured;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    /**
     * Formats a configuration exception message for {@link #configure(Properties)} method.
     *
     * @param e exception.
     * @throws ConfigInvalidException after formatting exception message.
     */
    protected void handleConfigureExceptions(Exception e) throws ConfigInvalidException {
        String methodName = getClass().getSimpleName() + "::configure(Properties)";
        throw new ConfigInvalidException(methodName + ": " + e.getMessage());
    }

    /**
     * L2-norm is used to calculate values.
     *
     * @param vector vector.
     * @return <code>true</code> if the vector is lower than or equal to epsilons vector.
     */
    protected boolean vectorLEQEpsilons(double[] vector) {
        double vectorNorm = Math.sqrt(Arrays.stream(vector).map(v -> v * v).sum());
        double epsilonsNorm = Math.sqrt(Arrays.stream(epsilons).map(e -> e * e).sum());
        return vectorNorm <= epsilonsNorm;
    }

    /**
     * Calculates <code>L2-norm</code> of the provided <code>vector</code>.
     *
     * @param vector vector.
     * @return L2 norm.
     */
    protected double l2Norm(double[] vector) {
        return Math.sqrt(Arrays.stream(vector).map(v -> v * v).sum());
    }

    /**
     * Increments iterations variable by provided <code>i</code>.
     *
     * @param i increment i.
     */
    protected void incrementIterations(int i) {
        iterations += i;
    }

}
