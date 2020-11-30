package hr.fer.zemris.apr.hw03.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;
import hr.fer.zemris.apr.hw03.function.IFunction;
import hr.fer.zemris.apr.hw03.optimization.constraint.ArgsConstraints;

import java.util.Arrays;
import java.util.Properties;

/**
 * An abstract implementation of the {@link IOptAlgorithm} interface.
 *
 * @author dbrcina
 */
public abstract class AbstractOptAlgorithm implements IOptAlgorithm {

    private static final int DIVERGENCE_LIMIT = 100;
    private final static double DEFAULT_EPSILON = 1e-6;

    private IMatrix initialPoint;
    private IMatrix epsilons;
    private boolean verbose;
    private int iterations;
    private boolean configured;
    private ArgsConstraints constraints;
    private int divergenceCounter;

    /**
     * Constructor is protected because of the singleton/factory design pattern.
     */
    protected AbstractOptAlgorithm(int something) {
    }

    @Override
    public void configure(Properties properties) throws Exception {
        if (properties == null) {
            throw new ConfigInvalidException("Properties object is null!");
        }
        if (properties.size() == 0) {
            throw new ConfigInvalidException("Properties object contains no configuration!");
        }
        /* Configure the initial point vector. Find 'initial.point' in the properties map. */
        initialPoint = null;
        Object initialPointObj = properties.get("initial.point");
        if (initialPointObj != null) {
            double[] columnData = Arrays.stream(((String) initialPointObj).split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            initialPoint = new Matrix(columnData.length, 1);
            initialPoint.setColumn(0, columnData);
        }
        if (initialPoint == null) {
            throw new ConfigInvalidException("Initial point must be provided!");
        }
        /* Configure the epsilons vector. Find 'epsilons' in the properties map. */
        epsilons = null;
        Object epsilonObj = properties.get("epsilons");
        double[] epsilonsColumnData;
        if (epsilonObj != null) {
            epsilonsColumnData = Arrays.stream(((String) epsilonObj).split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            if (epsilonsColumnData.length != initialPoint.getRowsCount()) {
                throw new ConfigInvalidException(
                        "Dimension of the initial point and the epsilons vectors need to be the same!"
                );
            }
        } else {
            epsilonsColumnData = new double[initialPoint.getRowsCount()];
            Arrays.fill(epsilonsColumnData, DEFAULT_EPSILON);
        }
        epsilons = new Matrix(epsilonsColumnData.length, 1);
        epsilons.setColumn(0, epsilonsColumnData);
        configured = true;
    }

    @Override
    public IMatrix run(IFunction function) {
        if (verbose) {
            System.out.println("Running " + this + " optimization algorithm:");
        }
        iterations = 0;
        return null;
    }

    @Override
    public IMatrix getInitialPoint() {
        return initialPoint.copy();
    }

    @Override
    public void setInitialPoint(IMatrix initialPoint) {
        this.initialPoint = initialPoint.copy();
    }

    @Override
    public IMatrix getEpsilons() {
        return epsilons.copy();
    }

    @Override
    public void setEpsilons(IMatrix epsilons) {
        this.epsilons = epsilons.copy();
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
    public void setConstraints(ArgsConstraints constraints) {
        this.constraints = constraints;
    }

    @Override
    public ArgsConstraints getConstraints() {
        return constraints;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    /**
     * Formats a configuration exception message for {@link #configure(Properties)} method.
     *
     * @param e exception.
     *
     * @throws ConfigInvalidException after formatting exception message.
     */
    protected void handleConfigureExceptions(Exception e) throws ConfigInvalidException {
        String methodName = getClass().getSimpleName() + "::configure(Properties)";
        throw new ConfigInvalidException(methodName + ": " + e.getMessage());
    }

    /**
     * Calculates <code>L2-norm</code> of the provided column <code>vector</code>.
     *
     * @param vector column vector.
     *
     * @return L2 norm.
     */
    protected double l2Norm(IMatrix vector) {
        return Math.sqrt(Arrays.stream(vector.columnData(0)).map(v -> v * v).sum());
    }

    /**
     * Increments iterations variable by provided <code>i</code>.
     *
     * @param i increment i.
     */
    protected void incrementIterations(int i) {
        iterations += i;
    }

    protected boolean isDiverging(double currentValue, double best) {
        if (currentValue >= best) {
            divergenceCounter++;
            if (divergenceCounter == DIVERGENCE_LIMIT) {
                System.out.println("Divergence limit was hit! Exiting...");
                return true;
            }
        } else {
            divergenceCounter = 0;
        }
        return false;
    }

}
