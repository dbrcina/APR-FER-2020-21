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

    /*
     * Initial point and epsilon values are shared among all optimization algorithms,
     * so they are initialized in configure method where initial point must be provided!
     */
    private double[] initialPoint;
    private double[] epsilons; // default values are 1e-6

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
            Arrays.fill(epsilons, 1e-6);
        }
    }

    protected double[] getInitialPoint() {
        return initialPoint;
    }

    protected double[] getEpsilons() {
        return epsilons;
    }

}
