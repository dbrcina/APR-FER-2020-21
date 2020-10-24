package hr.fer.zemris.apr.hw02.optimization;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

/**
 * An abstract implementation of {@link IOptAlgorithm} interface. All optimization algorithms consist of this must
 * have properties (along with personal properties):
 * <ul>
 *     <li>initial point vector - <i>double[]</i>,</li>
 *     <li>epsilons vector - <i>double[]</i>,</li>
 *     <li>verbose - <i>boolean</i>, if set to <code>true</code>, results are printed in each iteration of optimization
 *     algorithm.</li>
 * </ul>
 * Properties are accessible through certain getters/setters methods.
 *
 * @author dbrcina
 */
public abstract class AbstractOptAlgorithm implements IOptAlgorithm {

    private final static double DEFAULT_EPSILON = 1e-6;

    private double[] initialPoint;
    private double[] epsilons;
    private boolean verbose;

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
        } else {
            epsilons = new double[initialPoint.length];
            Arrays.fill(epsilons, DEFAULT_EPSILON);
        }
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
     * @return initial point vector.
     */
    public double[] getInitialPoint() {
        return initialPoint;
    }

    /**
     * @return epsilons vector.
     */
    public double[] getEpsilons() {
        return epsilons;
    }

    /**
     * @return whether verbose flag is set for <b>this</b> optimization algorithm.
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * Setts verbose flag.
     *
     * @param verbose verbose flag.
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

}
