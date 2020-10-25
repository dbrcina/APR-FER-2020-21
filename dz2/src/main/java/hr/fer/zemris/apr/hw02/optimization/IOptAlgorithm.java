package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.IFunction;

import java.util.Properties;

/**
 * <p>
 * Simulates an optimization algorithm. All optimization algorithms consist of these must
 * have properties (along with personal properties):
 * <ul>
 *     <li>initial point vector - <i>double[]</i>,</li>
 *     <li>epsilons vector - <i>double[]</i>,</li>
 *     <li>verbose - <i>boolean</i>, if set to <code>true</code>, results of optimization algorithm are printed in
 *     each iteration,</li>
 *     <li>number of iterations - <i>integer</i>,</li>
 *     <li>configured - <i>boolean</i> - if set to <code>true</code>, then algorithm has been configured through
 *     {@link #configure(Properties)} method.</li>
 * </ul>
 * Properties are accessible through certain getters/setters methods.
 * </p>
 *
 * <p>
 * Instances can be accessed through {@link IOptAlgorithmProvider} class.
 * </p>
 *
 * @author dbrcina
 */
public interface IOptAlgorithm {

    /**
     * Configures <b>this</b> optimization algorithm based on provided <code>properties</code> object.
     *
     * @param properties properties object.
     * @throws Exception if configuration is invalid.
     */
    void configure(Properties properties) throws Exception;

    /**
     * Executes <b>this</b> optimization algorithm on provided <code>function</code>.
     *
     * @param function function that needs to be optimized.
     * @return the optimal point as a result.
     */
    double[] run(IFunction function);

    /**
     * @return initial point vector.
     */
    double[] getInitialPoint();

    /**
     * Setter for initial point vector.
     *
     * @param initialPoint initial point vector.
     */
    void setInitialPoint(double[] initialPoint);

    /**
     * @return epsilons vector.
     */
    double[] getEpsilons();

    /**
     * Setter for epsilons vector.
     *
     * @param epsilons epsilons vector.
     */
    void setEpsilons(double[] epsilons);

    /**
     * @return <code>true</code> if verbose flag is set to true.
     */
    boolean isVerbose();

    /**
     * Setts verbose flag.
     *
     * @param verbose verbose flag.
     */
    void setVerbose(boolean verbose);

    /**
     * @return number of iterations.
     */
    int numberOfIterations();

    /**
     * @return <code>true</code> if <b>this</b> optimization algorithm has been configured through
     * {@link #configure(Properties)} method.
     */
    boolean isConfigured();

}
