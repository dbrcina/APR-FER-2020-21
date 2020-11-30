package hr.fer.zemris.apr.hw03.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw03.function.IFunction;
import hr.fer.zemris.apr.hw03.optimization.constraint.ArgsConstraints;

import java.util.Properties;

/**
 * <p>
 * Simulates an optimization algorithm. All optimization algorithms consist of these must
 * have properties (along with personal properties):
 * <ul>
 *     <li>initial point column vector - <i>{@link IMatrix}</i>,</li>
 *     <li>epsilons column vector - <i>{@link IMatrix}</i>,</li>
 *     <li>verbose - <i>boolean</i>, if set to <code>true</code>, results of optimization algorithm are printed in
 *     each iteration,</li>
 *     <li>number of iterations - <i>integer</i>,</li>
 *     <li>configured - <i>boolean</i> - if set to <code>true</code>, then algorithm has been configured through
 *     {@link #configure(Properties)} method,</li>
 *     <li>constraints - <i>{@link ArgsConstraints}</i>, it can be {@code null}.</li>
 * </ul>
 * Properties are accessible through certain getters/setters methods.
 * </p>
 *
 * <p>
 * Instances can be accessed through {@link OptAlgorithmProvider} class.
 * </p>
 *
 * @author dbrcina
 */
public interface IOptAlgorithm {

    /**
     * Configures <b>this</b> optimization algorithm based on provided <code>properties</code> object.
     *
     * @param properties properties object.
     *
     * @throws Exception if configuration is invalid.
     */
    void configure(Properties properties) throws Exception;

    /**
     * Executes <b>this</b> optimization algorithm on provided <code>function</code>.
     *
     * @param function function that needs to be optimized.
     *
     * @return the optimal point as a result.
     */
    IMatrix run(IFunction function);

    /**
     * @return a copy of the initial point vector.
     */
    IMatrix getInitialPoint();

    /**
     * Setter for the initial point vector. The provided vector is copied rather than saved by the reference.
     *
     * @param initialPoint the initial point vector.
     */
    void setInitialPoint(IMatrix initialPoint);

    /**
     * @return a copy of the epsilons vector.
     */
    IMatrix getEpsilons();

    /**
     * Setter for the epsilons vector. The provided vector is copied rather than saved by the reference.
     *
     * @param epsilons the epsilons vector.
     */
    void setEpsilons(IMatrix epsilons);

    /**
     * @return <code>true</code> if the verbose flag is set to <code>true</code>.
     */
    boolean isVerbose();

    /**
     * Setter for the verbose flag.
     *
     * @param verbose the verbose flag.
     */
    void setVerbose(boolean verbose);

    /**
     * @return the number of iterations.
     */
    int numberOfIterations();

    /**
     * @return <code>true</code> if <b>this</b> optimization algorithm has been configured through
     * {@link #configure(Properties)} method.
     */
    boolean isConfigured();

    /**
     * Setter for the constraints.
     *
     * @param constraints constraints.
     */
    void setConstraints(ArgsConstraints constraints);

    /**
     * @return function's constraints.
     */
    ArgsConstraints getConstraints();

}
