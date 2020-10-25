package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.IFunction;

import java.nio.file.Files;
import java.nio.file.Paths;
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

    /* Constructor is protected because of the singleton design pattern. */
    protected AbstractOptAlgorithm() {
    }

    @Override
    public void configure(String configFile) throws Exception {
        initialPoint = null;
        Properties properties = PropertiesProvider.getProperties();
        properties.load(Files.newInputStream(Paths.get(configFile)));
        Object initialPointObj = properties.get("initial.point");
        if (initialPointObj != null) {
            initialPoint = Arrays.stream(((String) initialPointObj).split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
        }
        if (initialPoint == null) {
            throw new ConfigFileException("Initial point must be provided!");
        }
        Object epsilonObj = properties.get("epsilons");
        if (epsilonObj != null) {
            epsilons = Arrays.stream(((String) epsilonObj).split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            if (epsilons.length != initialPoint.length) {
                throw new ConfigFileException("Dimension of initial point and epsilons vectors need to be the same!");
            }
        } else {
            epsilons = new double[initialPoint.length];
            Arrays.fill(epsilons, DEFAULT_EPSILON);
        }
        configured = true;
    }

    @Override
    public double[] run(IFunction function) {
        System.out.println("Running " + getClass().getSimpleName() + " optimization algorithm:");
        iterations = 0;
        return null;
    }

    @Override
    public int numberOfIterations() {
        return iterations;
    }

    @Override
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public boolean isConfigured() {
        return configured;
    }

    /**
     * Formats a configuration exception message for {@link #configure(String)} method.
     *
     * @param configFile configuration file.
     * @param e          exception.
     * @throws ConfigFileException after formatting exception message.
     */
    protected void handleConfigureExceptions(String configFile, Exception e) throws ConfigFileException {
        String methodName = getClass().getSimpleName() + "::configure(String)";
        String msg = String.format("%s configuration file '%s' is invalid!", methodName, configFile);
        // Additional message is expected when ConfigFileException is thrown!
        if (e instanceof ConfigFileException) {
            msg += " " + e.getMessage();
        }
        throw new ConfigFileException(msg);
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
     * @return initial point vector.
     */
    protected double[] getInitialPoint() {
        return initialPoint;
    }

    /**
     * Setter for initial point.
     *
     * @param initialPoint initial point vector.
     */
    protected void setInitialPoint(double[] initialPoint) {
        this.initialPoint = initialPoint;
    }

    /**
     * @return epsilons vector.
     */
    protected double[] getEpsilons() {
        return epsilons;
    }

    /**
     * Setter for epsilons.
     *
     * @param epsilons epsilons vector.
     */
    protected void setEpsilons(double[] epsilons) {
        this.epsilons = epsilons;
    }

    /**
     * @return whether verbose flag is set for <b>this</b> optimization algorithm.
     */
    protected boolean isVerbose() {
        return verbose;
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
