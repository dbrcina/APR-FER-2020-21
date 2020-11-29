package hr.fer.zemris.apr.hw03.optimization;

import java.util.Properties;

/**
 * An implementation of {@link AbstractOptAlgorithm} which models algorithms that uses gradient in optimization.
 *
 * @author dbrcina
 */
public abstract class GradientOptAlgorithm extends AbstractOptAlgorithm {

    protected boolean optimized;

    /**
     * @see AbstractOptAlgorithm#AbstractOptAlgorithm(int)
     */
    protected GradientOptAlgorithm(int something) {
        super(something);
    }

    @Override
    public void configure(Properties properties) throws Exception {
        try {
            super.configure(properties);
            Object optimizedObj = properties.get("optimized");
            if (optimizedObj != null) {
                optimized = Boolean.parseBoolean((String) optimizedObj);
            }
        } catch (NumberFormatException | ConfigInvalidException e) {
            handleConfigureExceptions(e);
        }
    }

}
