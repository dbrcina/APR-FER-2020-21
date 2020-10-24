package hr.fer.zemris.apr.hw02.optimization;

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

    /*
     * Initial point and epsilon values are shared among all optimization algorithms,
     * so they are initialized in configure method where initial point must be provided!
     */
    private double[] initialPoint;
    /* Vector of epsilons. Default values are set to DEFAULT_EPSILON. */
    private double[] epsilons;

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

    protected double[] getInitialPoint() {
        return initialPoint;
    }

    protected double[] getEpsilons() {
        return epsilons;
    }

}
