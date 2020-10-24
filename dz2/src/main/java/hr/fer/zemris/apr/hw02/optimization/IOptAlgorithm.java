package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.IFunction;

/**
 * Simulates an optimization algorithm that contains {@link #configure(String)} and {@link #run(IFunction)}
 * methods.
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

}
