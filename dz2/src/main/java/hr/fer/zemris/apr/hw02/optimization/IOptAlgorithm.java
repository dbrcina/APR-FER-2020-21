package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.IFunction;

/**
 * <p>
 * Simulates an optimization algorithm. All optimization algorithms consist of these must
 * have properties (along with personal properties):
 * <ul>
 *     <li>initial point vector - <i>double[]</i>,</li>
 *     <li>epsilons vector - <i>double[]</i>,</li>
 *     <li>verbose - <i>boolean</i>, if set to <code>true</code>, results of optimization algorithm are printed in
 *     each iteration,</li>
 *     <li>number of iterations - <i>integer</i>.</li>
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
     * Configures <b>this</b> optimization algorithm based on provided <code>configFile</code>.
     *
     * @param configFile configuration file.
     * @throws Exception if errors occurs while reading from a file or if the configuration file is invalid.
     */
    void configure(String configFile) throws Exception;

    /**
     * Executes <b>this</b> optimization algorithm on provided <code>function</code>.
     *
     * @param function function that needs to be optimized.
     * @return the optimal point as a result.
     */
    double[] run(IFunction function);

    /**
     * @return number of iterations.
     */
    int numberOfIterations();

    /**
     * Setts verbose flag.
     *
     * @param verbose verbose flag.
     */
    void setVerbose(boolean verbose);

}
