package hr.fer.zemris.apr.hw03.optimization;

/**
 * Exception that gets thrown when some configuration is invalid.
 *
 * @author dbrcina
 */
public class ConfigInvalidException extends RuntimeException {

    public ConfigInvalidException() {
        super();
    }

    public ConfigInvalidException(String message) {
        super(message);
    }

}
